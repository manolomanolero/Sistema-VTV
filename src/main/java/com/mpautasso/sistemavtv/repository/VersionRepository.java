package com.mpautasso.sistemavtv.repository;

import com.mpautasso.sistemavtv.model.Modelo;
import com.mpautasso.sistemavtv.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VersionRepository extends JpaRepository<Version, Long> {
    List<Version> findAllByModelo(Modelo modelo);
}
