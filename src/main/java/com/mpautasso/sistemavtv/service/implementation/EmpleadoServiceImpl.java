package com.mpautasso.sistemavtv.service.implementation;

import com.mpautasso.sistemavtv.exceptions.custom.EntityAlreadyExistsException;
import com.mpautasso.sistemavtv.exceptions.custom.EntityNotFoundException;
import com.mpautasso.sistemavtv.exceptions.custom.NoSuchEntityExistsException;
import com.mpautasso.sistemavtv.mapper.EmpleadoMapper;
import com.mpautasso.sistemavtv.model.Empleado;
import com.mpautasso.sistemavtv.model.Gerente;
import com.mpautasso.sistemavtv.model.Inspector;
import com.mpautasso.sistemavtv.model.dtos.empleado.EmpleadoRequest;
import com.mpautasso.sistemavtv.model.dtos.empleado.EmpleadoResponse;
import com.mpautasso.sistemavtv.repository.EmpleadoRepository;
import com.mpautasso.sistemavtv.service.EmpleadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmpleadoServiceImpl implements EmpleadoService {
    private final EmpleadoRepository empleadoRepository;
    private final EmpleadoMapper empleadoMapper;

    @Override
    public List<EmpleadoResponse> listarEmpleados() {
        return empleadoRepository.findAll().stream()
                .map(empleadoMapper::fromEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmpleadoResponse> listarInspectores() {
        return empleadoRepository.findAllInspectores().stream()
                .map(empleadoMapper::fromEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<EmpleadoResponse> listarGerentes() {
        return empleadoRepository.findAllGerentes().stream()
                .map(empleadoMapper::fromEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Inspector buscarEntidadInspectorPorDni(Long dni) {
        Optional<Inspector> inspectorOptional = empleadoRepository.findInspectorByDni(dni);
        if(inspectorOptional.isEmpty()){
            throw new EntityNotFoundException("No se encontro inspector asociado a este dni");
        }
        return inspectorOptional.get();
    }

    @Override
    public Gerente buscarEntidadGerentePorDni(Long dni) {
        Optional<Gerente> gerenteOptional = empleadoRepository.findGerenteByDni(dni);
        if(gerenteOptional.isEmpty()){
            throw new EntityNotFoundException("No se encontro gerente asociado a este dni");
        }
        return gerenteOptional.get();
    }

    @Override
    public EmpleadoResponse buscarInspectorPorDni(Long dni) {
        return empleadoMapper.fromEntityToResponse(this.buscarEntidadInspectorPorDni(dni));
    }

    @Override
    public EmpleadoResponse buscarGerentePorDni(Long dni) {
        return empleadoMapper.fromEntityToResponse(this.buscarEntidadGerentePorDni(dni));
    }

    @Override
    public EmpleadoResponse crearInspector(EmpleadoRequest inspectorRequest) {
        Optional<Inspector> inspectorOptional = empleadoRepository.findInspectorByDni(inspectorRequest.getDni());
        if(inspectorOptional.isPresent()){
            throw new EntityAlreadyExistsException("Ya existe un inspector con este dni");
        }

        return empleadoMapper.fromEntityToResponse(
                empleadoRepository.save(
                        empleadoMapper.fromInspectorRequestToEntity(inspectorRequest)
                )
        );
    }

    @Override
    public EmpleadoResponse crearGerente(EmpleadoRequest empleadoRequest) {
        Optional<Inspector> inspectorOptional = empleadoRepository.findInspectorByDni(empleadoRequest.getDni());
        if(inspectorOptional.isPresent()){
            throw new EntityAlreadyExistsException("Ya existe un gerente con este dni");
        }

        return empleadoMapper.fromEntityToResponse(
                empleadoRepository.save(
                        empleadoMapper.fromGerenteRequestToEntity(empleadoRequest)
                )
        );
    }

    @Override
    public EmpleadoResponse editarInspector(EmpleadoRequest inspectorRequest) {
        Optional<Inspector> inspectorOptional = empleadoRepository.findInspectorByDni(inspectorRequest.getDni());
        if(inspectorOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se encontro el empleado a editar");
        }
        Inspector inspectorBD = inspectorOptional.get();
        inspectorBD.setNombre(inspectorRequest.getNombre());
        inspectorBD.setApellido(inspectorRequest.getApellido());

        return empleadoMapper.fromEntityToResponse(
                empleadoRepository.save(inspectorBD)
        );
    }

    @Override
    public EmpleadoResponse editarGerente(EmpleadoRequest empleadoRequest) {
        Optional<Gerente> gerenteOptional = empleadoRepository.findGerenteByDni(empleadoRequest.getDni());
        if(gerenteOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se encontro el empleado a editar");
        }
        Gerente gerenteBD = gerenteOptional.get();
        gerenteBD.setNombre(empleadoRequest.getNombre());
        gerenteBD.setApellido(empleadoRequest.getApellido());

        return empleadoMapper.fromEntityToResponse(
                empleadoRepository.save(gerenteBD)
        );
    }

    @Override
    public Empleado buscarEntidadPorDni(Long dni) {
        Optional<Empleado> empleadoOptional = empleadoRepository.findByDni(dni);
        if(empleadoOptional.isEmpty()){
            throw new EntityNotFoundException("No se encontro empleado asociado a este dni");
        }
        return empleadoOptional.get();
    }

    @Override
    public EmpleadoResponse buscarEmpleadoPorDni(Long dni) {
        Optional<Empleado> empleadoOptional = empleadoRepository.findByDni(dni);
        if(empleadoOptional.isEmpty()){
            throw new EntityNotFoundException("No se encontro empleado asociado a este dni");
        }
        return empleadoMapper.fromEntityToResponse(
                empleadoOptional.get());
    }

    @Override
    public void eliminarEmpleado(Long dni) {
        Optional<Empleado> empleadoOptionalOptional = empleadoRepository.findByDni(dni);
        if(empleadoOptionalOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se encontro el empleado a eliminar");
        }
        empleadoRepository.delete(empleadoOptionalOptional.get());
    }

}
