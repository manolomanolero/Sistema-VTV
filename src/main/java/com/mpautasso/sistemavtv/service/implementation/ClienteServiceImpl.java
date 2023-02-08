package com.mpautasso.sistemavtv.service.implementation;

import com.mpautasso.sistemavtv.exceptions.custom.EntityAlreadyExistsException;
import com.mpautasso.sistemavtv.exceptions.custom.EntityNotFoundException;
import com.mpautasso.sistemavtv.exceptions.custom.InvalidArgumentException;
import com.mpautasso.sistemavtv.exceptions.custom.NoSuchEntityExistsException;
import com.mpautasso.sistemavtv.mapper.ClienteMapper;
import com.mpautasso.sistemavtv.model.Chofer;
import com.mpautasso.sistemavtv.model.Cliente;
import com.mpautasso.sistemavtv.model.Propietario;
import com.mpautasso.sistemavtv.model.PropietarioExento;
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
    private final ClienteMapper clienteMapper;

    @Override
    public List<ClienteResponse> listarClientes() {
        return clientesRepository.findAll().stream()
                .map(clienteMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClienteResponse> listarPropietarios() {
        return clientesRepository.findAllPropietarios().stream()
                .map(clienteMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClienteResponse> listarChoferes() {
        return clientesRepository.findAllChoferes().stream()
                .map(clienteMapper::entityToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Propietario buscarEntidadPropietarioPorDni(Long dni) {
        Optional<Propietario> propietarioOptional = clientesRepository.findPropietarioByDni(dni);
        if (propietarioOptional.isEmpty()) {
            throw new EntityNotFoundException("No se encontro propietario asociado a este dni");
        }
        return propietarioOptional.get();
    }

    @Override
    public Chofer buscarEntidadChoferPorDni(Long dni) {
        return clientesRepository
                .findChoferByDni(dni)
                .orElseThrow(
                        () -> {
                            throw new EntityNotFoundException("No se encontro chofer asociado a este dni");
                        }
                );
    }

    @Override
    public ClienteResponse buscarPropietarioPorDni(Long dni) {
        Optional<Propietario> propietarioOptional = clientesRepository.findPropietarioByDni(dni);
        if (propietarioOptional.isEmpty()) {
            throw new EntityNotFoundException("No se encontro propietario asociado a este dni");
        }
        return clienteMapper.entityToResponse(propietarioOptional.get());
    }

    @Override
    public ClienteResponse crearCliente(ClienteRequest clienteRequest) {
        if (!clienteRequest.isEsTitular()) {
            return crearChofer(clienteRequest);
        } else if (clienteRequest.isEstaExento()) {
            return crearPropietarioExento(clienteRequest);
        }
        return crearPropietarioComun(clienteRequest);
    }

    @Override
    public ClienteResponse crearPropietarioComun(ClienteRequest clienteRequest) {
        validarCliente(clienteRequest);
        Optional<Propietario> propietarioOptional = clientesRepository.findPropietarioByDni(clienteRequest.getDni());
        if (propietarioOptional.isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe un propietario con este dni");
        }

        return clienteMapper.entityToResponse(
                clientesRepository.save(
                        clienteMapper.comunRequestToEntity(clienteRequest)
                )
        );
    }

    @Override
    public ClienteResponse crearPropietarioExento(ClienteRequest clienteRequest) {
        validarCliente(clienteRequest);

        Optional<Propietario> propietarioOptional = clientesRepository.findPropietarioByDni(clienteRequest.getDni());
        if (propietarioOptional.isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe un propietario con este dni");
        }

        return clienteMapper.entityToResponse(
                clientesRepository.save(
                        clienteMapper.exentoRequestToEntity(clienteRequest)
                )
        );
    }

    @Override
    public ClienteResponse crearChofer(ClienteRequest clienteRequest) {
        validarCliente(clienteRequest);

        Optional<Chofer> choferOptional = clientesRepository.findChoferByDni(clienteRequest.getDni());
        if (choferOptional.isPresent()) {
            throw new EntityAlreadyExistsException("Ya existe un chofer con este dni");
        }

        return clienteMapper.entityToResponse(
                clientesRepository.save(
                        clienteMapper.choferRequestToEntity(clienteRequest)
                )
        );
    }

    @Override
    public ClienteResponse editarPropietarioComun(ClienteRequest clienteRequest) {
        validarCliente(clienteRequest);

        Optional<Propietario> propietarioOptional = clientesRepository.findPropietarioByDni(clienteRequest.getDni());
        if (propietarioOptional.isEmpty()) {
            throw new NoSuchEntityExistsException("No se encontro el propietario a editar");
        }

        Propietario propietarioActualizado = clienteMapper.comunRequestToEntity(clienteRequest);

        if (!propietarioOptional.get().getClass().getSimpleName().equals("PropietarioComun")) {
            clientesRepository.updateExentoToComun(
                    propietarioOptional.get().getId(),
                    propietarioActualizado.getNombre(),
                    propietarioActualizado.getApellido());

            return clienteMapper.entityToResponse(clientesRepository.findPropietarioByDni(clienteRequest.getDni()).get());
        }

        propietarioActualizado.setId(propietarioOptional.get().getId());

        return clienteMapper.entityToResponse(
                clientesRepository.save(propietarioActualizado)
        );
    }

    @Override
    public ClienteResponse editarPropietarioExcento(ClienteRequest clienteRequest) {
        validarCliente(clienteRequest);

        Optional<Propietario> propietarioOptional = clientesRepository.findPropietarioByDni(clienteRequest.getDni());
        if (propietarioOptional.isEmpty()) {
            throw new NoSuchEntityExistsException("No se encontro el propietario a editar");
        }


        PropietarioExento propietarioActualizado = (PropietarioExento) clienteMapper.exentoRequestToEntity(clienteRequest);

        if (!propietarioOptional.get().getClass().getSimpleName().equals("PropietarioExento")) {
            clientesRepository.updateComunToExento(
                    propietarioOptional.get().getId(),
                    propietarioActualizado.getNombre(),
                    propietarioActualizado.getApellido(),
                    propietarioActualizado.getCuit());

            return clienteMapper.entityToResponse(clientesRepository.findPropietarioByDni(clienteRequest.getDni()).get());
        }

        propietarioActualizado.setId(propietarioOptional.get().getId());

        return clienteMapper.entityToResponse(
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
                                () -> {
                                    throw new EntityAlreadyExistsException("No se encontro un chofer con este dni");
                                }
                        );

        Cliente choferActualizado = clienteMapper.choferRequestToEntity(clienteRequest);
        choferActualizado.setId(choferBD.getId());

        return clienteMapper.entityToResponse(
                clientesRepository.save(choferActualizado)
        );
    }

    @Override
    public void eliminarPropietario(Long dni) {
        Optional<Propietario> propietarioOptional = clientesRepository.findPropietarioByDni(dni);
        if (propietarioOptional.isEmpty()) {
            throw new NoSuchEntityExistsException("No se pudo eliminar ningun propietario ya que no se encontro ninguno asociado al dni");
        }
        clientesRepository.delete(propietarioOptional.get());
    }

    @Override
    public void eliminarChofer(Long dni) {
        Optional<Chofer> choferOptional = clientesRepository.findChoferByDni(dni);
        if (choferOptional.isEmpty()) {
            throw new NoSuchEntityExistsException("No se pudo eliminar ningun chofer ya que no se encontro ninguno asociado al dni");
        }
        clientesRepository.delete(choferOptional.get());
    }

    private void validarCliente(ClienteRequest clienteRequest) {
        if (clienteRequest.getDni() < 1000000 || clienteRequest.getDni() > 1000000000) {
            throw new InvalidArgumentException("El dni no puede ser menor a 1.000.000 o mayor a 1.000.000.000");
        }
        if (clienteRequest.getNombre().isBlank() || clienteRequest.getNombre().trim().length() <= 2) {
            throw new InvalidArgumentException("El nombre no puede tener menos de 2 caracteres");
        }
        if (clienteRequest.getApellido().isBlank() || clienteRequest.getApellido().trim().length() <= 2) {
            throw new InvalidArgumentException("El apellido no puede tener menos de 2 caracteres");
        }

        if (!clienteRequest.isEsTitular()) {

            if (clienteRequest.getCedulaAzul().isBlank() || clienteRequest.getCedulaAzul().trim().length() <= 6) {
                throw new InvalidArgumentException("La cedula azul no puede tener menos de 6 caracteres");
            }

        } else if (clienteRequest.isEstaExento()) {
            if (clienteRequest.getCuit() < 2000000000L || clienteRequest.getCuit() > 35000000000L) {
                throw new InvalidArgumentException("El cuit no está dentro del rango de validez" +
                        "no puede ser menor a 2.000.000.000 o mayor a 35.000.000.000");
            }
        }
    }
}
