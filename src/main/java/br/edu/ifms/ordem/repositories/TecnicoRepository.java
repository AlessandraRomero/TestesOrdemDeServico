package br.edu.ifms.ordem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifms.ordem.entities.Tecnico;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Long> {
	
}