package com.mpautasso.sistemavtv.repository;

import com.mpautasso.sistemavtv.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarcaRepository extends JpaRepository<Marca, Long> {
}
