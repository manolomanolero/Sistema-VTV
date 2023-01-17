package com.mpautasso.sistemavtv.repository;

import com.mpautasso.sistemavtv.model.Automovil;
import com.mpautasso.sistemavtv.model.EstadosInspeccion;
import com.mpautasso.sistemavtv.model.UltimaInspeccionAutomovil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UltimaInspeccionAutomovilRepository extends JpaRepository<UltimaInspeccionAutomovil, Long> {
    List<UltimaInspeccionAutomovil> findAllByEstadosInspeccion(EstadosInspeccion estadosInspeccion);
    Optional<UltimaInspeccionAutomovil> findByAutomovil(Automovil automovil);
}
