package service;

import model.IConta;

public class Investimentos {

    public void aplicarCDB(IConta conta, double valor) {
        System.out.printf("Aplicando R$ %.2f em CDB na conta de %s...%n", valor, conta.getCliente().getNome());
        conta.sacar(valor);
        System.out.println("Aplicação realizada com sucesso!");
        conta.imprimirExtrato();
    }
}