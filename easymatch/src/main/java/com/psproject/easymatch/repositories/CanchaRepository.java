package com.psproject.easymatch.repositories;

import com.psproject.easymatch.models.Cancha;
import com.psproject.easymatch.models.Jugador;
import com.psproject.easymatch.models.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CanchaRepository extends JpaRepository<Cancha, Long> {
    @Modifying
    @Query(value = "UPDATE canchas SET estado = false where id_cancha = :id_cancha", nativeQuery = true)
    void updateEstadoToFalse(@Param("id_cancha") long id_cancha);

    @Modifying
    @Query(value = "UPDATE canchas SET estado = true where id_cancha = :id_cancha", nativeQuery = true)
    void updateEstadoToTrue(@Param("id_cancha") long id_cancha);

    @Query(value = "select * from canchas where id_cancha = :id_cancha LIMIT 1", nativeQuery = true)
    Cancha searchById(@Param("id_cancha") long id_cancha);

    @Query(value = "select * from canchas where id_negocio = :id_negocio", nativeQuery = true)
    List<Cancha> findCanchasByNegocioId(@Param("id_negocio") long id_negocio);

}
