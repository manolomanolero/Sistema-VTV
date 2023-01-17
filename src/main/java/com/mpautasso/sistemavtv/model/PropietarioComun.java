package com.mpautasso.sistemavtv.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Entity
@AllArgsConstructor
@DiscriminatorValue("Comun")
public class PropietarioComun extends Propietario {
    @Override
    public String tipoPropietario() {
        return "comun";
    }

    public PropietarioComun(Long id, Long dni, String nombre, String apellido){
        super(id, dni, nombre, apellido);
    }
}
