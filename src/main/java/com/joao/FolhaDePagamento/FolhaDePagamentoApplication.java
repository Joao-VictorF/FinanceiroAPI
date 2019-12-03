package com.joao.FolhaDePagamento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.joao.FolhaDePagamento.domain.Colaborador;
import com.joao.FolhaDePagamento.domain.ColaboradorRepository;
import com.joao.FolhaDePagamento.domain.FolhaRepository;
import com.joao.FolhaDePagamento.domain.Folha_Pagamento;
import com.joao.FolhaDePagamento.domain.Movimento;
import com.joao.FolhaDePagamento.domain.MovimentoRepository;

@SpringBootApplication
public class FolhaDePagamentoApplication {
	@Autowired
	private FolhaRepository Frepository;
	@Autowired
	private ColaboradorRepository Crepository;
	@Autowired
	private MovimentoRepository Mrepository;
	
	public static void main(String[] args) {
		SpringApplication.run(FolhaDePagamentoApplication.class, args);
	}
	@Bean
	CommandLineRunner runner() {
		return args -> {
			Folha_Pagamento folha1 = new Folha_Pagamento(11, 2019);
			Frepository.save(folha1);
			
			Colaborador colab1 = new Colaborador("João Victor", "Rua X", "(85) 98824-2372", "Lagoa Redonda",
			"11111-11", "xxx.xxx.xxx-xx", "Desenvolvedor", (double) 1200);
			colab1.setFolha(folha1);
			Crepository.save(colab1);
			
			Colaborador colab2 = new Colaborador("Mauricio", "Rua X", "(85) 98212-2121", "Caucaia",
			"11111-11", "xxx.xxx.xxx-xx", "Desenvolvedor", (double) 800);
			colab2.setFolha(folha1);
			Crepository.save(colab2);
			
			Colaborador colab3 = new Colaborador("Alerrandro", "Rua X", "(85) 99228-0921", "Serrinha",
			"11111-11", "xxx.xxx.xxx-xx", "Desenvolvedor", (double) 600);
			colab3.setFolha(folha1);
			Crepository.save(colab3);
			
			Movimento m1 = new Movimento("Falta", (double) 500, "D");
			m1.setColaborador(colab2);
			m1.setFolha(folha1);
			Mrepository.save(m1);
			
			Movimento m2 = new Movimento("Décimo Terceiro", (double) 1200, "P");
			m2.setColaborador(colab1);
			m2.setFolha(folha1);
			Mrepository.save(m2);
		};
	
	}
}
