package com.mpautasso.sistemavtv.service.implementation;

import com.mpautasso.sistemavtv.exceptions.custom.EntityNotFoundException;
import com.mpautasso.sistemavtv.exceptions.custom.InvalidArgumentException;
import com.mpautasso.sistemavtv.exceptions.custom.NoSuchEntityExistsException;
import com.mpautasso.sistemavtv.mapper.InspeccionMapper;
import com.mpautasso.sistemavtv.mapper.ClienteMapper;
import com.mpautasso.sistemavtv.model.*;
import com.mpautasso.sistemavtv.model.dtos.vehiculo.VehiculoInspeccionResponse;
import com.mpautasso.sistemavtv.model.dtos.inspeccion.InspeccionRequest;
import com.mpautasso.sistemavtv.model.dtos.inspeccion.InspeccionResponse;
import com.mpautasso.sistemavtv.model.dtos.inspeccion.SimpleInspeccionResponse;
import com.mpautasso.sistemavtv.model.dtos.cliente.PropietarioDetallesResponse;
import com.mpautasso.sistemavtv.model.enums.EstadosInspeccion;
import com.mpautasso.sistemavtv.repository.InspeccionRepository;
import com.mpautasso.sistemavtv.repository.UltimaInspeccionAutomovilRepository;
import com.mpautasso.sistemavtv.service.VehiculoService;
import com.mpautasso.sistemavtv.service.InspeccionService;
import com.mpautasso.sistemavtv.service.EmpleadoService;
import com.mpautasso.sistemavtv.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InspeccionServiceImpl implements InspeccionService {
    private final InspeccionRepository inspeccionRepository;
    private final UltimaInspeccionAutomovilRepository inspeccionAutomovilRepository;
    private final VehiculoService vehiculoService;
    private final EmpleadoService empleadoService;
    private final ClienteService clienteService;
    private final InspeccionMapper inspeccionMapper;
    private final ClienteMapper clienteMapper;

    @Override
    public List<InspeccionResponse> listarInspecciones() {
        return inspeccionRepository.findAll().stream()
                .map(inspeccionMapper::fromEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Inspeccion buscarEntidadPorNumeroInspeccion(Long numeroInspeccion) {
        Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findById(numeroInspeccion);
        if(inspeccionOptional.isEmpty()){
            throw new EntityNotFoundException("No se encontro la inspeccion asociada a este numero de inspeccion");
        }
        return inspeccionOptional.get();
    }

    @Override
    public InspeccionResponse buscarInspeccionPorNumeroInspeccion(Long numeroInspeccion) {
        Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findById(numeroInspeccion);
        if(inspeccionOptional.isEmpty()){
            throw new EntityNotFoundException("No se encontro la inspeccion asociada a este numero de inspeccion");
        }
        return inspeccionMapper.fromEntityToResponse(inspeccionOptional.get());
    }

    @Override
    public InspeccionResponse crearInspeccion(InspeccionRequest inspeccionRequest) {
        validarDatosDeInspeccion(inspeccionRequest);

        Vehiculo vehiculo = vehiculoService.buscarEntidadPorDominio(inspeccionRequest.getDominioVehiculo());
        Inspector inspector = empleadoService.buscarEntidadInspectorPorDni(inspeccionRequest.getInspectorId());
        Inspeccion inspeccion = inspeccionRepository.save(
                inspeccionMapper.fromRequestToEntity(inspeccionRequest, vehiculo, inspector)
        );

        actualizarUltimaInspeccion(vehiculo, inspeccion);

        return inspeccionMapper.fromEntityToResponse(inspeccion);
    }

    @Override
    public InspeccionResponse editarInspeccion(InspeccionRequest inspeccionRequest) {
        validarDatosDeInspeccion(inspeccionRequest);

        Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findById(inspeccionRequest.getNumeroInspeccion());
        if(inspeccionOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se encontro la inspeccion a editar");
        }

        Inspeccion inspeccionBD = inspeccionOptional.get();
        inspeccionBD.setEstado(EstadosInspeccion.valueOf(inspeccionRequest.getEstado()));

        if(inspeccionRequest.getFecha() != null) {
            inspeccionBD.setFecha(inspeccionRequest.getFecha());
        }

        Vehiculo vehiculo = vehiculoService.buscarEntidadPorDominio(inspeccionRequest.getDominioVehiculo());
        Inspector inspector = empleadoService.buscarEntidadInspectorPorDni(inspeccionRequest.getInspectorId());
        inspeccionBD.setVehiculo(vehiculo);
        inspeccionBD.setInspector(inspector);
        inspeccionBD.setEstaExento(vehiculo.getPropietario().tipoDeCliente().equals("exento"));

        actualizarUltimaInspeccion(vehiculo, inspeccionBD);

        return inspeccionMapper.fromEntityToResponse(
                inspeccionRepository.save(inspeccionBD)
        );
    }

    @Override
    public void eliminarInspeccion(Long numeroInspeccion) {
        Optional<Inspeccion> inspeccionOptional = inspeccionRepository.findById(numeroInspeccion);
        if(inspeccionOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se encontro la inspeccion a eliminar");
        }
        inspeccionRepository.delete(inspeccionOptional.get());
    }

    @Override
    public PropietarioDetallesResponse listarInspeccionesPorVehiculoDePropietario(Long dni) {
        Propietario propietario = clienteService.buscarEntidadPropietarioPorDni(dni);
        List<Vehiculo> vehiculos = vehiculoService.listarVehiculos(propietario);

        List<VehiculoInspeccionResponse> automovilesResponse =
                vehiculos.stream()
                        .map(this::listarInspeccionesDeVehiculo)
                        .collect(Collectors.toList());


        return new PropietarioDetallesResponse(
                clienteMapper.entityToResponse(propietario),
                automovilesResponse
        );
    }

    private void validarDatosDeInspeccion(InspeccionRequest inspeccionRequest){
        if(inspeccionRequest.getEstado() == null || noPerteneceAUnEstadoValido(inspeccionRequest.getEstado()) ){
            throw new InvalidArgumentException("El estado de la inspeccion debe ser valido, 'APTO', 'CONDICIONAL' o 'RECHAZADO'");
        }

        if(inspeccionRequest.getFecha() != null &&
            inspeccionRequest.getFecha().after(new Date())){
            throw new InvalidArgumentException("La fecha de la inspeccion no puede ser mayor a la actual");
        }

        if(inspeccionRequest.getInspectorId() < 1){
            throw new InvalidArgumentException("El dni del inspector no puede ser 0 o un numero negativo");
        }

        if(inspeccionRequest.getDominioVehiculo().isBlank()){
            throw new InvalidArgumentException("El dominio del automovil no puede ser vacío o solo contener espacios");
        }
    }

    private VehiculoInspeccionResponse listarInspeccionesDeVehiculo(Vehiculo vehiculo){
        List<SimpleInspeccionResponse> simpleInspeccionResponses =
                inspeccionRepository.findAllByVehiculo(vehiculo)
                .stream()
                .map(inspeccionMapper::fromEntityToSimpleResponse)
                .collect(Collectors.toList());

       
        /*return new VehiculoInspeccionResponse(
                automovil.getMarca(),
                automovil.getModelo(),
                automovil.getDominio(),
                simpleInspeccionResponses
        );*/
        return null;
    }

    private boolean noPerteneceAUnEstadoValido(String estadosInspeccion){
        return !(estadosInspeccion.equals(EstadosInspeccion.APTO.toString()) ||
                estadosInspeccion.equals(EstadosInspeccion.CONDICIONAL.toString()) ||
                estadosInspeccion.equals(EstadosInspeccion.RECHAZADO.toString()));
    }

    private void actualizarUltimaInspeccion(Vehiculo vehiculo, Inspeccion inspeccion){
        Optional<UltimaInspeccionVehiculo> inspeccionVehiculoOpt = inspeccionAutomovilRepository.findByVehiculo(vehiculo);
        if(inspeccionVehiculoOpt.isPresent()){
            UltimaInspeccionVehiculo inspecVehiculo = inspeccionVehiculoOpt.get();
            inspecVehiculo.setEstadosInspeccion(inspeccion.getEstado());
            inspecVehiculo.setFecha(inspeccion.getFecha());
            inspeccionAutomovilRepository.save(inspecVehiculo);
        } else {
            UltimaInspeccionVehiculo inspecVehiculo =
                    new UltimaInspeccionVehiculo(null, vehiculo, inspeccion.getEstado(), inspeccion.getFecha());
            inspeccionAutomovilRepository.save(inspecVehiculo);
        }
    }
}
