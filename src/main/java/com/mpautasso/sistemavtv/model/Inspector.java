package com.mpautasso.sistemavtv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "INSPECTORES")
public class Inspector {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Long dni;

    @Column(nullable = false, length = 25)
    private String nombre;

    @Column(nullable = false, length = 25)
    private String apellido;

}
