package com.joao.FolhaDePagamento.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "folha")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Folha_Pagamento {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	private int mes, ano;
	@SuppressWarnings("unused")
	private float totalDescontos, totalProventos, totalSalarios;

	@OneToMany (cascade = CascadeType.ALL, mappedBy="folha")
	private List<Colaborador> colabs = null;
	
	@OneToMany (cascade = CascadeType.ALL, mappedBy="folha")
	private List<Movimento> movimentos = null;	
	
	public Folha_Pagamento() {}
	public Folha_Pagamento(int mes, int ano){
	    this.mes = mes;
	    this.ano = ano;
	}
	
	public void inserirMovimento(Movimento movimento) {
		this.movimentos.add(movimento);
	}
	
	public List<Movimento> getMovimentos(){
	    return movimentos;
	}  
	
	public void setColaboradores(List <Colaborador> colabs){
		this.colabs = colabs;
    }
	
	public void inserirColaborador(Colaborador colab) {
		this.colabs.add(colab);
	}
	
	public List<Colaborador> getColaboradores(){
	    return colabs;
	}
	
	public int getMes() {
		return mes;
	}
	
	public void setMes(int mes) {
		this.mes = mes;
	}
	
	public int getAno() {
		return ano;
	}
	
	public void setAno(int ano) {
		this.ano = ano;
	}
	
	public float getTotalDescontos() {
		float totalDescontos = 0;
		
		if(this.movimentos != null) {
			for(Movimento movimento: this.movimentos){
		      if(movimento.getMovimentoType() == MovimentoType.D){
		        totalDescontos += movimento.getValor();
		      }
		    }
		}
		
		return totalDescontos;
	}
	
	public float getTotalProventos() {
		float totalProventos = 0;
		
		if(this.movimentos != null) {
			for(Movimento movimento: this.movimentos){
		      if(movimento.getMovimentoType() == MovimentoType.P){
		    	  totalProventos += movimento.getValor();
		      }
		    }
		}
			
		return totalProventos;
	}
	
	public long getid() {
		return id;
	}
	
	public float getTotalSalarios() {
		float totalSalarios = 0;
		
		if(this.colabs != null) {
			for (Colaborador colab: this.colabs) {
				totalSalarios += colab.getSalario();
			}
		}
	    
		return totalSalarios;
	}	
}
