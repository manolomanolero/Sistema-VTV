package com.mpautasso.sistemavtv.model.dtos.automovil;

import com.mpautasso.sistemavtv.model.dtos.inspeccion.SimpleInspeccionResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AutomovilInspeccionResponse {
    private String marca;
    private String modelo;
    private String dominio;
    private List<SimpleInspeccionResponse> inspecciones;
}
