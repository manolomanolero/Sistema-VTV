package com.mpautasso.sistemavtv.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
public abstract class Propietario extends Cliente{
    public Propietario(Long dni, String nombre, String apellido) {
        super(dni, nombre, apellido);
    }

    public Propietario(Long id, Long dni, String nombre, String apellido) {
        super(id, dni, nombre, apellido);
    }
}
