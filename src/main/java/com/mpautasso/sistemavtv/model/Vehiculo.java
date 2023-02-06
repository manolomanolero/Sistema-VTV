package com.mpautasso.sistemavtv.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "VEHICULOS")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo",
        discriminatorType = DiscriminatorType.STRING)
public abstract class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dominio", nullable = false, unique = true, length = 25)
    private String dominio;

    @Column(name = "nro_motor", nullable = false, length = 20)
    private String numeroMotor;

    @Column(name = "nro_chasis", nullable = false, unique = true, length = 20)
    private String numeroChasis;

    @ManyToOne
    @JoinColumn(
            name = "version_id",
            referencedColumnName = "id"
    )
    private Version version;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "propietario_id",
            referencedColumnName = "id"
    )
    private Propietario propietario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(
            name = "chofer_id",
            referencedColumnName = "id"
    )
    private Chofer chofer;

    public Vehiculo(String dominio, String numeroMotor, String numeroChasis, Version version, Propietario propietario, Chofer chofer) {
        this.dominio = dominio;
        this.numeroMotor = numeroMotor;
        this.numeroChasis = numeroChasis;
        this.version = version;
        this.propietario = propietario;
        this.chofer = chofer;
    }

    public Vehiculo(Long id, String dominio, String numeroMotor, String numeroChasis, Version version, Propietario propietario) {
        this.id = id;
        this.dominio = dominio;
        this.numeroMotor = numeroMotor;
        this.numeroChasis = numeroChasis;
        this.version = version;
        this.propietario = propietario;
    }

    public Vehiculo(String dominio, String numeroMotor, String numeroChasis, Version version, Propietario propietario) {
        this.dominio = dominio;
        this.numeroMotor = numeroMotor;
        this.numeroChasis = numeroChasis;
        this.version = version;
        this.propietario = propietario;
    }
}
