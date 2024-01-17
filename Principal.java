import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;


public class Principal   {
    public static void main(String[] args) {
        List<Funcionario> funcionarios = new ArrayList<>();

        // 3.1 Inserir todos os funcionários
        funcionarios.add(new Funcionario("João", LocalDate.of(1990, 10, 15), new BigDecimal("3000.00"), "Analista"));
        funcionarios.add(new Funcionario("Maria", LocalDate.of(1985, 5, 20), new BigDecimal("5000.50"), "Gerente"));
        funcionarios.add(new Funcionario("Pedro", LocalDate.of(1992, 12, 8), new BigDecimal("4000.75"), "Analista"));

        // 3.2 Remover o funcionário “João” da lista
        funcionarios.removeIf(funcionario -> funcionario.getNome().equals("João"));

        // 3.3 Imprimir todos os funcionários com todas suas informações
        imprimirFuncionarios(funcionarios);

        // 3.4 Funcionários recebem 10% de aumento
       funcionarios.forEach(funcionario -> funcionario.setSalario(funcionario.getSalario().multiply(new BigDecimal("1.10"))));
        // 3.5 Agrupar os funcionários por função em um MAP
        Map<String, List<Funcionario>> funcionariosPorFuncao = funcionarios.stream()
                .collect(Collectors.groupingBy(Funcionario::getFuncao));

        // 3.6 Imprimir os funcionários, agrupados por função
        imprimirFuncionariosPorFuncao(funcionariosPorFuncao);

        // 3.8 Imprimir os funcionários que fazem aniversário no mês 10 e 12
        imprimirAniversariantes(funcionarios, 10, 12);

        // 3.9 Imprimir o funcionário com a maior idade
        imprimirFuncionarioMaiorIdade(funcionarios);

        // 3.10 Imprimir a lista de funcionários por ordem alfabética
        Collections.sort(funcionarios, Comparator.comparing(Funcionario::getNome));
        imprimirFuncionarios(funcionarios);

        // 3.11 Imprimir o total dos salários dos funcionários
        imprimirTotalSalarios(funcionarios);

        // 3.12 Imprimir quantos salários mínimos ganha cada funcionário
        imprimirSalariosMinimos(funcionarios);
    }

    private static void imprimirFuncionarios(List<Funcionario> funcionarios) {
        System.out.println("Lista de Funcionários:");
        for (Funcionario funcionario : funcionarios) {
            System.out.println("Nome: " + funcionario.getNome() +
                    ", Data de Nascimento: " + funcionario.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")) +
                    ", Salário: " + formatarValor(funcionario.getSalario()) +
                    ", Função: " + funcionario.getFuncao());
        }
        System.out.println();
    }

    private static void imprimirFuncionariosPorFuncao(Map<String, List<Funcionario>> funcionariosPorFuncao) {
        System.out.println("Funcionários agrupados por função:");
        for (Map.Entry<String, List<Funcionario>> entry : funcionariosPorFuncao.entrySet()) {
            System.out.println("Função: " + entry.getKey());
            imprimirFuncionarios(entry.getValue());
        }
    }

    private static void imprimirAniversariantes(List<Funcionario> funcionarios, int... meses) {
        System.out.println("Funcionários que fazem aniversário nos meses " + Arrays.toString(meses) + ":");
        for (Funcionario funcionario : funcionarios) {
            if (Arrays.stream(meses).anyMatch(mes -> funcionario.getDataNascimento().getMonthValue() == mes)) {
                System.out.println("Nome: " + funcionario.getNome() +
                        ", Data de Nascimento: " + funcionario.getDataNascimento().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            }
        }
        System.out.println();
    }

    private static void imprimirFuncionarioMaiorIdade(List<Funcionario> funcionarios) {
        System.out.println("Funcionário com maior idade:");
        Funcionario funcionarioMaiorIdade = Collections.max(funcionarios, Comparator.comparing(f ->
                ChronoUnit.YEARS.between(f.getDataNascimento(), LocalDate.now())));
        System.out.println("Nome: " + funcionarioMaiorIdade.getNome() +
                ", Idade: " + ChronoUnit.YEARS.between(funcionarioMaiorIdade.getDataNascimento(), LocalDate.now()));
        System.out.println();
    }

    private static void imprimirTotalSalarios(List<Funcionario> funcionarios) {
        System.out.println("Total dos salários dos funcionários:");
        BigDecimal totalSalarios = funcionarios.stream()
                .map(Funcionario::getSalario)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        System.out.println(formatarValor(totalSalarios));
        System.out.println();
    }

    private static void imprimirSalariosMinimos(List<Funcionario> funcionarios) {
        System.out.println("Salários em quantidade de salários mínimos:");
        BigDecimal salarioMinimo = new BigDecimal("1212.00");
        for (Funcionario funcionario : funcionarios) {
            BigDecimal quantidadeSalariosMinimos = funcionario.getSalario().divide(salarioMinimo, 2, RoundingMode.DOWN);
            System.out.println("Nome: " + funcionario.getNome() +
                    ", Salários Mínimos: " + quantidadeSalariosMinimos);
        }
    }

    private static String formatarValor(BigDecimal valor) {
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        return decimalFormat.format(valor);
    }
}