package com.mpautasso.sistemavtv.model.dtos.inspeccion;

import com.mpautasso.sistemavtv.model.EstadosInspeccion;
import com.mpautasso.sistemavtv.model.dtos.inspector.InspectorResponse;
import com.mpautasso.sistemavtv.model.dtos.automovil.AutomovilResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InspeccionResponse {
    private Long numeroInspeccion;

    private EstadosInspeccion estado;
    private boolean estaExento;
    private Date fecha;

    private InspectorResponse inspector;

    private AutomovilResponse automovil;
}
