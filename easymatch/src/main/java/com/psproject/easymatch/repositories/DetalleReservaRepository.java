package com.psproject.easymatch.repositories;

import com.psproject.easymatch.models.DetalleReserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DetalleReservaRepository extends JpaRepository<DetalleReserva, Long> {

    @Query(value = "select * from detalle_reserva where " +
            "fecha_reserva = :fecha_reserva and " +
            "id_cancha = :id_cancha and " +
            "horario_inicial = :horario_inicial", nativeQuery = true)
    List<DetalleReserva> searchDetReservaByCanchaFechaHora(@Param("fecha_reserva") String fecha_reserva,
                                                           @Param("id_cancha") long id_cancha,
                                                           @Param("horario_inicial") int horario_inicial);

    @Query(value = "select id_cancha from detalle_reserva where " +
            "fecha_reserva > :fecha_reserva", nativeQuery = true)
    List<Long> searchIdCanchasReservadas(@Param("fecha_reserva") String fecha_reserva);

    @Query(value = "select * from detalle_reserva where id_reserva = :id_reserva", nativeQuery = true)
    List<DetalleReserva> findDetalleByReservaId(@Param("id_reserva") long id_reserva);

    @Query(value = "select * from detalle_reserva where id_cancha = :id_cancha", nativeQuery = true)
    List<DetalleReserva> findDetalleReservaByCancha(@Param("id_cancha") long id_cancha);


}
