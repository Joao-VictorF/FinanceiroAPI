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

import com.joao.FolhaDePagamento.domain.Colaborador;
import com.joao.FolhaDePagamento.domain.ColaboradorRepository;
import com.joao.FolhaDePagamento.domain.Folha_Pagamento;
import com.joao.FolhaDePagamento.domain.Movimento;
import com.joao.FolhaDePagamento.domain.MovimentoRepository;
import com.joao.FolhaDePagamento.domain.MovimentoType;

@CrossOrigin
@RestController
public class MovimentoController {
	@Autowired
	private MovimentoRepository repository;
	@Autowired
	private ColaboradorRepository colaboradorRepository;
	
	@GetMapping(value = "/movimentos")
	ResponseEntity<?> getAll() {
		try {
			List<Movimento> movimentos = (List<Movimento>) repository.findAll();
						
			MovimentosResponse response = new MovimentosResponse(true, "Consulta realizada com sucesso!", null);
			response.setMovimentos(movimentos);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		catch (Exception error) {
			List<String> errors = new ArrayList<String>();
	    	errors.add(error.toString());
			MovimentoResponse response = new MovimentoResponse(false, "Nenhum movimento encontrado", errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	@GetMapping(value = "/movimentos/{idColaborador}")
	ResponseEntity<MovimentosResponse> findByFuncionario(@PathVariable("idColaborador") Long id) {
		try {
			List<Movimento> movimentos = repository.findByIdColaborador(id);
			if(movimentos.isEmpty() == false) {
				MovimentosResponse response = new MovimentosResponse(true, "Consulta realizada com sucesso!", null);
				response.setMovimentos(movimentos);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
			else {
				MovimentosResponse response = new MovimentosResponse(true, "Nenhum movimento cadastrado para esse colaborador!", null);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
			
		}
		catch (Exception error) {
			List<String> errors = new ArrayList<String>();
	    	errors.add(error.toString());
			MovimentosResponse response = new MovimentosResponse(false, "Erro ao consultar os movimentos", errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	@PostMapping(value = "/movimentos/{colabId}")
	ResponseEntity<MovimentoResponse> post(@RequestBody Map<String, Object> movBody, @PathVariable("colabId") Long colabId) {
		try {
			String descricao = (String)movBody.get("descricao");
			Double valor = (Double)movBody.get("valor");
			String tipo = (String)movBody.get("tipo");
			List<String> errors = new ArrayList<String>();
			
			// Tratando possiveis erros
			if(descricao.isEmpty()) {
		    	errors.add("Informe a descrição do movimento");
			}
			if(valor <= 0 || valor.isNaN()) {
		    	errors.add("Informe o valor do movimento");
			}
			if(tipo.isEmpty()) {
		    	errors.add("Informe o tipo do movimento");
			}
			if(tipo.equals("P")){
		    } 
			else if(tipo.equals("D")){
		    } 
			else {
				errors.add("Tipo de movimento inválido");
		    } 
			

			if(errors.isEmpty()) {
				Optional<Colaborador> colab= colaboradorRepository.findById(colabId);
				if (colab != null) {
					Colaborador colaborador = colab.get();
					Folha_Pagamento folha = colaborador.getFolha();
					
					Movimento movimento = new Movimento(descricao, valor, tipo);
					movimento.setColaborador(colaborador);
					movimento.setFolha(folha);
					repository.save(movimento);
					
					MovimentoResponse response = new MovimentoResponse(true, "Movimento criado com sucesso!", null);
					response.setMovimento(repository.findById(movimento.getId()));
					return ResponseEntity.status(HttpStatus.CREATED).body(response);
					
				}
				else {
					MovimentoResponse response = new MovimentoResponse(true, "Funcionario não encontrado!", null);
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}
			}
			else {
				MovimentoResponse response = new MovimentoResponse(false, "Erro ao criar o movimento", errors);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			
		} 
		catch (Exception error) {
			List<String> errors = new ArrayList<String>();
	    	errors.add(error.toString());
			MovimentoResponse response = new MovimentoResponse(false, "Não foi possível criar o movimento", errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	@PutMapping(value = "/movimentos/{id}")
	ResponseEntity<MovimentoResponse> replaceColaborador(@PathVariable("id") Long id, @RequestBody Map<String, Object> putMovimento) {
		try {
			Optional<Movimento> getMovimento = repository.findById(id);
			Movimento movimento = getMovimento.get();
			
			String descricao = (String)putMovimento.get("descricao");
			Double valor = (Double)putMovimento.get("valor");
			String type = (String)putMovimento.get("tipo");
			List<String> errors = new ArrayList<String>();

			MovimentoType movType = null;
			
			if(descricao.isEmpty()) {
		    	errors.add("Informe a descrição do movimento");
			}
			if(valor < 0 || valor.isNaN()) {
		    	errors.add("Informe o valor do movimento");
			}
			if(type.isEmpty()) {
		    	errors.add("Informe o tipo do movimento");
			}
			if(type.equals("P")){
				movType = MovimentoType.P;
		    } 
		    else if(type.equals("D")){
		    	movType = MovimentoType.D;
		    } 
		    else {
		    	errors.add("Tipo de movimento inválido");
		    }
			
			if(errors.isEmpty()) {
				movimento.setDescricao((String)putMovimento.get("descricao"));
				movimento.setValor((Double)putMovimento.get("valor"));
				movimento.setMovimentoType(movType);

				repository.save(movimento);
				
				MovimentoResponse response = new MovimentoResponse(true, "Movimento atualizado com sucesso!", null);
				response.setMovimento(repository.findById(movimento.getId()));
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
			else {
				MovimentoResponse response = new MovimentoResponse(false, "Erro ao atualizar movimento", errors);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			
		}
		catch(Exception error) {
			List<String> errors = new ArrayList<String>();
	    	errors.add(error.toString());
			MovimentoResponse response = new MovimentoResponse(false, "Erro ao atualizar o movimento!", errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	@DeleteMapping("/movimentos/{id}")
	ResponseEntity<MovimentoResponse> delete(@PathVariable Long id) {
		try {
			repository.deleteById(id);
			MovimentoResponse response = new MovimentoResponse(true, "Movimento excluído com sucesso", null);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		catch(Exception error) {
			List<String> errors = new ArrayList<String>();
	    	errors.add(error.toString());
			MovimentoResponse response = new MovimentoResponse(false, "Erro ao excluir o movimento", errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	    
	}
}
