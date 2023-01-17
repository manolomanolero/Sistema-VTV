package com.mpautasso.sistemavtv.service.implementation;

import com.mpautasso.sistemavtv.exceptions.custom.EntityAlreadyExistsException;
import com.mpautasso.sistemavtv.exceptions.custom.InvalidArgumentException;
import com.mpautasso.sistemavtv.exceptions.custom.NoSuchEntityExistsException;
import com.mpautasso.sistemavtv.mapper.AutomovilMapper;
import com.mpautasso.sistemavtv.model.Automovil;
import com.mpautasso.sistemavtv.model.EstadosInspeccion;
import com.mpautasso.sistemavtv.model.Propietario;
import com.mpautasso.sistemavtv.model.dtos.automovil.AutomovilRequest;
import com.mpautasso.sistemavtv.model.dtos.automovil.AutomovilResponse;
import com.mpautasso.sistemavtv.repository.AutomovilRepository;
import com.mpautasso.sistemavtv.repository.UltimaInspeccionAutomovilRepository;
import com.mpautasso.sistemavtv.service.AutomovilService;
import com.mpautasso.sistemavtv.service.PropietarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AutomovilServiceImpl implements AutomovilService {
    private final AutomovilRepository automovilRepository;
    private final UltimaInspeccionAutomovilRepository inspeccionAutoRepo;
    private final AutomovilMapper automovilMapper;
    private final PropietarioService propietarioService;

    //Toda inspeccion anterior a esta fecha estara vencidad ya que ocurrio hace mas de 1 año
    private final Date FECHA_LIMITE_VENCIMIENTO_DE_AÑO = new Date(new Date().getTime() - 1000 * 60 * 60 * 24 * 365);
    private final Date FECHA_LIMITE_VENCIMIENTO_DE_DIA = new Date(new Date().getTime() - 1000 * 60 * 60 * 24 );

    @Override
    public List<AutomovilResponse> listarAutomoviles() {
        return automovilRepository.findAll().stream()
                .map(automovilMapper::fromEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<Automovil> listarAutomoviles(Propietario propietario) {
        return automovilRepository.findAllByPropietario(propietario);
    }

    @Override
    public List<AutomovilResponse> listarAutomovilesAptos() {
        return inspeccionAutoRepo.findAllByEstadosInspeccion(EstadosInspeccion.APTO)
                .stream()
                .filter(auto -> FECHA_LIMITE_VENCIMIENTO_DE_AÑO.before(auto.getFecha()))
                .map(automovilMapper::inspeccionAutoToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AutomovilResponse> listarAutomovilesCondicionales() {
        return inspeccionAutoRepo.findAllByEstadosInspeccion(EstadosInspeccion.CONDICIONAL)
                .stream()
                .filter(auto -> FECHA_LIMITE_VENCIMIENTO_DE_DIA.before(auto.getFecha()))
                .map(automovilMapper::inspeccionAutoToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<AutomovilResponse> listarAutomovilesRechazados() {
        return inspeccionAutoRepo.findAllByEstadosInspeccion(EstadosInspeccion.RECHAZADO)
                .stream()
                .map(automovilMapper::inspeccionAutoToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Automovil buscarEntidadPorDominio(String dominio) {
        Optional<Automovil> automovilOptional = automovilRepository.findByDominio(dominio);
        if(automovilOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se encontro automovil asociado al dominio");
        }
        return automovilOptional.get();
    }

    @Override
    public AutomovilResponse buscarAutomovilPorDominio(String dominio) {
        Optional<Automovil> automovilOptional = automovilRepository.findByDominio(dominio);
        if(automovilOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se encontro automovil asociado al dominio");
        }
        return automovilMapper.fromEntityToResponse(
                automovilOptional.get());
    }

    @Override
    public AutomovilResponse crearAutomovil(AutomovilRequest automovilRequest) {
        validarDatosDeAutomovil(automovilRequest);

        Optional<Automovil> automovilOptional = automovilRepository.findByDominio(automovilRequest.getDominio());
        if(automovilOptional.isPresent()){
            throw new EntityAlreadyExistsException("Ya existe un automovil con este dominio");
        }

        Propietario propietario = propietarioService.buscarEntidadPorDni(automovilRequest.getPropietarioDni());

        return automovilMapper.fromEntityToResponse(
                automovilRepository.save(
                        automovilMapper.fromRequestToEntity(automovilRequest, propietario)
                )
        );
    }

    @Override
    public AutomovilResponse editarAutomovil(AutomovilRequest automovilRequest) {
        validarDatosDeAutomovil(automovilRequest);

        Optional<Automovil> automovilOptional = automovilRepository.findByDominio(automovilRequest.getDominio());
        if(automovilOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se encontro el automovil a editar");
        }
        Propietario propietario = propietarioService.buscarEntidadPorDni(automovilRequest.getPropietarioDni());

        Automovil automovilBD = automovilOptional.get();
        automovilBD.setMarca(automovilRequest.getMarca());
        automovilBD.setModelo(automovilRequest.getModelo());
        automovilBD.setPropietario(propietario);

        return automovilMapper.fromEntityToResponse(
                automovilRepository.save(automovilBD)
        );
    }

    @Override
    public void eliminarAutomovil(String dominio) {
        Optional<Automovil> automovilOptional = automovilRepository.findByDominio(dominio);
        if(automovilOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se encontro el automovil a eliminar");
        }
        automovilRepository.delete(automovilOptional.get());
    }

    private void validarDatosDeAutomovil(AutomovilRequest automovilRequest){
        if(automovilRequest.getDominio().isEmpty()){
            throw new InvalidArgumentException("El dominio no puede ser vacío o solo contener espacios");
        }
        if(automovilRequest.getMarca().isEmpty()){
            throw new InvalidArgumentException("El marca no puede ser vacío o solo contener espacios");
        }
        if(automovilRequest.getModelo().isEmpty()){
            throw new InvalidArgumentException("El modelo no puede ser vacío o solo contener espacios");
        }
        if(automovilRequest.getPropietarioDni() < 1){
            throw new InvalidArgumentException("El dni del propietario no puede ser 0 o un numero negativo");
        }
    }
}
