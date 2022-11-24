package br.edu.ifms.ordem.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.edu.ifms.ordem.dto.TecnicoDTO;
import br.edu.ifms.ordem.services.TecnicoService;
import br.edu.ifms.ordem.services.exceptions.ResourceNotFoundException;
import br.edu.ifms.ordem.tests.Factory;

@WebMvcTest(TecnicoResource.class)
public class TecnicoResourceTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TecnicoService service;

	private PageImpl<TecnicoDTO> page;
	private TecnicoDTO tecnicoDTO;
	private Long idExistente;
	private Long idInexistente;

	@BeforeEach
	void setUp() throws Exception {
		idExistente = 1L;
		idInexistente = 100L;

		tecnicoDTO = Factory.novoTecnicoDTO();
		page = new PageImpl<>(List.of(tecnicoDTO));

		when(service.findAllPaged(any())).thenReturn(page);

		// findById(id)
		when(service.findById(idExistente)).thenReturn(tecnicoDTO);
		when(service.findById(idExistente)).thenThrow(ResourceNotFoundException.class);

		// Update
		when(service.update(eq(idExistente), any())).thenReturn(tecnicoDTO);
		when(service.update(eq(idInexistente), any())).thenThrow(ResourceNotFoundException.class);
		
		//insert 
		when(service.insert(tecnicoDTO)).thenReturn(tecnicoDTO);
		
		//delete 
		doNothing().when(service).delete(idInexistente);
		doThrow(ResourceNotFoundException.class).when(service).delete(idInexistente);
	}
	public void insertDeveriaRetornarCreatedQuandoInserirUmNovoTecnico() throws Exception{
		String jsonBody = objectMapper.writeValueAsString(tecnicoDTO);
	}

	public void deleteDeveriaRetornarNoContentQuandoIdExistir() {

	}

	public void deleteDeveriaRetornarNotFoundQuandoIdExistir() {

	}

	@Test
	public void updateDeveriaRetornarUmTecnicoQuandoIdExistir() throws Exception {

		String jsonBody = objectMapper.writeValueAsString(tecnicoDTO);

		ResultActions resultado = mockMvc.perform(put("/tecnicos/{id}", idExistente).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		resultado.andExpect(status().isOk());
		resultado.andExpect(jsonPath("$.id").exists());
	}

	@Test
	public void updateDeveriaRetornarExceptionNotFoundQuandoIdInexistente() throws Exception {
		String jsonBody = objectMapper.writeValueAsString(tecnicoDTO);

		ResultActions resultado = mockMvc.perform(put("/tecnicos/{id}", idInexistente).content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

		resultado.andExpect(status().isNotFound());
	}

	@Test
	public void findAllDeveriaRetornarPaginaTecnicoDTO() throws Exception {

		// mockMvc.perform(get("/tecnicos")).andExpect(status().isOk());

		ResultActions resultado = mockMvc.perform(get("/tecnicos").accept(MediaType.APPLICATION_JSON));

		resultado.andExpect(status().isOk());
	}

	@Test
	public void findByIdDeveriaRetornarUmTecnicoQuandoIdExistente() throws Exception {
		/// tecnicos/2
		ResultActions resultado = mockMvc
				.perform(get("/tecnicos/{id}", idExistente).accept(MediaType.APPLICATION_JSON));

		resultado.andExpect(status().isOk());
		resultado.andExpect(jsonPath("$.id").exists());
		resultado.andExpect(jsonPath("$.nome").exists());
	}

	@Test
	public void findByIdDeveriaRetornarExceptionNotFoundQuandoIdInexistente() throws Exception {
		/// tecnicos/100
		ResultActions resultado = mockMvc
				.perform(get("/tecnicos/{id}", idInexistente).accept(MediaType.APPLICATION_JSON));

		resultado.andExpect(status().isNotFound());
	}

}
