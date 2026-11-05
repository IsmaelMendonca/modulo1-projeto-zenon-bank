package br.com.zenon.fraud;

import java.util.List;
import java.util.Optional;

public class TransactionListRepository implements TransactionRepository {

    List<Transaction> transactions;

    public TransactionListRepository(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public Optional<Transaction> getByCustomerName(String customerName) {
        if(transactions == null || transactions.isEmpty()) {
            throw new IllegalArgumentException("Transactions list is empty");
        }

        if(customerName == null || customerName.isEmpty()) {
            throw new IllegalArgumentException("Customer name is empty");
        }

        return transactions.stream()
                .filter(transaction -> transaction.customerOrig().name().equals(customerName))
                .findFirst();
    }
}
