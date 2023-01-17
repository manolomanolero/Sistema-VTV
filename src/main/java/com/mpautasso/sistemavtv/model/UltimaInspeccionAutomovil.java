package com.mpautasso.sistemavtv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UltimaInspeccionAutomovil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "automovil_id", referencedColumnName = "id")
    private Automovil automovil;

    private EstadosInspeccion estadosInspeccion;
    private Date fecha;
}
