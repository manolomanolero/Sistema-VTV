package com.mpautasso.sistemavtv.model.dtos.vehiculo;

import com.mpautasso.sistemavtv.model.dtos.inspeccion.SimpleInspeccionResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoInspeccionResponse {
    private String dominio;
    private String numeroMotor;
    private String numeroChasis;
    private String marca;
    private String modelo;
    private String version;
    private List<SimpleInspeccionResponse> inspecciones;
}
