package com.autobots.automanager.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.autobots.automanager.entitades.Credencial;

public interface RepositorioCredencial extends JpaRepository<Credencial, Long> {
    
    @Query("SELECT c FROM Credencial c WHERE c.inativo = false")
    List<Credencial> findActiveCredencials();

    @Query("SELECT c FROM Credencial c WHERE c.id = :id AND c.inativo = false") 
    Optional<Credencial> findActiveCredencialById(@Param("id") Long id);
}
