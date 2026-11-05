package br.com.zenon.fraud;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TransactionMapRepository implements TransactionRepository {

    Map<String, Transaction> transactions;

    public TransactionMapRepository(List<Transaction> transactions) {
        this.transactions = new HashMap<>();
        transactions.forEach(transaction -> this.transactions.put(transaction.customerOrig().name(), transaction));
    }

    public Optional<Transaction> getByCustomerName(String customerName) {
        if(transactions.isEmpty()) {
            throw new IllegalArgumentException("Transactions map is empty");
        }

        if(customerName == null || customerName.isEmpty()) {
            throw new IllegalArgumentException("Customer name is empty");
        }

        return Optional.of(transactions.get(customerName));
    }
}
