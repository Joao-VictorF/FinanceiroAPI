package com.joao.FolhaDePagamento.web;

import java.util.List;
import java.util.Optional;

import com.joao.FolhaDePagamento.domain.Colaborador;

public class ColaboradorResponse {
	private Boolean success;
	private String message;
	private List<String> errors;
	private Optional<Colaborador> colaborador;
	
	public ColaboradorResponse(Boolean success, String message, List<String> errors) {
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

	public List<String> geterrors() {
		return errors;
	}

	public void seterrors(List<String> errors) {
		this.errors = errors;
	}

	public Optional<Colaborador> getColaborador() {
		return colaborador;
	}

	public void setColaborador(Optional<Colaborador> colab) {
		this.colaborador = colab;
	}
}
