package com.mpautasso.sistemavtv.model.dtos.inspeccion;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InspeccionRequest {
    private Long numeroInspeccion;

    private String estado;
    private Date fecha;

    private Long inspectorDni;

    private String dominioVehiculo;
    private String observaciones;
}
