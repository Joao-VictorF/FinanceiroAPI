package com.joao.FolhaDePagamento.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "colaborador")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Colaborador {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String nome, endereco, telefone, bairro, cep, cpf, cargo;
	private Double salario;
	
	 
	@OneToMany(cascade = CascadeType.ALL, mappedBy="colaborador")
	@JsonManagedReference
	private List<Movimento> movimentos;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "folha")
	@JsonBackReference
	private Folha_Pagamento folha;
	
	public Colaborador () {}
	
	public Colaborador(String nome, String endereco, String telefone, String bairro, String cep,
	String cpf, String cargo, Double salario){
		//super();
		this.nome = nome;
	    this.endereco = endereco;
	    this.telefone = telefone;
	    this.bairro = bairro;
	    this.cep = cep;
	    this.cpf = cpf;
	    this.cargo = cargo;
	    this.salario = salario;
	}
	
	public Double calcularSalario(Folha_Pagamento folha) {
		Double salario = this.salario;
	    for(Movimento movimento: folha.getMovimentos()){
	      if(movimento.getColaborador().getid() == this.id){
	        if(movimento.getMovimentoType() == MovimentoType.P){
	          salario += movimento.getValor();
	        } 
	        else if(movimento.getMovimentoType() == MovimentoType.D){
	          salario -= movimento.getValor();
	        }
	      }
	    }
	    return salario;
	}

	public Double getSalario() {
		Double salario = calcularSalario(this.folha);
		return salario;
	}
	
	public long getid() {
		return id;
	}

	public Folha_Pagamento getFolha() {
		return folha;
	}

	public void setFolha(Folha_Pagamento folha) {
		this.folha = folha;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

}