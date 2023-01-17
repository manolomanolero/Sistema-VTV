package com.mpautasso.sistemavtv.service.implementation;

import com.mpautasso.sistemavtv.exceptions.custom.EntityAlreadyExistsException;
import com.mpautasso.sistemavtv.exceptions.custom.EntityNotFoundException;
import com.mpautasso.sistemavtv.exceptions.custom.InvalidArgumentException;
import com.mpautasso.sistemavtv.exceptions.custom.NoSuchEntityExistsException;
import com.mpautasso.sistemavtv.mapper.InspectorMapper;
import com.mpautasso.sistemavtv.model.Inspector;
import com.mpautasso.sistemavtv.model.dtos.inspector.InspectorRequest;
import com.mpautasso.sistemavtv.model.dtos.inspector.InspectorResponse;
import com.mpautasso.sistemavtv.repository.InspectorRepository;
import com.mpautasso.sistemavtv.service.InspectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InspectorServiceImpl implements InspectorService {
    private final InspectorRepository inspectorRepository;
    private final InspectorMapper inspectorMapper;

    @Override
    public List<InspectorResponse> listarInspectores() {
        return inspectorRepository.findAll().stream()
                .map(inspectorMapper::fromEntityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Inspector buscarEntidadPorDni(Long dni) {
        Optional<Inspector> inspectorOptional = inspectorRepository.findByDni(dni);
        if(inspectorOptional.isEmpty()){
            throw new EntityNotFoundException("No se encontro inspector asociado a este dni");
        }
        return inspectorOptional.get();
    }

    @Override
    public InspectorResponse buscarInspectorPorDni(Long dni) {
        Optional<Inspector> inspectorOptional = inspectorRepository.findByDni(dni);
        if(inspectorOptional.isEmpty()){
            throw new EntityNotFoundException("No se encontro inspector asociado a este dni");
        }
        return inspectorMapper.fromEntityToResponse(
                inspectorOptional.get());
    }

    @Override
    public InspectorResponse crearInspector(InspectorRequest inspectorRequest) {
        validarDatosDeInspector(inspectorRequest);

        Optional<Inspector> inspectorOptional = inspectorRepository.findByDni(inspectorRequest.getDni());
        if(inspectorOptional.isPresent()){
            throw new EntityAlreadyExistsException("Ya existe un inspector con este dni");
        }

        return inspectorMapper.fromEntityToResponse(
                inspectorRepository.save(
                        inspectorMapper.fromRequestToEntity(inspectorRequest)
                )
        );
    }

    @Override
    public InspectorResponse editarInspector(InspectorRequest inspectorRequest) {
        validarDatosDeInspector(inspectorRequest);

        Optional<Inspector> inspectorOptional = inspectorRepository.findByDni(inspectorRequest.getDni());
        if(inspectorOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se encontro el inspector a editar");
        }
        Inspector inspectorBD = inspectorOptional.get();
        inspectorBD.setNombre(inspectorRequest.getNombre());
        inspectorBD.setApellido(inspectorRequest.getApellido());

        return inspectorMapper.fromEntityToResponse(
                inspectorRepository.save(inspectorBD)
        );
    }

    @Override
    public void eliminarInspector(Long dni) {
        Optional<Inspector> inspectorOptional = inspectorRepository.findByDni(dni);
        if(inspectorOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se encontro el inspector a eliminar");
        }
        inspectorRepository.delete(inspectorOptional.get());
    }

    private void validarDatosDeInspector(InspectorRequest inspectorRequest){
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
