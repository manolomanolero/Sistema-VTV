package com.mpautasso.sistemavtv.repository;

import com.mpautasso.sistemavtv.model.Automovil;
import com.mpautasso.sistemavtv.model.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutomovilRepository extends JpaRepository<Automovil, Long> {
    Optional<Automovil> findByDominio(String dominio);
    List<Automovil> findAllByPropietario(Propietario propietario);
}
