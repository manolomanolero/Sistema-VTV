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

    public Inspector(Long numeroLegajo, Long dni, String nombre, String apellido) {
        super(numeroLegajo, dni, nombre, apellido);
    }

    public Inspector(Long dni, String nombre, String apellido) {
        super(dni, nombre, apellido);
    }
}
