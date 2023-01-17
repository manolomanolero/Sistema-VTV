package com.mpautasso.sistemavtv.repository;

import com.mpautasso.sistemavtv.model.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PropietariosRepository extends JpaRepository<Propietario, Long> {
    Optional<Propietario> findByDni(Long dni);
}
