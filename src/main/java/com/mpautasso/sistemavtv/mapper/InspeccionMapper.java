package com.mpautasso.sistemavtv.mapper;

import com.mpautasso.sistemavtv.model.Automovil;
import com.mpautasso.sistemavtv.model.EstadosInspeccion;
import com.mpautasso.sistemavtv.model.Inspeccion;
import com.mpautasso.sistemavtv.model.Inspector;
import com.mpautasso.sistemavtv.model.dtos.automovil.AutomovilResponse;
import com.mpautasso.sistemavtv.model.dtos.inspeccion.InspeccionRequest;
import com.mpautasso.sistemavtv.model.dtos.inspeccion.InspeccionResponse;
import com.mpautasso.sistemavtv.model.dtos.inspeccion.SimpleInspeccionResponse;
import com.mpautasso.sistemavtv.model.dtos.inspector.InspectorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class InspeccionMapper {
    @Autowired
    private AutomovilMapper automovilMapper;

    @Autowired
    private InspectorMapper inspectorMapper;

    public Inspeccion fromRequestToEntity(InspeccionRequest inspeccionRequest, Automovil automovil, Inspector inspector){
        boolean estaExento = automovil.getPropietario().tipoPropietario().equals("exento");
        Date fecha = (inspeccionRequest.getFecha() == null) ?
                new Date() : inspeccionRequest.getFecha();
        return new Inspeccion(
                null,
                EstadosInspeccion.valueOf(inspeccionRequest.getEstado()),
                estaExento,
                fecha,
                inspector,
                automovil
        );
    }

    public InspeccionResponse fromEntityToResponse(Inspeccion inspeccion){
        InspectorResponse inspectorResponse = inspectorMapper.fromEntityToResponse(inspeccion.getInspector());
        AutomovilResponse automovilResponse = automovilMapper.fromEntityToResponse(inspeccion.getAutomovil());

        return new InspeccionResponse(
                inspeccion.getNumeroInspeccion(),
                inspeccion.getEstado(),
                inspeccion.getEstaExento(),
                inspeccion.getFecha(),
                inspectorResponse,
                automovilResponse
        );
    }

    public SimpleInspeccionResponse fromEntityToSimpleResponse(Inspeccion inspeccion){
        InspectorResponse inspectorResponse = inspectorMapper.fromEntityToResponse(inspeccion.getInspector());

        return new SimpleInspeccionResponse(
                inspeccion.getNumeroInspeccion(),
                inspeccion.getEstado(),
                inspeccion.getEstaExento(),
                inspeccion.getFecha(),
                inspectorResponse
        );
    }
}
