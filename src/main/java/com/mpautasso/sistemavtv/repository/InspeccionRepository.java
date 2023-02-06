package com.mpautasso.sistemavtv.repository;

import com.mpautasso.sistemavtv.model.Inspeccion;
import com.mpautasso.sistemavtv.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InspeccionRepository extends JpaRepository<Inspeccion, Long> {
    List<Inspeccion> findAllByVehiculo(Vehiculo vehiculo);
}
