package com.mpautasso.sistemavtv.repository;

import com.mpautasso.sistemavtv.model.Version;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VersionRepository extends JpaRepository<Version, Long> {
}
