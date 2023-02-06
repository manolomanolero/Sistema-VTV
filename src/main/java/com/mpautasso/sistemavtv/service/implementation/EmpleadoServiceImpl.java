package com.mpautasso.sistemavtv.service.implementation;

import com.mpautasso.sistemavtv.exceptions.custom.EntityAlreadyExistsException;
import com.mpautasso.sistemavtv.exceptions.custom.EntityNotFoundException;
import com.mpautasso.sistemavtv.exceptions.custom.InvalidArgumentException;
import com.mpautasso.sistemavtv.exceptions.custom.NoSuchEntityExistsException;
import com.mpautasso.sistemavtv.mapper.EmpleadoMapper;
import com.mpautasso.sistemavtv.model.Empleado;
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
    public List<EmpleadoResponse> listarInspectores() {
        return empleadoRepository.findAll().stream()
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
    public Empleado buscarEntidadPorDni(Long dni) {
        Optional<Empleado> inspectorOptional = empleadoRepository.findByDni(dni);
        if(inspectorOptional.isEmpty()){
            throw new EntityNotFoundException("No se encontro empleado asociado a este dni");
        }
        return inspectorOptional.get();
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
    public EmpleadoResponse crearEmpleado(EmpleadoRequest inspectorRequest) {
        validarDatosDeEmpleado(inspectorRequest);

        Optional<Inspector> inspectorOptional = empleadoRepository.findInspectorByDni(inspectorRequest.getDni());
        if(inspectorOptional.isPresent()){
            throw new EntityAlreadyExistsException("Ya existe un inspector con este dni");
        }

        return empleadoMapper.fromEntityToResponse(
                empleadoRepository.save(
                        empleadoMapper.fromRequestToEntity(inspectorRequest)
                )
        );
    }

    @Override
    public EmpleadoResponse editarEmpleado(EmpleadoRequest empleadoRequest) {
        validarDatosDeEmpleado(empleadoRequest);

        Optional<Inspector> empleadoOptional = empleadoRepository.findInspectorByDni(empleadoRequest.getDni());
        if(empleadoOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se encontro el empleado a editar");
        }
        Inspector inspectorBD = empleadoOptional.get();
        inspectorBD.setNombre(empleadoRequest.getNombre());
        inspectorBD.setApellido(empleadoRequest.getApellido());

        return empleadoMapper.fromEntityToResponse(
                empleadoRepository.save(inspectorBD)
        );
    }

    @Override
    public void eliminarEmpleado(Long dni) {
        Optional<Inspector> inspectorOptional = empleadoRepository.findInspectorByDni(dni);
        if(inspectorOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se encontro el empleado a eliminar");
        }
        empleadoRepository.delete(inspectorOptional.get());
    }

    private void validarDatosDeEmpleado(EmpleadoRequest inspectorRequest){
        if(inspectorRequest.getDni() < 1){
            throw new InvalidArgumentException("El dni no puede ser 0 o un numero negativo");
        }
        if(inspectorRequest.getNombre().isBlank()){
            throw new InvalidArgumentException("El nombre no puede ser vacío o solo contener espacios");
        }
        if(inspectorRequest.getApellido().isBlank()){
            throw new InvalidArgumentException("El apellido no puede ser vacío o solo contener espacios");
        }
    }
}
