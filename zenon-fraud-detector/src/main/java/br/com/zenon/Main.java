package br.com.zenon;

import br.com.zenon.fraud.Transaction;
import br.com.zenon.fraud.TransactionIngestor;

import java.util.List;

public class Main {
    static void main() throws Exception {
        TransactionIngestor ingestor = new TransactionIngestor("data/paysim_with_bad_data.csv");
        List<Transaction> transactions = ingestor.getTransactions(1000);

        System.out.println(transactions.size());
        transactions.stream().limit(10).forEach(System.out::println);
    }
}
