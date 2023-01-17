package com.mpautasso.sistemavtv.exceptions.custom;

public class NoSuchEntityExistsException extends RuntimeException {
    private final static String DESCRIPTION = "No existe la entidad (400)";

    public NoSuchEntityExistsException(String details){
        super(DESCRIPTION + ". " + details);
    }
}
