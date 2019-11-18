package com.joao.FolhaDePagamento.web;

import java.util.List;

import com.joao.FolhaDePagamento.domain.Folha_Pagamento;

public class FolhasResponse {
	private Boolean success;
	private String message;
	private List<String> errors;
	private List<Folha_Pagamento> folhas;
	
	public FolhasResponse(Boolean success, String message, List<String> errors) {
		super();
		this.success = success;
		this.message = message;
		this.errors = errors;
	}
	
	public List<Folha_Pagamento> getFolhas() {
		return folhas;
	}

	public void setFolhas(List<Folha_Pagamento> folhas) {
		this.folhas = folhas;
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
