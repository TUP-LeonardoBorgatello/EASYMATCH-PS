package com.psproject.easymatch.repositories;

import com.psproject.easymatch.models.TipoDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoDocRepository extends JpaRepository<TipoDoc, Long> {

}
