package com.mpautasso.sistemavtv.repository;

import com.mpautasso.sistemavtv.model.Inspector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InspectorRepository extends JpaRepository<Inspector, Long> {
    Optional<Inspector> findByDni(Long dni);
    boolean existsByDni(Long dni);
}
