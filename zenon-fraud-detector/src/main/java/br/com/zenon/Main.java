package br.com.zenon;

import br.com.zenon.fraud.Transaction;
import br.com.zenon.fraud.TransactionIngestor;

import java.util.List;

public class Main {
    static void main() {
        TransactionIngestor ingestor = new TransactionIngestor("data/PS_20174392719_1491204439457_log.csv");
        List<Transaction> transactions = ingestor.getTransactions(1000);

        transactions.stream().limit(10).forEach(System.out::println);
    }
}
