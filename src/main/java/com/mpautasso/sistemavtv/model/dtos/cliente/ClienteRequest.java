package com.mpautasso.sistemavtv.model.dtos.cliente;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClienteRequest {
    private Long dni;
    private String nombre;
    private String apellido;
    private boolean esTitular;
    private boolean estaExento;
    private String cedulaAzul;
    private Long cuit;
}
