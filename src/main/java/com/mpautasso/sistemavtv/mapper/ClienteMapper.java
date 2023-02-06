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
        return new ClienteResponse(
                cliente.getDni(), cliente.getNombre(), cliente.getApellido(), cliente.tipoDeCliente()
        );
    }
}