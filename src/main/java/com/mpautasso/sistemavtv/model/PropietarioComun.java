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
    public String tipoDeCliente() {
        return "Propietario comun";
    }

    public PropietarioComun(Long dni, String nombre, String apellido){
        super(dni, nombre, apellido);
    }

    public PropietarioComun(Long id, Long dni, String nombre, String apellido) {
        super(id, dni, nombre, apellido);
    }
}
