package com.psproject.easymatch.repositories;

import com.psproject.easymatch.models.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NegocioRepository extends JpaRepository<Negocio, Long> {

    boolean existsByCuil(int cuil);

    @Query(value = "select * from negocios where cuil = :cuil", nativeQuery = true)
    List<Negocio> searchByCuil(@Param("cuil") int cuil);

    void deleteByCuil(int cuil);

    @Query(value = "select * from negocios where cuil = :cuil LIMIT 1", nativeQuery = true)
    Negocio searchByCuil1(@Param("cuil") int cuil);

    @Modifying
    @Query(value = "UPDATE negocios SET estado = FALSE WHERE id_negocio = :id_negocio", nativeQuery = true)
    void updateNegocioStatus(@Param("id_negocio") long id_negocio);
}
