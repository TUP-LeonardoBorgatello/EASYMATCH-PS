package com.psproject.easymatch.repositories;

import com.psproject.easymatch.models.LoginJugadores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginJugadoresRepository extends JpaRepository<LoginJugadores, Long> {

    @Query(value = "SELECT max(id_login_jugador) from login_jugadores", nativeQuery = true)
    Long lastIdJugadorLogued();
}
