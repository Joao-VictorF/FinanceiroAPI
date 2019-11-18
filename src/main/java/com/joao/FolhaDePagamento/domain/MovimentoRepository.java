package com.joao.FolhaDePagamento.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface MovimentoRepository extends CrudRepository<Movimento, Long> {
	List<Movimento> findByIdColaborador(long idColaborador);
}
