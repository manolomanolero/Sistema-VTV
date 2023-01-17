package com.mpautasso.sistemavtv.service.implementation;

import com.mpautasso.sistemavtv.exceptions.custom.EntityAlreadyExistsException;
import com.mpautasso.sistemavtv.exceptions.custom.EntityNotFoundException;
import com.mpautasso.sistemavtv.exceptions.custom.InvalidArgumentException;
import com.mpautasso.sistemavtv.exceptions.custom.NoSuchEntityExistsException;
import com.mpautasso.sistemavtv.mapper.PropietarioMapper;
import com.mpautasso.sistemavtv.model.Propietario;
import com.mpautasso.sistemavtv.model.dtos.propietario.PropietarioRequest;
import com.mpautasso.sistemavtv.model.dtos.propietario.PropietarioResponse;
import com.mpautasso.sistemavtv.repository.PropietariosRepository;
import com.mpautasso.sistemavtv.service.PropietarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropietarioServiceImpl implements PropietarioService {
    private final PropietariosRepository propietariosRepository;
    private final PropietarioMapper propietarioMapper;

    @Override
    public List<PropietarioResponse> listarPropietarios() {
        return propietariosRepository.findAll().stream()
                .map(propietarioMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Propietario buscarEntidadPorDni(Long dni) {
        Optional<Propietario> propietarioOptional = propietariosRepository.findByDni(dni);
        if(propietarioOptional.isEmpty()){
            throw new EntityNotFoundException("No se encontro propietario asociado a este dni");
        }
        return propietarioOptional.get();
    }

    @Override
    public PropietarioResponse buscarPropietarioPorDni(Long dni) {
        Optional<Propietario> propietarioOptional = propietariosRepository.findByDni(dni);
        if(propietarioOptional.isEmpty()){
            throw new EntityNotFoundException("No se encontro propietario asociado a este dni");
        }
        return propietarioMapper.entityToResponse(propietarioOptional.get());
    }

    @Override
    public PropietarioResponse crearPropietarioComun(PropietarioRequest propietarioRequest) {
        validarPropietario(propietarioRequest);
        Optional<Propietario> propietarioOptional = propietariosRepository.findByDni(propietarioRequest.getDni());
        if(propietarioOptional.isPresent()){
            throw new EntityAlreadyExistsException("Ya existe un propietario con este dni");
        }

        return propietarioMapper.entityToResponse(
                propietariosRepository.save(
                        propietarioMapper.comunRequestToEntity(propietarioRequest)
                )
        );
    }

    @Override
    public PropietarioResponse crearPropietarioExento(PropietarioRequest propietarioRequest) {
        validarPropietario(propietarioRequest);

        Optional<Propietario> propietarioOptional = propietariosRepository.findByDni(propietarioRequest.getDni());
        if(propietarioOptional.isPresent()){
            throw new EntityAlreadyExistsException("Ya existe un propietario con este dni");
        }

        return propietarioMapper.entityToResponse(
                propietariosRepository.save(
                        propietarioMapper.exentoRequestToEntity(propietarioRequest)
                )
        );
    }

    @Override
    public PropietarioResponse editarPropietarioComun(PropietarioRequest propietarioRequest) {
        validarPropietario(propietarioRequest);

        Optional<Propietario> propietarioOptional = propietariosRepository.findByDni(propietarioRequest.getDni());
        if(propietarioOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se encontro el propietario a editar");
        }

        Propietario propietarioActualizado = propietarioMapper.comunRequestToEntity(propietarioRequest);
        propietarioActualizado.setId(propietarioOptional.get().getId());

        return propietarioMapper.entityToResponse(
                propietariosRepository.save(propietarioActualizado)
        );
    }

    @Override
    public PropietarioResponse editarPropietarioExcento(PropietarioRequest propietarioRequest) {
        validarPropietario(propietarioRequest);

       Optional<Propietario> propietarioOptional = propietariosRepository.findByDni(propietarioRequest.getDni());
        if(propietarioOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se encontro el propietario a editar");
        }


        Propietario propietarioActualizado = propietarioMapper.exentoRequestToEntity(propietarioRequest);
        propietarioActualizado.setId(propietarioOptional.get().getId());

        return propietarioMapper.entityToResponse(
                propietariosRepository.save(propietarioActualizado)
        );
    }

    @Override
    public void eliminarPropietario(Long dni) {
        Optional<Propietario> propietarioOptional = propietariosRepository.findByDni(dni);
        if(propietarioOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se encontro el propietario a eliminar");
        }
        propietariosRepository.delete(propietarioOptional.get());
    }

    private void validarPropietario(PropietarioRequest propietarioRequest){
        if(propietarioRequest.getDni() < 1){
            throw new InvalidArgumentException("El dni no puede ser 0 o un numero negativo");
        }
        if(propietarioRequest.getNombre().isBlank()){
            throw new InvalidArgumentException("El nombre no puede ser vacío o solo contener espacios");
        }
        if(propietarioRequest.getApellido().isBlank()){
            throw new InvalidArgumentException("El apellido no puede ser vacío o solo contener espacios");
        }
    }
}
