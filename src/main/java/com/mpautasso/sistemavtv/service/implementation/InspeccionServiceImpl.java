package com.mpautasso.sistemavtv.service.implementation;

import com.mpautasso.sistemavtv.exceptions.custom.EntityNotFoundException;
import com.mpautasso.sistemavtv.exceptions.custom.InvalidArgumentException;
import com.mpautasso.sistemavtv.exceptions.custom.NoSuchEntityExistsException;
import com.mpautasso.sistemavtv.mapper.InspeccionMapper;
import com.mpautasso.sistemavtv.mapper.PropietarioMapper;
import com.mpautasso.sistemavtv.model.*;
import com.mpautasso.sistemavtv.model.dtos.automovil.AutomovilInspeccionResponse;
import com.mpautasso.sistemavtv.model.dtos.inspeccion.InspeccionRequest;
import com.mpautasso.sistemavtv.model.dtos.inspeccion.InspeccionResponse;
import com.mpautasso.sistemavtv.model.dtos.inspeccion.SimpleInspeccionResponse;
import com.mpautasso.sistemavtv.model.dtos.propietario.PropietarioDetallesResponse;
import com.mpautasso.sistemavtv.repository.InspeccionRepository;
import com.mpautasso.sistemavtv.repository.UltimaInspeccionAutomovilRepository;
import com.mpautasso.sistemavtv.service.AutomovilService;
import com.mpautasso.sistemavtv.service.InspeccionService;
import com.mpautasso.sistemavtv.service.InspectorService;
import com.mpautasso.sistemavtv.service.PropietarioService;
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
    private final AutomovilService automovilService;
    private final InspectorService inspectorService;
    private final PropietarioService propietarioService;
    private final InspeccionMapper inspeccionMapper;
    private final PropietarioMapper propietarioMapper;

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

        Automovil automovil = automovilService.buscarEntidadPorDominio(inspeccionRequest.getDominioAutomovil());
        Inspector inspector = inspectorService.buscarEntidadPorDni(inspeccionRequest.getInspectorId());
        Inspeccion inspeccion = inspeccionRepository.save(
                inspeccionMapper.fromRequestToEntity(inspeccionRequest, automovil, inspector)
        );

        actualizarUltimaInspeccion(automovil, inspeccion);

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

        Automovil automovil = automovilService.buscarEntidadPorDominio(inspeccionRequest.getDominioAutomovil());
        Inspector inspector = inspectorService.buscarEntidadPorDni(inspeccionRequest.getInspectorId());
        inspeccionBD.setAutomovil(automovil);
        inspeccionBD.setInspector(inspector);
        inspeccionBD.setEstaExento(automovil.getPropietario().tipoPropietario().equals("exento"));

        actualizarUltimaInspeccion(automovil, inspeccionBD);

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
    public PropietarioDetallesResponse listarInspeccionesPorAutoDePropietario(Long dni) {
        Propietario propietario = propietarioService.buscarEntidadPorDni(dni);
        List<Automovil> automoviles = automovilService.listarAutomoviles(propietario);

        List<AutomovilInspeccionResponse> automovilesResponse =
                automoviles.stream()
                        .map(this::listarInspeccionesDeAuto)
                        .collect(Collectors.toList());


        return new PropietarioDetallesResponse(
                propietarioMapper.entityToResponse(propietario),
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

        if(inspeccionRequest.getDominioAutomovil().isBlank()){
            throw new InvalidArgumentException("El dominio del automovil no puede ser vacío o solo contener espacios");
        }
    }

    private AutomovilInspeccionResponse listarInspeccionesDeAuto(Automovil automovil){
        List<SimpleInspeccionResponse> simpleInspeccionResponses =
                inspeccionRepository.findAllByAutomovil(automovil)
                .stream()
                .map(inspeccionMapper::fromEntityToSimpleResponse)
                .collect(Collectors.toList());
        return new AutomovilInspeccionResponse(
                automovil.getMarca(),
                automovil.getModelo(),
                automovil.getDominio(),
                simpleInspeccionResponses
        );
    }

    private boolean noPerteneceAUnEstadoValido(String estadosInspeccion){
        return !(estadosInspeccion.equals(EstadosInspeccion.APTO.toString()) ||
                estadosInspeccion.equals(EstadosInspeccion.CONDICIONAL.toString()) ||
                estadosInspeccion.equals(EstadosInspeccion.RECHAZADO.toString()));
    }

    private void actualizarUltimaInspeccion(Automovil automovil, Inspeccion inspeccion){
        Optional<UltimaInspeccionAutomovil> inspeccionAutomovilOpt = inspeccionAutomovilRepository.findByAutomovil(automovil);
        if(inspeccionAutomovilOpt.isPresent()){
            UltimaInspeccionAutomovil inspecAuto = inspeccionAutomovilOpt.get();
            inspecAuto.setEstadosInspeccion(inspeccion.getEstado());
            inspecAuto.setFecha(inspeccion.getFecha());
            inspeccionAutomovilRepository.save(inspecAuto);
        } else {
            UltimaInspeccionAutomovil inspecAuto =
                    new UltimaInspeccionAutomovil(null, automovil, inspeccion.getEstado(), inspeccion.getFecha());
            inspeccionAutomovilRepository.save(inspecAuto);
        }
    }
}
