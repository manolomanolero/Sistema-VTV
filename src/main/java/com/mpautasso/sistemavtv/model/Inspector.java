package com.mpautasso.sistemavtv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@DiscriminatorValue("Inspector")
public class Inspector extends Empleado {

    @Override
    public String cargoDelEmpleado() {
        return "Inspector";
    }

    public Inspector(Long dni, Long numeroLegajo, String nombre, String apellido) {
        super(dni, numeroLegajo, nombre, apellido);
    }
}
