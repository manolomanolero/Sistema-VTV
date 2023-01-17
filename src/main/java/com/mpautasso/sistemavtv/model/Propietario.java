package com.mpautasso.sistemavtv.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "PROPIETARIOS")
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo",
        discriminatorType = DiscriminatorType.STRING)
public abstract class Propietario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long dni;
    @Column(nullable = false, length = 25)
    private String nombre;
    @Column(nullable = false, length = 25)
    private String apellido;

    public abstract String tipoPropietario();
}
