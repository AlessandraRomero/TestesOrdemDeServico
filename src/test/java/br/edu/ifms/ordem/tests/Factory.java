package br.edu.ifms.ordem.tests;

import br.edu.ifms.ordem.dto.TecnicoDTO;
import br.edu.ifms.ordem.entities.Tecnico;

public class Factory {
	public static Tecnico novoTecnico() {
		Tecnico tecnico = new Tecnico(4L, "Carlos Eduardo", "(67) 98888-4545", 
										  "carlos@gmail.com", "senha");		
		return tecnico;
	}
	
	public static TecnicoDTO novoTecnicoDTO() {
		Tecnico tecnico = novoTecnico();
		return new TecnicoDTO(tecnico);
	}
}
