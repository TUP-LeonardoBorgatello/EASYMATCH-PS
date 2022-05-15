package com.psproject.easymatch.repositories;

import com.psproject.easymatch.models.LoginNegocio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginNegociosRepository extends JpaRepository<LoginNegocio, Long> {

    @Query(value = "SELECT max(id_login_negocio) from login_negocios", nativeQuery = true)
    Long lastIdNegocioLogued();
}
