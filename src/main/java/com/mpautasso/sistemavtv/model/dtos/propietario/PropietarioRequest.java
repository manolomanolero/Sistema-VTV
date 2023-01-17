package com.mpautasso.sistemavtv.model.dtos.propietario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PropietarioRequest {
    private Long dni;
    private String nombre;
    private String apellido;
}
