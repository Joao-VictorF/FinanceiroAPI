package com.joao.FolhaDePagamento.web;

import java.util.List;

import com.joao.FolhaDePagamento.domain.Movimento;;

public class MovimentosResponse {
	private Boolean success;
	private String message;
	private List<String> errors;
	private List<Movimento> movimentos;
	
	public MovimentosResponse(Boolean success, String message, List<String> errors) {
		super();
		this.success = success;
		this.message = message;
		this.errors = errors;
	}
	
	public List<Movimento> getMovimentos() {
		return movimentos;
	}

	public void setMovimentos(List<Movimento> movimentos) {
		this.movimentos = movimentos;
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
}
