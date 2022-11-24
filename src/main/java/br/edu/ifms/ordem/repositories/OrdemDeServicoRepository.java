package br.edu.ifms.ordem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifms.ordem.entities.OrdemDeServico;

@Repository
public interface OrdemDeServicoRepository extends JpaRepository<OrdemDeServico, Long> {
	
}