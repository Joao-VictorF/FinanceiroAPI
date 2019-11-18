package com.joao.FolhaDePagamento.domain;

public enum MovimentoType {
  P(1), D(2);
  int option;
  MovimentoType(int option){
    this.option = option;
  }
}