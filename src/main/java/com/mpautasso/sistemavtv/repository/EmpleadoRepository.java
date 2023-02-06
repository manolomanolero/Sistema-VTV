package com.mpautasso.sistemavtv.repository;

import com.mpautasso.sistemavtv.model.Empleado;
import com.mpautasso.sistemavtv.model.Inspector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {
    Optional<Empleado> findByDni(Long dni);

    @Query(
            value = "select * from Empleados WHERE dni = ?1 AND cargo = 'Inspector'",
            nativeQuery = true
    )
    Optional<Inspector> findInspectorByDni(Long dni);

    boolean existsByDni(Long dni);
}
