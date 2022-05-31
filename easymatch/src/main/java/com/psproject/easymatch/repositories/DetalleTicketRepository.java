package com.psproject.easymatch.repositories;

import com.psproject.easymatch.models.DetalleTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DetalleTicketRepository extends JpaRepository<DetalleTicket, Long> {
    @Query(value = "select * from detalle_tickets where id_ticket = :id_ticket", nativeQuery = true)
    List<DetalleTicket> findDetalleByTicketId(@Param("id_ticket") long id_ticket);
}
