package br.com.mercadolivre.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SimianRepository extends JpaRepository<SimianEntity, UUID> {

}
