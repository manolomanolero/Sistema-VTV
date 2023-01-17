package com.mpautasso.sistemavtv.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorMessage {
    private String exception;
    private String mensaje;
    private String ruta;

    public ErrorMessage(Exception exception, String ruta){
        this.exception = exception.getClass().getSimpleName();
        this.mensaje = exception.getMessage();
        this.ruta = ruta;
    }

}
