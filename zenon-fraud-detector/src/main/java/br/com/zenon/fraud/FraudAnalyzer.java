package br.com.zenon.fraud;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FraudAnalyzer {

    private final List<Transaction> transactions;

    public FraudAnalyzer(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void analyze() {

        List<Transaction> listaFraudes = transactions.stream().filter(Transaction::isFraud).toList();

        System.out.println("1. Total de Fraudes: " + listaFraudes.size());

        List<Transaction> listaFraudesOrdenada = listaFraudes.stream().sorted(Comparator.comparing(Transaction::amount).reversed()).toList();

        List<Transaction> top3 = listaFraudesOrdenada.stream().limit(3).toList();
        System.out.println("2. Top 3 Fraudes de Maior Valor: ");
        top3.stream().map(Transaction::amount).forEach(x -> System.out.println(x.toPlainString()));

        List<String> listaNomeClientes = listaFraudesOrdenada.stream()
                .map(t -> t.customerOrig().name()).distinct().limit(5).toList();

        System.out.println("3. Clientes suspeitos: ");
        listaNomeClientes.forEach(System.out::println);

        BigDecimal valorTotalFraude = listaFraudesOrdenada.stream().map(Transaction::amount).reduce(BigDecimal.ZERO, BigDecimal::add);

        System.out.println("4. Prejuízo total: " + valorTotalFraude.toPlainString());

        Map<Transaction.TransactionType, Long> colecaoTransacaoPorTipo = listaFraudesOrdenada.stream().collect(Collectors.groupingBy(Transaction::type, Collectors.counting()));

        System.out.println("5. Fraudes por Tipo: ");

        colecaoTransacaoPorTipo.forEach((x, y) -> System.out.println(" - " + x.name() + ": " + y.toString()));



    }
}
