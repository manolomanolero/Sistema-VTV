package com.mpautasso.sistemavtv.repository;

import com.mpautasso.sistemavtv.model.Chofer;
import com.mpautasso.sistemavtv.model.Cliente;
import com.mpautasso.sistemavtv.model.Propietario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientesRepository extends JpaRepository<Cliente, Long> {
    List<Cliente> findAllByDni(Long dni);


    @Query(
            value = "select * from Clientes WHERE tipo != 'Chofer'",
            nativeQuery = true
    )
    List<Propietario> findAllPropietarios();

    @Query(
            value = "select * from Clientes WHERE tipo = 'Chofer'",
            nativeQuery = true
    )
    List<Chofer> findAllChoferes();


    @Query(
            value = "select * from Clientes WHERE dni = ?1 AND tipo != 'Chofer'",
            nativeQuery = true
    )
    Optional<Propietario> findPropietarioByDni(Long dni);

    @Query(
            value = "select * from Clientes WHERE dni = ?1 AND tipo = ?2",
            nativeQuery = true
    )
    Optional<Cliente> findClienteByDniAndTipo(Long dni, String tipo);

    @Query(
            value = "select * from Clientes WHERE dni = ?1 AND tipo = 'Chofer'",
            nativeQuery = true
    )
    Optional<Chofer> findChoferByDni(Long dni);

}
