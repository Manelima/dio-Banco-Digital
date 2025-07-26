

import model.*;
import service.Investimentos;

import java.util.Scanner;
import java.util.Locale;

public class Main {

    private static Cliente clienteLogado = null;

    public static void main(String[] args) {
        Locale.setDefault(Locale.US);
        Scanner scanner = new Scanner(System.in);
        Banco meuBanco = new Banco("Banco Digital");

        while (true) {
            System.out.println("\n======================================");
            System.out.println("  Bem-vindo ao " + meuBanco.getNome() + "!");
            System.out.println("  Recife, 26 de julho de 2025");
            System.out.println("======================================");

            if (clienteLogado != null) {
                System.out.println("Olá, " + clienteLogado.getNome() + "!");
                exibirMenuLogado();
            } else {
                exibirMenuDeslogado();
            }
            System.out.print("Sua escolha: ");


            int escolha = -1;
            try {
                escolha = scanner.nextInt();
            } catch (Exception e) {
                System.out.println("Entrada inválida. Por favor, digite um número.");
            } finally {
                scanner.nextLine();
            }

            if (escolha != -1) {
                if (clienteLogado != null) {
                    processarEscolhaLogado(escolha, meuBanco, scanner);
                } else {
                    processarEscolhaDeslogado(escolha, meuBanco, scanner);
                }
            }
        }
    }

    private static void exibirMenuDeslogado() {
        System.out.println("1 - Login");
        System.out.println("2 - Criar Conta");
        System.out.println("3 - Sair");
    }

    private static void exibirMenuLogado() {
        System.out.println("1 - Realizar Operações (Saque, Depósito, Transferência)");
        System.out.println("2 - Ver Extrato");
        System.out.println("3 - Investimentos");
        System.out.println("4 - Suporte Técnico");
        System.out.println("5 - Logout (Sair da conta)");
    }

    private static void processarEscolhaDeslogado(int escolha, Banco banco, Scanner scanner) {
        switch (escolha) {
            case 1:
                fazerLogin(banco, scanner);
                break;
            case 2:
                criarConta(banco, scanner);
                break;
            case 3:
                System.out.println("Obrigado por usar nosso banco!");
                scanner.close();
                System.exit(0);
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
    }

    private static void processarEscolhaLogado(int escolha, Banco banco, Scanner scanner) {
        IConta contaDoCliente = banco.buscarContaPorCliente(clienteLogado);

        if (contaDoCliente == null && escolha != 5) {
            System.out.println("Erro: Não foi possível encontrar uma conta associada a este cliente.");
            return;
        }

        switch (escolha) {
            case 1:
                realizarOperacoes(contaDoCliente, banco, scanner);
                break;
            case 2:
                contaDoCliente.imprimirExtrato();
                break;
            case 3:
                Investimentos inv = new Investimentos();
                System.out.print("Digite o valor para aplicar em CDB: ");
                double valorCDB = scanner.nextDouble();
                scanner.nextLine();
                inv.aplicarCDB(contaDoCliente, valorCDB);
                break;
            case 4:
                System.out.println("Contatando suporte... (24) 99999-9999");
                break;
            case 5:
                clienteLogado = null;
                System.out.println("Logout realizado com sucesso.");
                break;
            default:
                System.out.println("Opção inválida.");
                break;
        }
    }

    private static void fazerLogin(Banco banco, Scanner scanner) {
        System.out.print("Digite seu CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();

        clienteLogado = banco.autenticarCliente(cpf, senha);

        if (clienteLogado == null) {
            System.out.println("CPF ou senha inválidos.");
        }
    }

    private static void criarConta(Banco banco, Scanner scanner) {
        System.out.print("Digite seu nome completo: ");
        String nome = scanner.nextLine();
        System.out.print("Digite seu CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Crie uma senha: ");
        String senha = scanner.nextLine();

        Cliente novoCliente = banco.criarNovoCliente(nome, cpf, senha);

        if(novoCliente != null) {
            System.out.println("Escolha o tipo de conta: 1 - Corrente | 2 - Poupança");
            int tipoConta = scanner.nextInt();
            scanner.nextLine(); // Limpa buffer

            IConta novaConta = (tipoConta == 2) ? new ContaPoupanca(novoCliente) : new ContaCorrente(novoCliente);
            banco.adicionarConta(novaConta);

            System.out.println("\nConta criada com sucesso para " + nome + "!");
            novaConta.imprimirExtrato();
        }
    }

    private static void realizarOperacoes(IConta conta, Banco banco, Scanner scanner) {
        System.out.println("1 - Saque | 2 - Depósito | 3 - Transferência");
        System.out.print("Escolha a operação: ");
        int op = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Digite o valor: ");
        double valor = scanner.nextDouble();
        scanner.nextLine();

        if (op == 1) {
            conta.sacar(valor);
        } else if (op == 2) {
            conta.depositar(valor);
        } else if (op == 3) {
            System.out.print("Digite o número da conta de destino: ");
            int numDestino = scanner.nextInt();
            scanner.nextLine();
            IConta contaDestino = banco.buscarContaPorNumero(numDestino);
            if (contaDestino != null) {
                conta.transferir(valor, contaDestino);
            } else {
                System.out.println("Conta de destino não encontrada.");
            }
        } else {
            System.out.println("Operação inválida.");
        }
        System.out.println("Operação finalizada.");
        conta.imprimirExtrato();
    }
}