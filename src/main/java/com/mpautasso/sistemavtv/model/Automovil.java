package com.mpautasso.sistemavtv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Entity
@DiscriminatorValue("Automovil")
public class Automovil extends Vehiculo{
    public Automovil(String dominio, String numeroMotor, String numeroChasis, Version version, Propietario propietario, Chofer chofer) {
        super(dominio, numeroMotor, numeroChasis, version, propietario, chofer);
    }

    public Automovil(Long id, String dominio, String numeroMotor, String numeroChasis, Version version, Propietario propietario, Chofer chofer) {
        super(id, dominio, numeroMotor, numeroChasis, version, propietario, chofer);
    }

    public Automovil(Long id, String dominio, String numeroMotor, String numeroChasis, Version version, Propietario propietario) {
        super(id, dominio, numeroMotor, numeroChasis, version, propietario);
    }

    public Automovil(String dominio, String numeroMotor, String numeroChasis, Version version, Propietario propietario) {
        super(dominio, numeroMotor, numeroChasis, version, propietario);
    }
}

