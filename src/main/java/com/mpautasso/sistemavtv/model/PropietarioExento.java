package com.mpautasso.sistemavtv.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("Exento")
public class PropietarioExento extends Propietario {
    @Column(unique = true)
    private Long cuit;

    @Override
    public String tipoDeCliente() {
        return "Propietario exento";
    }

    public PropietarioExento(Long dni, String nombre, String apellido, Long cuit){
        super(dni, nombre, apellido);
        this.cuit = cuit;
    }

    public PropietarioExento(Long id, Long dni, String nombre, String apellido, Long cuit) {
        super(id, dni, nombre, apellido);
        this.cuit = cuit;
    }
}
