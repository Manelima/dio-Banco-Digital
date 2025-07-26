package model;

public abstract class Conta implements IConta {

    private static final int AGENCIA_PADRAO = 1;
    private static int SEQUENCIAL = 1;

    protected int agencia;
    protected int numero;
    protected double saldo;
    protected Cliente cliente;

    public Conta(Cliente cliente) {
        this.agencia = Conta.AGENCIA_PADRAO;
        this.numero = SEQUENCIAL++;
        this.cliente = cliente;
        this.saldo = 0.0;
    }

    @Override
    public void sacar(double valor) {
        if (valor > 0 && this.saldo >= valor) {
            this.saldo -= valor;
        } else {
            System.out.println("Saque não realizado. Saldo insuficiente ou valor inválido.");
        }
    }

    @Override
    public void depositar(double valor) {
        if (valor > 0) {
            this.saldo += valor;
        } else {
            System.out.println("Depósito não realizado. Valor inválido.");
        }
    }

    @Override
    public void transferir(double valor, IConta contaDestino) {
        if (valor > 0 && this.saldo >= valor) {
            this.sacar(valor);
            contaDestino.depositar(valor);
        } else {
            System.out.println("Transferência não realizada. Saldo insuficiente ou valor inválido.");
        }
    }

    @Override
    public int getAgencia() {
        return agencia;
    }

    @Override
    public int getNumero() {
        return numero;
    }

    @Override
    public double getSaldo() {
        return saldo;
    }

    @Override
    public Cliente getCliente() {
        return cliente;
    }

    protected void imprimirInfosComuns() {
        System.out.printf("Titular: %s%n", this.cliente.getNome());
        System.out.printf("CPF: %s%n", this.cliente.getCpf());
        System.out.printf("Agencia: %d%n", this.agencia);
        System.out.printf("Numero: %d%n", this.numero);
        System.out.printf("Saldo: R$ %.2f%n", this.saldo);
    }
}