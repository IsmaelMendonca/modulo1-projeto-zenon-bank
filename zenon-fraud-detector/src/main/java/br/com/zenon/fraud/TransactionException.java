package br.com.zenon.fraud;

public class TransactionException extends Exception {
    public TransactionException(String message) {
        super(message);
    }

    public TransactionException(String message, Exception e) {
        super(message, e);
    }
}
