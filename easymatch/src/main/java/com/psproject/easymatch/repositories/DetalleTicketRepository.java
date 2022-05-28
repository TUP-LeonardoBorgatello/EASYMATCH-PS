package com.psproject.easymatch.repositories;

import com.psproject.easymatch.models.DetalleTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DetalleTicketRepository extends JpaRepository<DetalleTicket, Long> {

}
