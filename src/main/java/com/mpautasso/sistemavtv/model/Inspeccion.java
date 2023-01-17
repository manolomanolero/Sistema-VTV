package com.mpautasso.sistemavtv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "INSPECCIONES")
public class Inspeccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long numeroInspeccion;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 15)
    private EstadosInspeccion estado;

    private Boolean estaExento;

    private Date fecha;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            columnDefinition = "inspector_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Inspector inspector;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            columnDefinition = "automovil_id",
            referencedColumnName = "id",
            nullable = false
    )
    private Automovil automovil;
}
