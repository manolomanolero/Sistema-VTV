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

    public Gerente(Long dni, Long numeroLegajo, String nombre, String apellido) {
        super(dni, numeroLegajo, nombre, apellido);
    }
}
