package com.mpautasso.sistemavtv.model.dtos.automovil;

import com.mpautasso.sistemavtv.model.dtos.propietario.PropietarioResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutomovilResponse {
    private String marca;
    private String modelo;

    private String dominio;

    private PropietarioResponse propietario;
}
