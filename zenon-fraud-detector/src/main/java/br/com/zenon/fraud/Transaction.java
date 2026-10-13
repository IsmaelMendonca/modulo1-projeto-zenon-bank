package br.com.zenon.fraud;

import java.math.BigDecimal;

public record Transaction(
        int step,
        TransactionType type,
        BigDecimal amount,
        TransactionCustomer customerOrig,
        TransactionCustomer customerDest,
        boolean isFraud,
        boolean isFlaggedFraud) {

    @Override
    public String toString() {
        return "Transaction[" +
                "step:" + step +
                ", type:" + type +
                ", amount:" + amount +
                ", nameOrig:" + customerOrig.name() +
                ", oldBalanceOrig:" + customerOrig.oldBalance() +
                ", newBalanceOrig:" + customerOrig.newBalance() +
                ", nameDest:" + customerDest.name() +
                ", oldBalanceDest:" + customerDest.oldBalance() +
                ", newBalanceDest:" + customerDest.newBalance() +
                ", isFraud:" + (isFraud ? "1" : "0") +
                ", isFlaggedFraud:" + (isFlaggedFraud ? "1" : "0") + "]";
    }

    public enum TransactionType {
        CASH_IN,
        CASH_OUT,
        DEBIT,
        PAYMENT,
        TRANSFER;
    }
}
