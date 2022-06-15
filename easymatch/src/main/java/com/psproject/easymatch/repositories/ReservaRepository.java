package com.psproject.easymatch.repositories;

import com.psproject.easymatch.dtos.CantReserXFechaGraficoDTO;
import com.psproject.easymatch.dtos.CantReservasXFechaDTO;
import com.psproject.easymatch.dtos.MontoXReservaConfirmadaDTO;
import com.psproject.easymatch.models.Estado;
import com.psproject.easymatch.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
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

    @Modifying
    @Query(value = "UPDATE reservas SET id_estado = 3 where id_reserva = :id_reserva", nativeQuery = true)
    void updateEstadoToCancelado(@Param("id_reserva") long id_reserva);

    @Modifying
    @Query(value = "UPDATE reservas SET id_estado = 1 where id_reserva = :id_reserva", nativeQuery = true)
    void updateEstadoToFacturado(@Param("id_reserva") long id_reserva);

    @Query(value = "select new com.psproject.easymatch.dtos.CantReservasXFechaDTO(count(r.idReserva)) from Reserva r join " +
            "DetalleReserva d on d.reserva.idReserva = r.idReserva join Cancha c on c.idCancha = d.cancha.idCancha where" +
            " r.fecha between ?1 and ?2 and r.estado.idEstado = ?3 and c.negocio.idNegocio = ?4")
    CantReservasXFechaDTO reporte2(LocalDate fecha1, LocalDate fecha2, long estado, long idNegocio);

    @Query(value = "select new com.psproject.easymatch.dtos.CantReserXFechaGraficoDTO(count(r.idReserva), r.fecha) from Reserva r " +
            "join DetalleReserva d on d.reserva.idReserva = r.idReserva join Cancha c on c.idCancha = d.cancha.idCancha " +
            "where r.fecha between ?1 and ?2 and c.negocio.idNegocio = ?3 group by r.fecha")
    List<CantReserXFechaGraficoDTO> reporte3(LocalDate fecha1, LocalDate fecha2, long idNegocio);
    @Query(value = "select new com.psproject.easymatch.dtos.MontoXReservaConfirmadaDTO(sum(c.precioCancha), r.fecha) from DetalleReserva d " +
            "join Cancha c on c.idCancha = d.cancha.idCancha join Reserva r on r.idReserva = d.reserva.idReserva " +
            "where r.estado.idEstado = 1 and c.negocio.idNegocio = ?1 group by r.fecha" )
    List<MontoXReservaConfirmadaDTO> reporte4(long idNegocio);

}
