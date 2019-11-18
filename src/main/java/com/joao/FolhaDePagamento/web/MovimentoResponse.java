package com.joao.FolhaDePagamento.web;

import java.util.List;
import java.util.Optional;

import com.joao.FolhaDePagamento.domain.Movimento;;

public class MovimentoResponse {
	private Boolean success;
	private String message;
	private List<String> errors;
	private Optional<Movimento> movimento;
	
	public MovimentoResponse(Boolean success, String message, List<String> errors) {
		super();
		this.success = success;
		this.message = message;
		this.errors = errors;
	}

	public Boolean getSuccess() {
		return success;
	}

	public void setSuccess(Boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}

	public Optional<Movimento> getMovimento() {
		return movimento;
	}

	public void setMovimento(Optional<Movimento> movimento) {
		this.movimento = movimento;
	}
}
