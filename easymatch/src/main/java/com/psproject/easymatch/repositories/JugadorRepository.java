package com.psproject.easymatch.repositories;

import com.psproject.easymatch.models.Jugador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface JugadorRepository extends JpaRepository<Jugador, Long> {
    boolean existsByDocumento(int documento);

    @Query(value = "select * from jugadores where documento = :documento", nativeQuery = true)
    List<Jugador> searchByDocumento(@Param("documento") int documento);

    void deleteByDocumento(int documento);

    @Query(value = "select * from jugadores where documento = :documento LIMIT 1", nativeQuery = true)
    Jugador searchByDocumento1(@Param("documento") int documento);

    @Modifying
    @Transactional
    @Query(value = "DELETE from jugadores WHERE documento = :documento", nativeQuery = true)
    void deleteJugador(@Param("documento") int documento);

    @Modifying
    @Transactional
    @Query(value = "DELETE from login_jugadores WHERE id_jugador = :id_jugador", nativeQuery = true)
    void deleteJugadorLogin(@Param("id_jugador") long id_jugador);

    @Modifying
    @Query(value = "UPDATE jugadores SET estado = false where id_jugador = :id_jugador", nativeQuery = true)
    void updateEstadoToFalse(@Param("id_jugador") long id_jugador);

    @Modifying
    @Query(value = "UPDATE jugadores SET estado = true where id_jugador = :id_jugador", nativeQuery = true)
    void updateEstadoToTrue(@Param("id_jugador") long id_jugador);
}
