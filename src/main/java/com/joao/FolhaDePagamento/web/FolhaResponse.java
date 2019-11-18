package com.joao.FolhaDePagamento.web;

import java.util.List;
import java.util.Optional;

import com.joao.FolhaDePagamento.domain.Folha_Pagamento;

public class FolhaResponse {
	private Boolean success;
	private String message;
	private List<String> errors;
	private Optional<Folha_Pagamento> folha;
	
	public FolhaResponse(Boolean success, String message, List<String> errors) {
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

	public Optional<Folha_Pagamento> getFolha() {
		return folha;
	}

	public void setFolha(Optional<Folha_Pagamento> folha) {
		this.folha = folha;
	}
}
