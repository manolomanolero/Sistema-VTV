package com.mpautasso.sistemavtv.model;


import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("Chofer")
public class Chofer extends Cliente{
    @Column(length = 20)
    private String cedulaAzul;

    @Override
    public String tipoDeCliente() {
        return "Chofer";
    }

    public Chofer(Long dni, String nombre, String apellido, String cedulaAzul) {
        super(dni, nombre, apellido);
        this.cedulaAzul = cedulaAzul;
    }

    public Chofer(Long id, Long dni, String nombre, String apellido, String cedulaAzul) {
        super(id, dni, nombre, apellido);
        this.cedulaAzul = cedulaAzul;
    }
}
