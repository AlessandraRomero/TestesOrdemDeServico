package br.edu.ifms.ordem.services;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.edu.ifms.ordem.dto.TecnicoDTO;
import br.edu.ifms.ordem.entities.Tecnico;
import br.edu.ifms.ordem.repositories.TecnicoRepository;
import br.edu.ifms.ordem.services.exceptions.DataBaseException;
import br.edu.ifms.ordem.services.exceptions.ResourceNotFoundException;
import br.edu.ifms.ordem.tests.Factory;

@ExtendWith(SpringExtension.class)
public class TecnicoServiceTests {
	
	@InjectMocks
	private TecnicoService service;
	
	@Mock
	private TecnicoRepository repository;
	
	private Long idExistente;
	private Long idInexistente;
	private Long idDependente;
	
	private PageImpl<Tecnico> page;
	private Tecnico tecnico;
	
	@BeforeEach
	void setUp() throws Exception {
		idExistente = 1L;
		idInexistente = 100L;
		idDependente = 10L;
		
		tecnico = Factory.novoTecnico();
		page = new PageImpl<>(List.of(tecnico));
		
		//Configurar comportamento
		// Metodos com Retorno
		Mockito.when(repository.findAll((Pageable)ArgumentMatchers.any())).thenReturn(page);
		
		Mockito.when(repository.findById(idExistente))
		                       .thenReturn(Optional.of(tecnico));
		Mockito.when(repository.findById(idInexistente))
        					   .thenReturn(Optional.empty());
		
		
		// Metodos VOID
		Mockito.doNothing().when(repository).deleteById(idExistente);
		Mockito.doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(idInexistente);
		Mockito.doThrow(DataIntegrityViolationException.class).when(repository).deleteById(idDependente);
		
	}
	
	@Test
	public void findAllPagedDeveriaRetornarUmaPagina() {
		Pageable pageable = PageRequest.of(0, 10);
		Page<TecnicoDTO> resultado = service.findAllPaged(pageable);
		Assertions.assertNotNull(resultado);
		Mockito.verify(repository, Mockito.times(1)).findAll(pageable);
	}
	
	@Test
	public void findByIdDeveriaRetornarUmTecnicoQuandoIdExistir() {
		TecnicoDTO tecnico = service.findById(idExistente);
		Assertions.assertNotNull(tecnico);
		Mockito.verify(repository, Mockito.times(1)).findById(idExistente);
	}
	
	@Test
	public void findByIdDeveriaRetornarUmaExcecaoQuandoIdExistir() {
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.findById(idInexistente);
		});
		
		Mockito.verify(repository, Mockito.times(1)).findById(idInexistente);
	}
	
	@Test
	public void deleteDeveriaExcluirRegistroQuandoIdExistir() {
		
		Assertions.assertDoesNotThrow(() -> {
			service.delete(idExistente);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(idExistente);
		
	}
	
	@Test
	public void deleteShouldThrowExceptionWhenIdInexistente() {
		
		Assertions.assertThrows(ResourceNotFoundException.class, () -> {
			service.delete(idInexistente);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(idInexistente);
		
	}
	
	@Test
	public void deleteShouldThrowDataBaseExceptionWhenIdDependente() {
		
		Assertions.assertThrows(DataBaseException.class, () -> {
			service.delete(idDependente);
		});
		
		Mockito.verify(repository, Mockito.times(1)).deleteById(idDependente);
		
	}

	
}





