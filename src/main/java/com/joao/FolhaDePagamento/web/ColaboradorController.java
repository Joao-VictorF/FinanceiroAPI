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
import com.joao.FolhaDePagamento.domain.FolhaRepository;
import com.joao.FolhaDePagamento.domain.Folha_Pagamento;

@CrossOrigin
@RestController
public class ColaboradorController {
	@Autowired
	private ColaboradorRepository repository;
	@Autowired
	private FolhaRepository FolhaRepository;
	
	@GetMapping(value = "/colaboradores")
	ResponseEntity<ColaboradoresResponse> getAll() {
		try {
			List<Colaborador> colabs = (List<Colaborador>) repository.findAll();
			if (colabs.isEmpty()) {
				ColaboradoresResponse response = new ColaboradoresResponse(true, "Consulta realizada com sucesso!", null);
				response.setColaboradores(colabs);
				return ResponseEntity.status(HttpStatus.OK).body(response);
	        }
			ColaboradoresResponse response = new ColaboradoresResponse(true, "Consulta realizada com sucesso!", null);
			response.setColaboradores(colabs);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		catch (Exception error) {
			List<String> errors= new ArrayList<String>();
			errors.add(error.toString());
			ColaboradoresResponse response = new ColaboradoresResponse(false, "Não foi possível realizar a consulta.", errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	@GetMapping(value = "/colaboradores/{id}")
	ResponseEntity<ColaboradorResponse> getById(@PathVariable("id") Long id) {
		try {
			Optional<Colaborador> colab = repository.findById(id);
			if(colab.isPresent()) {
				ColaboradorResponse response = new ColaboradorResponse(true, "Consulta realizada com sucesso!", null);
				response.setColaborador(colab);
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
			else {
				ColaboradorResponse response = new ColaboradorResponse(true, "Nenhum funcionário foi encontrado com o id " + id, null);
				response.setColaborador(colab);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			
		}
		catch (Exception error) {
			List<String> errors= new ArrayList<String>();
			errors.add(error.toString());
			ColaboradorResponse response = new ColaboradorResponse(false, "Funcionário não encontrado", errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	@PostMapping(value = "/colaboradores/{folhaID}")
	ResponseEntity<ColaboradorResponse> post(@RequestBody Map<String, Object> colab, @PathVariable("folhaID") Long folhaID) {
		try {
			String nome = (String)colab.get("nome");
			String endereco = (String)colab.get("endereco");
			String telefone = (String)colab.get("telefone");
			String bairro = (String)colab.get("bairro");
			String cep = (String)colab.get("cep");
			String cpf = (String)colab.get("cpf");
			String cargo = (String)colab.get("cargo");
			Double salario = (Double)colab.get("salario");
			List<String> errors = new ArrayList<String>();
			
			if(nome.isEmpty()) {
				errors.add("Informe o nome do funcionário!");
			}
			if(endereco.isEmpty()) {
				errors.add("Informe o endereço do funcionário!");
			}
			if(telefone.isEmpty()) {
				errors.add("Informe o telefone do funcionário!");
			}
			if(bairro.isEmpty()) {
				errors.add("Informe o bairro do funcionário!");
			}
			if(cep.isEmpty()) {
				errors.add("Informe o CEP do funcionário!");
			}
			if(cpf.isEmpty()) {
				errors.add("Informe o CPF do funcionário!");
			}
			if(cargo.isEmpty()) {
				errors.add("Informe o cargo do funcionário!");
			}
			if(salario == null || salario <=0) {
				errors.add("Informe um salário válido para o funcionário!");
			}

			if(errors.isEmpty()) {
				Optional<Folha_Pagamento> getFolha = FolhaRepository.findById(folhaID);
				
				if(getFolha.isPresent()) {
					Folha_Pagamento folha = getFolha.get();
					
					Colaborador colaborador = new Colaborador(nome, endereco, telefone, bairro, cep, cpf, cargo, salario);
					colaborador.setFolha(folha);
					repository.save(colaborador);
					
					ColaboradorResponse response = new ColaboradorResponse(true, "Funcionário criado com sucesso!", null);
					response.setColaborador(repository.findById(colaborador.getid()));
					return ResponseEntity.status(HttpStatus.CREATED).body(response);
				}
				else {
					errors.add("Nenhuma folha de pagamento foi encontrada com o id " + folhaID);
					ColaboradorResponse response = new ColaboradorResponse(false, "Não foi possível criar o funcionário!", errors);
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}
				
			}
			else {
				ColaboradorResponse response = new ColaboradorResponse(false, "Não foi possível criar o funcionário!", errors);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
			
		} 
		catch (Exception error) {
			List<String> errors = new ArrayList<String>();
			errors.add(error.toString());
			ColaboradorResponse response = new ColaboradorResponse(false, "Não foi possível criar o funcionário", errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	@PutMapping(value = "/colaboradores/{id}")
	ResponseEntity<ColaboradorResponse> put(@PathVariable("id") Long id, @RequestBody Map<String, Object> putColab) {
		try {
			Optional<Colaborador> colab = repository.findById(id);
			List<String> errors = new ArrayList<String>();
			
			if(colab.isPresent()) {
				Colaborador colaborador = colab.get();
				
				String nome = (String)putColab.get("nome");
				String endereco = (String)putColab.get("endereco");
				String telefone = (String)putColab.get("telefone");
				String bairro = (String)putColab.get("bairro");
				String cep = (String)putColab.get("cep");
				String cpf = (String)putColab.get("cpf");
				String cargo = (String)putColab.get("cargo");
				Double salario = (Double)putColab.get("salario");
				
				if(nome.isEmpty()) {
					errors.add("Informe o nome do funcionário!");
				}
				if(endereco.isEmpty()) {
					errors.add("Informe o endereço do funcionário!");
				}
				if(telefone.isEmpty()) {
					errors.add("Informe o telefone do funcionário!");
				}
				if(bairro.isEmpty()) {
					errors.add("Informe o bairro do funcionário!");
				}
				if(cep.isEmpty()) {
					errors.add("Informe o CEP do funcionário!");
				}
				if(cpf.isEmpty()) {
					errors.add("Informe o CPF do funcionário!");
				}
				if(cargo.isEmpty()) {
					errors.add("Informe o cargo do funcionário!");
				}
				if(salario == null || salario <=0) {
					errors.add("Informe um salário válido para o funcionário!");
				}
				
				if(errors.isEmpty()) {
					colaborador.setNome(nome);
					colaborador.setEndereco(endereco);
					colaborador.setTelefone(telefone);
					colaborador.setBairro(bairro);
					colaborador.setCep(cep);
					colaborador.setCpf(cpf);
					colaborador.setCargo(cargo);
					colaborador.setSalario(salario);
					
					repository.save(colaborador);
					
					ColaboradorResponse response = new ColaboradorResponse(true, "Funcionário atualizado com sucesso!", null);
					response.setColaborador(repository.findById(colaborador.getid()));
					return ResponseEntity.status(HttpStatus.OK).body(response);
				}
				else {
					ColaboradorResponse response = new ColaboradorResponse(false, "Não foi possível atualizar o funcionário!", errors);
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
				}
				
			}
			else {
				errors.add("Nenhuma funcionário foi encontrado com o id " + id);
				ColaboradorResponse response = new ColaboradorResponse(false, "Não foi possível atualizar o funcionário!", errors);
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		}
		catch(Exception error) {
			ColaboradorResponse response = new ColaboradorResponse(false, "Não foi possível atualizar o funcionário!", null);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
	
	@DeleteMapping("/colaboradores/{id}")
	ResponseEntity<ColaboradorResponse> delete(@PathVariable Long id) {
		try {
			repository.deleteById(id);
			ColaboradorResponse response = new ColaboradorResponse(true, "Funcionário excluído com sucesso", null);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
		catch(Exception error) {
			List<String> errors= new ArrayList<String>();
			errors.add(error.toString());
			ColaboradorResponse response = new ColaboradorResponse(false, "Não foi possível excluir o funcionário com id " + id, errors);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	    
	}
}
