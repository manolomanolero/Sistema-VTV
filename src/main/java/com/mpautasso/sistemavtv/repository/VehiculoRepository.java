package com.mpautasso.sistemavtv.repository;

import com.mpautasso.sistemavtv.model.Propietario;
import com.mpautasso.sistemavtv.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    Optional<Vehiculo> findByDominio(String dominio);
    List<Vehiculo> findAllByPropietario(Propietario propietario);

    @Query(
            value = "select * from Vehiculos WHERE tipo = 'Automovil' AND propietario_id = ?1",
            nativeQuery = true
    )
    List<Vehiculo> findAllAutomovilesPorPropietarioId(Long propietarioId);
    @Query(
            value = "select * from Vehiculos WHERE tipo = 'Automovil'",
            nativeQuery = true
    )
    List<Vehiculo> findAllAutomoviles();
}
