package br.edu.ifms.ordem.resources;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifms.ordem.dto.OrdemDeServicoDTO;
import br.edu.ifms.ordem.services.OrdemDeServicoService;

@RestController
@RequestMapping(value = "/servicos")
public class OrdemDeServicoResource {
	
	@Autowired
	private OrdemDeServicoService service;

	/*@GetMapping
	public ResponseEntity<Page<OrdemDeServicoDTO>> findAllPaged(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesPerPage", defaultValue = "10") Integer linesPerPage,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction,
			@RequestParam(value = "orderBy", defaultValue = "dataCadastro") String orderBy
		){
		
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, 
				Direction.valueOf(direction), orderBy);
		
		Page<OrdemDeServicoDTO> list = service.findAllPaged(pageRequest);
		return ResponseEntity.ok().body(list);
	}*/
	
	@GetMapping
	public ResponseEntity<List<OrdemDeServicoDTO>> findAll(){
		List<OrdemDeServicoDTO> lista = service.findAll();
		return ResponseEntity.ok().body(lista);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<OrdemDeServicoDTO> findById(@PathVariable Long id){
		OrdemDeServicoDTO dto = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<OrdemDeServicoDTO> insert(@RequestBody OrdemDeServicoDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
											 .path("/{id}")
											 .buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<OrdemDeServicoDTO> update(@PathVariable Long id, @RequestBody OrdemDeServicoDTO dto){
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}

	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id){
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
}
