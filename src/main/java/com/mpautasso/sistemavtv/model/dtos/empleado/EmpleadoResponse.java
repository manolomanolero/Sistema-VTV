package com.mpautasso.sistemavtv.model.dtos.empleado;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoResponse {
    private Long numeroLegajo;
    private Long dni;
    private String nombre;
    private String apellido;
    private String cargo;
}
