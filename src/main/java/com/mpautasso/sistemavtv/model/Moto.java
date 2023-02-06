package com.mpautasso.sistemavtv.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@DiscriminatorValue("Moto")
public class Moto extends Vehiculo{
    public Moto(String dominio, String numeroMotor, String numeroChasis, Version version, Propietario propietario, Chofer chofer) {
        super(dominio, numeroMotor, numeroChasis, version, propietario, chofer);
    }

    public Moto(Long id, String dominio, String numeroMotor, String numeroChasis, Version version, Propietario propietario, Chofer chofer) {
        super(id, dominio, numeroMotor, numeroChasis, version, propietario, chofer);
    }

    public Moto(Long id, String dominio, String numeroMotor, String numeroChasis, Version version, Propietario propietario) {
        super(id, dominio, numeroMotor, numeroChasis, version, propietario);
    }

    public Moto(String dominio, String numeroMotor, String numeroChasis, Version version, Propietario propietario) {
        super(dominio, numeroMotor, numeroChasis, version, propietario);
    }
}
