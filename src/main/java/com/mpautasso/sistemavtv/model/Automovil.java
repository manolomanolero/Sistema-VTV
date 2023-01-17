package com.mpautasso.sistemavtv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AUTOMOVILES")
public class Automovil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "marca", nullable = false, length = 25)
    private String marca;
    @Column(name = "modelo", nullable = false, length = 25)
    private String modelo;

    @Column(name = "dominio", nullable = false, unique = true, length = 25)
    private String dominio;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "propietario_id",
            referencedColumnName = "id"
    )
    private Propietario propietario;



}
