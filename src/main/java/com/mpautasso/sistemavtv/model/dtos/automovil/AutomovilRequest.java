package com.mpautasso.sistemavtv.model.dtos.automovil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutomovilRequest {
    private String marca;
    private String modelo;

    private String dominio;

    private Long propietarioDni;
}
