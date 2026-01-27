package com.peepl.peepl_codetest.repository;

import com.peepl.peepl_codetest.model.Client;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


@Repository
public interface ClientRepository extends JpaRepository<Client, Integer>{

    Optional<Client> findBySlug(String slug);

    boolean existsBySlug(String slug);

    @Query("SELECT c FROM Client c WHERE c.deletedAt IS NULL")
    List<Client> findAllActive();

    @Query("SELECT c FROM Client c WHERE c.id = :id AND c.deletedAt IS NULL")
    Optional<Client> findByIdActive(Integer id);

    @Query("SELECT c FROM Client c WHERE c.slug = :slug AND c.deletedAt IS NULL")
    Optional<Client> findBySlugActive(String slug);
    
}
