package com.mpautasso.sistemavtv.mapper;

import com.mpautasso.sistemavtv.model.*;
import com.mpautasso.sistemavtv.model.dtos.cliente.ClienteResponse;
import com.mpautasso.sistemavtv.model.dtos.vehiculo.VehiculoRequest;
import com.mpautasso.sistemavtv.model.dtos.vehiculo.VehiculoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehiculoMapper {
    @Autowired
    private ClienteMapper clienteMapper;

    public Vehiculo fromAutomovilRequestToEntity(VehiculoRequest vehiculoRequest, Version version, Propietario propietario, Chofer chofer) {
        return new Automovil(null,
                vehiculoRequest.getDominio(),
                vehiculoRequest.getNumeroMotor(),
                vehiculoRequest.getNumeroChasis(),
                version,
                propietario,
                chofer);
    }

    public Vehiculo fromMotoRequestToEntity(VehiculoRequest vehiculoRequest, Version version, Propietario propietario, Chofer chofer) {
        return new Moto(null,
                vehiculoRequest.getDominio(),
                vehiculoRequest.getNumeroMotor(),
                vehiculoRequest.getNumeroChasis(),
                version,
                propietario,
                chofer);
    }

    public VehiculoResponse fromEntityToResponse(Vehiculo vehiculo) {
        ClienteResponse propietarioResponse = clienteMapper.entityToResponse(vehiculo.getPropietario());
        ClienteResponse choferResponse = (vehiculo.getChofer() != null) ?
                clienteMapper.entityToResponse(vehiculo.getPropietario())
                : null;

        return new VehiculoResponse(
                vehiculo.getDominio(),
                vehiculo.getNumeroMotor(),
                vehiculo.getNumeroChasis(),
                vehiculo.getVersion().getModelo().getMarca().getNombre(),
                vehiculo.getVersion().getModelo().getNombre(),
                vehiculo.getVersion().getNombre(),
                propietarioResponse,
                choferResponse);
    }

    public VehiculoResponse inspeccionAutoToResponse(UltimaInspeccionVehiculo inspeccionAutomovil) {
        return fromEntityToResponse(inspeccionAutomovil.getVehiculo());
    }


}
