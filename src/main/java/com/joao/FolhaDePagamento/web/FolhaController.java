package com.joao.FolhaDePagamento.web;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.joao.FolhaDePagamento.domain.FolhaRepository;
import com.joao.FolhaDePagamento.domain.Folha_Pagamento;

@CrossOrigin
@RestController
public class FolhaController {
    @Autowired
    private FolhaRepository repository;
    
    @GetMapping(value = "/folhas")
	ResponseEntity<?> getAll() {
		try {
			List<Folha_Pagamento> folhas = (List<Folha_Pagamento>) repository.findAll();
			
			if (folhas.isEmpty()) {
				FolhasResponse response = new FolhasResponse(true, "Não há folhas cadastradas!", null);
				return ResponseEntity.status(HttpStatus.OK).body(response);
	        }
			
			FolhasResponse response = new FolhasResponse(true, "Consulta realizada com sucesso!", null);
			response.setFolhas(folhas);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		catch (Exception error) {
			List<String> errors = new ArrayList<String>();
	    	errors.add(error.toString());
			FolhaResponse response = new FolhaResponse(false, "Não foi possível realizar a consulta", errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	@GetMapping(value = "/folhas/{id}")
	ResponseEntity<FolhaResponse> getById(@PathVariable("id") Long id) {
		try {
			Optional<Folha_Pagamento> folha = repository.findById(id);
			System.out.println(folha);
			if(folha.isPresent()) {
				FolhaResponse response = new FolhaResponse(true, "Consulta realizada com sucesso!", null);
				response.setFolha(folha);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} 
			else {
				FolhaResponse response = new FolhaResponse(false, "Nenhuma folha foi encontrada com o id " + id, null);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			
		}
		catch (Exception error) {
			List<String> errors = new ArrayList<String>();
	    	errors.add(error.toString());
			FolhaResponse response = new FolhaResponse(false, "Não foi possível realizar a consulta", errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	@PostMapping(value = "/folhas")
	ResponseEntity<FolhaResponse> post(@RequestBody Map<String, Object> folhaBody) {
		try {
			Integer mes = (Integer)folhaBody.get("mes");
			Integer ano = (Integer)folhaBody.get("ano");
			List<String> errors = new ArrayList<String>();
			
			if(mes <= 0 || mes == null || mes > 12) {
				errors.add("Informe um mês válido");
			}
			if(ano <= 2000 || ano == null) {
				errors.add("Informe um ano válido");
			}
				
			if(errors.isEmpty()) {
				Folha_Pagamento folha = new Folha_Pagamento(mes, ano);
				repository.save(folha);
				
				FolhaResponse response = new FolhaResponse(true, "Folha de pagamentos criada com sucesso!", null);
				response.setFolha(repository.findById(folha.getid()));
				return ResponseEntity.status(HttpStatus.CREATED).body(response);
			}
			else {
				FolhaResponse response = new FolhaResponse(false, "Não foi possível criar a folha de pagamentos", errors);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			
		} 
		catch (Exception error) {
			List<String> errors = new ArrayList<String>();
	    	errors.add(error.toString());
			FolhaResponse response = new FolhaResponse(false, "Não foi possível criar a folha de pagamentos", errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	@PutMapping(value = "/folhas/{id}")
	ResponseEntity<FolhaResponse> put(@PathVariable("id") Long id, @RequestBody Map<String, Object> putFolha) {
		try {
			Optional<Folha_Pagamento> getFolha = repository.findById(id);
			Folha_Pagamento folha = getFolha.get();
			Integer mes = (Integer)putFolha.get("mes");
			Integer ano = (Integer)putFolha.get("ano");
			List<String> errors = new ArrayList<String>();
			
			if(mes <= 0 || mes == null || mes > 12) {
				errors.add("Informe um mês válido");
			}
			if(ano <= 2000 || ano == null) {
				errors.add("Informe um ano válido");
			}
				
			if(errors.isEmpty()) {
				folha.setMes(mes);
				folha.setAno(ano);

				repository.save(folha);
				
				FolhaResponse response = new FolhaResponse(true, "Folha de pagamentos atualizada com sucesso!", null);
				response.setFolha(repository.findById(folha.getid()));
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
			else {
				FolhaResponse response = new FolhaResponse(false, "Erro ao atualizar a folha de pagamentos!", errors);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			
		}
		catch(Exception error) {
			List<String> errors = new ArrayList<String>();
	    	errors.add(error.toString());
			FolhaResponse response = new FolhaResponse(false, "Não foi possível atualizar a folha de pagamentos!", errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	@DeleteMapping("/folhas/{id}")
	ResponseEntity<FolhaResponse> delete(@PathVariable Long id) {
		try {
			repository.deleteById(id);
			FolhaResponse response = new FolhaResponse(true, "Folha de pagamentos excluída com sucesso", null);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		catch(Exception error) {
			List<String> errors = new ArrayList<String>();
	    	errors.add(error.toString());
			FolhaResponse response = new FolhaResponse(false, "Erro ao excluir a folha de pagamentos", errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	    
	}
}