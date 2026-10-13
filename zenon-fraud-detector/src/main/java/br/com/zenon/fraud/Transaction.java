package br.com.zenon.fraud;

import java.math.BigDecimal;

public record Transaction(
        int step,
        TransactionType type,
        BigDecimal amount,
        String nameOrig,
        BigDecimal oldBalanceOrg,
        BigDecimal newBalanceOrig,
        String nameDest,
        BigDecimal oldBalanceDest,
        BigDecimal newBalanceDest,
        boolean isFraud,
        boolean isFlaggedFraud) {

    @Override
    public String toString() {
        return "step:" + step +
                ", type:" + type +
                ", amount:" + amount +
                ", nameOrig:" + nameOrig +
                ", oldBalanceOrg:" + oldBalanceOrg +
                ", newBalanceOrig:" + newBalanceOrig +
                ", nameDest:" + nameDest +
                ", oldBalanceDest:" + oldBalanceDest +
                ", newBalanceDest:" + newBalanceDest +
                ", isFraud:" + (isFraud ? "1" : "0") +
                ", isFlaggedFraud:" + (isFlaggedFraud ? "1" : "0");
    }

    public enum TransactionType {
        CASH_IN,
        CASH_OUT,
        DEBIT,
        PAYMENT,
        TRANSFER;
    }
}
