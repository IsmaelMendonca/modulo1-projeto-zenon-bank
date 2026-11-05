package br.com.zenon;

import br.com.zenon.fraud.FraudAnalyzer;
import br.com.zenon.fraud.Transaction;
import br.com.zenon.fraud.TransactionIngestor;
import br.com.zenon.fraud.TransactionListRepository;

import java.util.List;
import java.util.Optional;

public class Main {
    static void main() throws Exception {
        TransactionIngestor ingestor = new TransactionIngestor("data/PS_20174392719_1491204439457_log.csv");
        List<Transaction> transactions = ingestor.getTransactions(100000);

        TransactionListRepository transactionListRepository = new TransactionListRepository(transactions);

        imprimirResultadoBusca(transactionListRepository, "C12345");
        imprimirResultadoBusca(transactionListRepository, "C1231006815");

        long tempoInicialLista = System.nanoTime();

        transactionListRepository.getByCustomerName("C1868032458");

        long tempoFinalLista = System.nanoTime();
        System.out.println("Tempo de busca lista: ".concat(Long.toString((tempoFinalLista - tempoInicialLista)/1000000)).concat(" ms"));


        long tempoInicialMap = System.nanoTime();

        transactionListRepository.getByCustomerName("C1868032458");

        long tempoFinalMap = System.nanoTime();
        System.out.println("Tempo de busca mapa: ".concat(Long.toString((tempoFinalMap - tempoInicialMap)/1000000)).concat(" ms"));
    }

    private static void imprimirResultadoBusca(TransactionListRepository transactionListRepository, String nome) {
        Transaction registro = transactionListRepository.getByCustomerName(nome).orElse(null);

        if(registro == null) {
            System.out.println("Transação não encontrada para o cliente: ".concat(nome));
        } else {
            System.out.println(registro);
        }
    }
}
