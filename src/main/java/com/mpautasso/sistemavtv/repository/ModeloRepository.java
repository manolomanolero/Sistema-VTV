package com.mpautasso.sistemavtv.repository;

import com.mpautasso.sistemavtv.model.Marca;
import com.mpautasso.sistemavtv.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo, Long> {
    List<Modelo> findAllByMarca(Marca marca);
}
