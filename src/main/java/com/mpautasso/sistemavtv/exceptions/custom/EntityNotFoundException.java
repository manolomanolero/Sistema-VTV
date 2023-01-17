package com.mpautasso.sistemavtv.exceptions.custom;

public class EntityNotFoundException extends RuntimeException {
    private final static String DESCRIPTION = "Entidad no encontrada (400)";

    public EntityNotFoundException(String details){
        super(DESCRIPTION + ". " + details);
    }
}
