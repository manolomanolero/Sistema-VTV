package com.mpautasso.sistemavtv.mapper;

import com.mpautasso.sistemavtv.model.Vehiculo;
import com.mpautasso.sistemavtv.model.enums.EstadosInspeccion;
import com.mpautasso.sistemavtv.model.Inspeccion;
import com.mpautasso.sistemavtv.model.Inspector;
import com.mpautasso.sistemavtv.model.dtos.vehiculo.VehiculoResponse;
import com.mpautasso.sistemavtv.model.dtos.inspeccion.InspeccionRequest;
import com.mpautasso.sistemavtv.model.dtos.inspeccion.InspeccionResponse;
import com.mpautasso.sistemavtv.model.dtos.inspeccion.SimpleInspeccionResponse;
import com.mpautasso.sistemavtv.model.dtos.empleado.EmpleadoResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class InspeccionMapper {
    @Autowired
    private VehiculoMapper vehiculoMapper;

    @Autowired
    private EmpleadoMapper empleadoMapper;

    public Inspeccion fromRequestToEntity(InspeccionRequest inspeccionRequest, Vehiculo vehiculo, Inspector inspector){
        boolean estaExento = vehiculo.getPropietario().tipoDeCliente().equals("exento");
        Date fecha = (inspeccionRequest.getFecha() == null) ?
                new Date() : inspeccionRequest.getFecha();
        return new Inspeccion(
                null,
                EstadosInspeccion.valueOf(inspeccionRequest.getEstado()),
                estaExento,
                fecha,
                inspector,
                vehiculo,
                inspeccionRequest.getObservaciones()
        );
    }

    public InspeccionResponse fromEntityToResponse(Inspeccion inspeccion){
        EmpleadoResponse empleadoResponse = empleadoMapper.fromEntityToResponse(inspeccion.getInspector());
        VehiculoResponse vehiculoResponse = vehiculoMapper.fromEntityToResponse(inspeccion.getVehiculo());

        return new InspeccionResponse(
                inspeccion.getNumeroInspeccion(),
                inspeccion.getEstado(),
                inspeccion.getEstaExento(),
                inspeccion.getFecha(),
                empleadoResponse,
                vehiculoResponse,
                inspeccion.getObservaciones()
        );
    }

    public SimpleInspeccionResponse fromEntityToSimpleResponse(Inspeccion inspeccion){
        EmpleadoResponse empleadoResponse = empleadoMapper.fromEntityToResponse(inspeccion.getInspector());

        return new SimpleInspeccionResponse(
                inspeccion.getNumeroInspeccion(),
                inspeccion.getEstado(),
                inspeccion.getEstaExento(),
                inspeccion.getFecha(),
                empleadoResponse
        );
    }
}
