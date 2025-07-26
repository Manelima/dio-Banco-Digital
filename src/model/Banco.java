package model;

import java.util.ArrayList;
import java.util.List;

public class Banco {

    private String nome;
    private List<IConta> contas;
    private List<Cliente> clientes;

    public Banco(String nome) {
        this.nome = nome;
        this.contas = new ArrayList<>();
        this.clientes = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void adicionarConta(IConta conta) {
        this.contas.add(conta);
    }

    public Cliente criarNovoCliente(String nome, String cpf, String senha) {
        // Validação simples para não criar clientes com mesmo CPF
        for (Cliente c : clientes) {
            if (c.getCpf().equals(cpf)) {
                System.out.println("Erro: CPF já cadastrado.");
                return null;
            }
        }
        Cliente novoCliente = new Cliente(nome, cpf, senha);
        this.clientes.add(novoCliente);
        return novoCliente;
    }

    public Cliente autenticarCliente(String cpf, String senha) {
        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpf) && cliente.verificarSenha(senha)) {
                return cliente;
            }
        }
        return null;
    }

    public IConta buscarContaPorNumero(int numero) {
        for (IConta conta : contas) {
            if (conta.getNumero() == numero) {
                return conta;
            }
        }
        return null;
    }

    /**
     * Método mais robusto para encontrar a conta de um cliente logado.
     * Na vida real, um cliente poderia ter múltiplas contas, mas para este
     * desafio, vamos assumir que ele tem apenas uma.
     */
    public IConta buscarContaPorCliente(Cliente cliente) {
        if (cliente == null) return null;
        for (IConta conta : this.contas) {
            if (conta.getCliente().getCpf().equals(cliente.getCpf())) {
                return conta;
            }
        }
        return null;
    }
}