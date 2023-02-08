package com.mpautasso.sistemavtv.mapper;

import com.mpautasso.sistemavtv.model.*;
import com.mpautasso.sistemavtv.model.dtos.cliente.ClienteRequest;
import com.mpautasso.sistemavtv.model.dtos.cliente.ClienteResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClienteMapper {
    @Autowired
    private ModelMapper modelMapper;

    public Propietario comunRequestToEntity(ClienteRequest clienteRequest){
        return modelMapper.map(clienteRequest, PropietarioComun.class);
    }

    public Propietario exentoRequestToEntity(ClienteRequest clienteRequest){
        return modelMapper.map(clienteRequest, PropietarioExento.class);
    }

    public Chofer choferRequestToEntity(ClienteRequest clienteRequest){
        return modelMapper.map(clienteRequest, Chofer.class);
    }

    public Cliente clienteRequestToEntity(ClienteRequest clienteRequest){
        if(clienteRequest.isEsTitular()){
            if (clienteRequest.isEstaExento()){
                return new PropietarioExento(
                        clienteRequest.getDni(),
                        clienteRequest.getNombre(),
                        clienteRequest.getApellido(),
                        clienteRequest.getCuit()
                        );
            } else {
                return new PropietarioComun(
                        clienteRequest.getDni(),
                        clienteRequest.getNombre(),
                        clienteRequest.getApellido()
                );
            }

        }
        return new Chofer(
                    clienteRequest.getDni(),
                    clienteRequest.getNombre(),
                    clienteRequest.getApellido(),
                    clienteRequest.getCedulaAzul()
            );

    }

    public ClienteResponse entityToResponse(Cliente cliente){
        if(cliente.getClass().getSimpleName().equals("PropietarioComun")) {
            return new ClienteResponse(
                    cliente.getDni(), cliente.getNombre(), cliente.getApellido(),
                    cliente.tipoDeCliente(), null, null
            );
        } else if(cliente.getClass().getSimpleName().equals("PropietarioExento")) {
            return new ClienteResponse(
                    cliente.getDni(), cliente.getNombre(), cliente.getApellido(),
                    cliente.tipoDeCliente(), null, ((PropietarioExento) cliente).getCuit()
            );
        } else if(cliente.getClass().getSimpleName().equals("Chofer")){
            return new ClienteResponse(
                    cliente.getDni(), cliente.getNombre(), cliente.getApellido(), cliente.tipoDeCliente(),
                    ((Chofer) cliente).getCedulaAzul(), null
            );
        }
        return new ClienteResponse(
                cliente.getDni(), cliente.getNombre(), cliente.getApellido(),
                cliente.tipoDeCliente(), null, null
        );
    }

}
