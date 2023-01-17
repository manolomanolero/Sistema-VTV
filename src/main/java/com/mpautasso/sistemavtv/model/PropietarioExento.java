package com.mpautasso.sistemavtv.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@DiscriminatorValue("Exento")
public class PropietarioExento extends Propietario {
    @Override
    public String tipoPropietario() {
        return "exento";
    }

    public PropietarioExento(Long id, Long dni, String nombre, String apellido){
        super(id, dni, nombre, apellido);
    }
}
