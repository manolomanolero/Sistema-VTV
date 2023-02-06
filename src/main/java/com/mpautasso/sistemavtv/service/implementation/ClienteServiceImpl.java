package com.mpautasso.sistemavtv.service.implementation;

import com.mpautasso.sistemavtv.exceptions.custom.EntityAlreadyExistsException;
import com.mpautasso.sistemavtv.exceptions.custom.EntityNotFoundException;
import com.mpautasso.sistemavtv.exceptions.custom.InvalidArgumentException;
import com.mpautasso.sistemavtv.exceptions.custom.NoSuchEntityExistsException;
import com.mpautasso.sistemavtv.mapper.PropietarioMapper;
import com.mpautasso.sistemavtv.model.Chofer;
import com.mpautasso.sistemavtv.model.Cliente;
import com.mpautasso.sistemavtv.model.Propietario;
import com.mpautasso.sistemavtv.model.dtos.cliente.ClienteRequest;
import com.mpautasso.sistemavtv.model.dtos.cliente.ClienteResponse;
import com.mpautasso.sistemavtv.repository.ClientesRepository;
import com.mpautasso.sistemavtv.service.ClienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClienteServiceImpl implements ClienteService {
    private final ClientesRepository clientesRepository;
    private final PropietarioMapper propietarioMapper;

    @Override
    public List<ClienteResponse> listarClientes() {
        return clientesRepository.findAll().stream()
                .map(propietarioMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClienteResponse> listarPropietarios() {
        return clientesRepository.findAllPropietarios().stream()
                .map(propietarioMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClienteResponse> listarChoferes() {
        return clientesRepository.findAllChoferes().stream()
                .map(propietarioMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Propietario buscarEntidadPropietarioPorDni(Long dni) {
        Optional<Propietario> propietarioOptional = clientesRepository.findPropietarioByDni(dni);
        if(propietarioOptional.isEmpty()){
            throw new EntityNotFoundException("No se encontro propietario asociado a este dni");
        }
        return propietarioOptional.get();
    }

    @Override
    public Chofer buscarEntidadChoferPorDni(Long dni) {
        return clientesRepository
                .findChoferByDni(dni)
                .orElseThrow(
                        () -> {throw new EntityNotFoundException("No se encontro chofer asociado a este dni");}
                );
    }

    @Override
    public ClienteResponse buscarPropietarioPorDni(Long dni) {
        Optional<Propietario> propietarioOptional = clientesRepository.findPropietarioByDni(dni);
        if(propietarioOptional.isEmpty()){
            throw new EntityNotFoundException("No se encontro propietario asociado a este dni");
        }
        return propietarioMapper.entityToResponse(propietarioOptional.get());
    }

    @Override
    public ClienteResponse crearCliente(ClienteRequest clienteRequest) {
        if(!clienteRequest.isEsTitular()){
            return crearChofer(clienteRequest);
        } else if (clienteRequest.isEstaExento()){
            return crearPropietarioExento(clienteRequest);
        }
        return crearPropietarioComun(clienteRequest);
    }

    @Override
    public ClienteResponse crearPropietarioComun(ClienteRequest clienteRequest) {
        validarCliente(clienteRequest);
        Optional<Propietario> propietarioOptional = clientesRepository.findPropietarioByDni(clienteRequest.getDni());
        if(propietarioOptional.isPresent()){
            throw new EntityAlreadyExistsException("Ya existe un propietario con este dni");
        }

        return propietarioMapper.entityToResponse(
                clientesRepository.save(
                        propietarioMapper.comunRequestToEntity(clienteRequest)
                )
        );
    }

    @Override
    public ClienteResponse crearPropietarioExento(ClienteRequest clienteRequest) {
        validarCliente(clienteRequest);

        Optional<Propietario> propietarioOptional = clientesRepository.findPropietarioByDni(clienteRequest.getDni());
        if(propietarioOptional.isPresent()){
            throw new EntityAlreadyExistsException("Ya existe un propietario con este dni");
        }

        return propietarioMapper.entityToResponse(
                clientesRepository.save(
                        propietarioMapper.exentoRequestToEntity(clienteRequest)
                )
        );
    }

    @Override
    public ClienteResponse crearChofer(ClienteRequest clienteRequest) {
        validarCliente(clienteRequest);

        Optional<Chofer> choferOptional = clientesRepository.findChoferByDni(clienteRequest.getDni());
        if(choferOptional.isPresent()){
            throw new EntityAlreadyExistsException("Ya existe un chofer con este dni");
        }

        return propietarioMapper.entityToResponse(
                clientesRepository.save(
                        propietarioMapper.clienteRequestToEntity(clienteRequest)
                )
        );
    }

    @Override
    public ClienteResponse editarPropietarioComun(ClienteRequest clienteRequest) {
        validarCliente(clienteRequest);

        Optional<Propietario> propietarioOptional = clientesRepository.findPropietarioByDni(clienteRequest.getDni());
        if(propietarioOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se encontro el propietario a editar");
        }

        Propietario propietarioActualizado = propietarioMapper.comunRequestToEntity(clienteRequest);
        propietarioActualizado.setId(propietarioOptional.get().getId());

        return propietarioMapper.entityToResponse(
                clientesRepository.save(propietarioActualizado)
        );
    }

    @Override
    public ClienteResponse editarPropietarioExcento(ClienteRequest clienteRequest) {
        validarCliente(clienteRequest);

       Optional<Propietario> propietarioOptional = clientesRepository.findPropietarioByDni(clienteRequest.getDni());
        if(propietarioOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se encontro el propietario a editar");
        }


        Propietario propietarioActualizado = propietarioMapper.exentoRequestToEntity(clienteRequest);
        propietarioActualizado.setId(propietarioOptional.get().getId());

        return propietarioMapper.entityToResponse(
                clientesRepository.save(propietarioActualizado)
        );
    }

    @Override
    public ClienteResponse editarChofer(ClienteRequest clienteRequest) {
        validarCliente(clienteRequest);

        Chofer choferBD =
                clientesRepository
                        .findChoferByDni(clienteRequest.getDni())
                        .orElseThrow(
                                () -> {throw new EntityAlreadyExistsException("No se encontro un chofer con este dni");}
                        );

        Cliente choferActualizado = propietarioMapper.clienteRequestToEntity(clienteRequest);
        choferActualizado.setId(choferBD.getId());

        return propietarioMapper.entityToResponse(
                clientesRepository.save(
                        propietarioMapper.clienteRequestToEntity(clienteRequest)
                )
        );
    }

    @Override
    public void eliminarPropietario(Long dni) {
        Optional<Propietario> propietarioOptional = clientesRepository.findPropietarioByDni(dni);
        if(propietarioOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se pudo eliminar ningun propietario ya que no se encontro ninguno asociado al dni");
        }
        clientesRepository.delete(propietarioOptional.get());
    }

    @Override
    public void eliminarChofer(Long dni) {
        Optional<Chofer> choferOptional = clientesRepository.findChoferByDni(dni);
        if(choferOptional.isEmpty()){
            throw new NoSuchEntityExistsException("No se pudo eliminar ningun chofer ya que no se encontro ninguno asociado al dni");
        }
        clientesRepository.delete(choferOptional.get());
    }

    private void validarCliente(ClienteRequest clienteRequest){
        if(clienteRequest.getDni() < 100000 || clienteRequest.getDni() > 1000000000){
            throw new InvalidArgumentException("El dni no puede ser menor a 100.000 o mayor a 1.000.000.000");
        }
        if(clienteRequest.getNombre().isBlank() || clienteRequest.getNombre().trim().length() <= 2){
            throw new InvalidArgumentException("El nombre no puede tener menos de 2 caracteres");
        }
        if(clienteRequest.getApellido().isBlank() || clienteRequest.getApellido().trim().length() <= 2){
            throw new InvalidArgumentException("El apellido no puede tener menos de 2 caracteres");
        }

        if (!clienteRequest.isEsTitular()){

            if(clienteRequest.getCedulaAzul().isBlank() || clienteRequest.getNombre().trim().length() <= 6){
                throw new InvalidArgumentException("La cedula azul no puede tener menos de 6 caracteres");
            }

        } else if (clienteRequest.isEstaExento()){
            if(clienteRequest.getCuit() < 20000000000L || clienteRequest.getCuit() > 35000000000L){
                throw new InvalidArgumentException("El cuit no está dentro del rango de validez" +
                        "no puede ser menor a 20.000.000.000 o mayor a 35.000.000.000");
            }
        }
    }
}
