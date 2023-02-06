package com.mpautasso.sistemavtv.model.dtos.inspeccion;

import com.mpautasso.sistemavtv.model.enums.EstadosInspeccion;
import com.mpautasso.sistemavtv.model.dtos.empleado.EmpleadoResponse;
import com.mpautasso.sistemavtv.model.dtos.vehiculo.VehiculoResponse;
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

    private EmpleadoResponse inspector;

    private VehiculoResponse vehiculo;
    private String observaciones;
}
