package com.psproject.easymatch.repositories;

import com.psproject.easymatch.models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query(value = "SELECT max(id_ticket) from tickets", nativeQuery = true)
    Long lastTicketId();
}
