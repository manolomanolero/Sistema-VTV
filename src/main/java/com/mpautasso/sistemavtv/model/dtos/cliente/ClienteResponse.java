package com.mpautasso.sistemavtv.model.dtos.cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteResponse {
    private Long dni;
    private String nombre;
    private String apellido;
    private String tipo;
}
