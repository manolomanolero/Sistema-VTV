package com.mpautasso.sistemavtv.model.dtos.vehiculo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoRequest {
    private String dominio;
    private String numeroMotor;
    private String numeroChasis;
    private Long versionId;
    private Long propietarioDni;
    private Long choferDni;
}
