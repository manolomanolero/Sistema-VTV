package com.mpautasso.sistemavtv.exceptions.custom;

public class EntityAlreadyExistsException extends RuntimeException {
    private final static String DESCRIPTION = "Entidad ya existe (400)";

    public EntityAlreadyExistsException(String details){
        super(DESCRIPTION + ". " + details);
    }
}
