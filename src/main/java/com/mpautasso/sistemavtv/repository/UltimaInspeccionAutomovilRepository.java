package com.mpautasso.sistemavtv.repository;

import com.mpautasso.sistemavtv.model.Vehiculo;
import com.mpautasso.sistemavtv.model.enums.EstadosInspeccion;
import com.mpautasso.sistemavtv.model.UltimaInspeccionVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UltimaInspeccionAutomovilRepository extends JpaRepository<UltimaInspeccionVehiculo, Long> {
    Optional<UltimaInspeccionVehiculo> findByVehiculo(Vehiculo vehiculo);
    List<UltimaInspeccionVehiculo> findAllByEstadosInspeccion(EstadosInspeccion estadosInspeccion);
}
