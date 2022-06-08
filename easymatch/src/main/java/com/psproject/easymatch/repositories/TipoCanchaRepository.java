package com.psproject.easymatch.repositories;

import com.psproject.easymatch.models.TipoCancha;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TipoCanchaRepository extends JpaRepository<TipoCancha, Long> {

}
