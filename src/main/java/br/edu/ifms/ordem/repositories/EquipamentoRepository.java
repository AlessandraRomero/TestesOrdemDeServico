package br.edu.ifms.ordem.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.ifms.ordem.entities.Equipamento;

@Repository
public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {
	
}