package com.mpautasso.sistemavtv.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@DiscriminatorValue("Gerente")
public class Gerente extends Empleado{
    @Override
    public String cargoDelEmpleado() {
        return "Gerente";
    }

    public Gerente(Long numeroLegajo, Long dni, String nombre, String apellido) {
        super(numeroLegajo, dni, nombre, apellido);
    }

    public Gerente(Long dni, String nombre, String apellido) {
        super(dni, nombre, apellido);
    }
}
