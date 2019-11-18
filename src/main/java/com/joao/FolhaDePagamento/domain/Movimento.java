package com.joao.FolhaDePagamento.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "movimento")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Movimento {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private String descricao;
	private Double valor;
	private String nomeColaborador;
	private long idColaborador;
	private MovimentoType movimentoType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "folha")
	@JsonBackReference
	private Folha_Pagamento folha;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "colaborador")
	@JsonBackReference
	private Colaborador colaborador;
	
	public Movimento() {}
	public Movimento(String descricao, Double valor, String movimentoType){
		super();
		this.descricao = descricao;
	    this.valor = valor;	   
		if(movimentoType.equals("P")){
	      this.movimentoType = MovimentoType.P;
	    } 
	    else if(movimentoType.equals("D")){
	      this.movimentoType = MovimentoType.D;
	    }
	}

	public long getId() {
		return id;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public MovimentoType getMovimentoType() {
		return movimentoType;
	}

	public void setMovimentoType(MovimentoType movimentoType) {
		this.movimentoType = movimentoType;
	}
	
	public Folha_Pagamento getFolha() {
		return folha;
	}
	public void setFolha(Folha_Pagamento folha) {
		this.folha = folha;
	}
	public Colaborador getColaborador() {
		return colaborador;
	}
	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
		setIdColaborador();
		setNomeColaborador();
	}
	public void setIdColaborador() {
		this.idColaborador = this.colaborador.getid();
	}
	public long getIdColaborador() {
		return idColaborador;
	}
	public String getNomeColaborador() {
		return nomeColaborador;
	}
	public void setNomeColaborador() {
		this.nomeColaborador = getColaborador().getNome();
	}
}
