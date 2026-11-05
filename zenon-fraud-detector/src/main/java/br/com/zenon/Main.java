package br.com.zenon;

import br.com.zenon.fraud.FraudAnalyzer;
import br.com.zenon.fraud.Transaction;
import br.com.zenon.fraud.TransactionIngestor;

import java.util.List;

public class Main {
    static void main() throws Exception {
        TransactionIngestor ingestor = new TransactionIngestor("data/PS_20174392719_1491204439457_log.csv");
        List<Transaction> transactions = ingestor.getTransactions(50000);

        FraudAnalyzer fraudAnalyzer = new FraudAnalyzer(transactions);
        fraudAnalyzer.analyze();
    }
}
