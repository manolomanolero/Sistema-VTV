package com.mpautasso.sistemavtv.model.dtos.vehiculo;

import com.mpautasso.sistemavtv.model.dtos.cliente.ClienteResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoResponse {
    private String dominio;
    private String numeroMotor;
    private String numeroChasis;
    private String marca;
    private String modelo;
    private String version;
    private ClienteResponse propietario;
    private ClienteResponse chofer;

}
