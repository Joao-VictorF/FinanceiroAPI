package com.joao.FolhaDePagamento.web;

import java.util.List;

import com.joao.FolhaDePagamento.domain.Colaborador;

public class ColaboradoresResponse {
	private Boolean success;
	private String message;
	private List<String> errors;
	private List<Colaborador> colaboradores;
	
	public ColaboradoresResponse(Boolean success, String message, List<String> errors) {
		super();
		this.success = success;
		this.message = message;
		this.errors = errors;
	}
	
	public List<Colaborador> getColaboradores() {
		return colaboradores;
	}

	public void setColaboradores(List<Colaborador> colaboradores) {
		this.colaboradores = colaboradores;
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
}
