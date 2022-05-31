package com.psproject.easymatch.repositories;

import com.psproject.easymatch.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Long> {

    @Query(value = "SELECT max(id_reserva) from reservas", nativeQuery = true)
    Long lastReservaId();

    @Modifying
    @Query(value = "UPDATE reservas SET id_estado = 2 where id_reserva = :id_reserva", nativeQuery = true)
    void updateEstadoToFinalizado(@Param("id_reserva") long id_reserva);

    @Query(value = "select * from reservas where id_jugador = :id_jugador", nativeQuery = true)
    List<Reserva> findReservaByJugador(@Param("id_jugador") long id_jugador);

}
