package com.mpautasso.sistemavtv.exceptions.custom;

public class InvalidArgumentException extends RuntimeException{
    private final static String DESCRIPTION = "Argumento invalido (400)";

    public InvalidArgumentException(String details){
        super(DESCRIPTION + ". " + details);
    }
}
