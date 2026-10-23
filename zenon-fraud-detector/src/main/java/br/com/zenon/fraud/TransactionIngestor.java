package br.com.zenon.fraud;

import br.com.zenon.fraud.Transaction.TransactionType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransactionIngestor {
    private String fileName;
    private List<Transaction> transactions;

    public TransactionIngestor(String fileName) {
        this.fileName = fileName;
    }

    public List<Transaction> getTransactions(int maxLines) throws Exception {
        transactions = new ArrayList<>();

        if(fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("Nome do arquivo não pode ser branco ou nulo.");
        }

        Path path = Paths.get(fileName);

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            int count = 0;
            while ((line = bufferedReader.readLine()) != null && count < maxLines) {
                if (count == 0) {
                    count++;
                    continue;
                }

                try {
                    transactions.add(getTransaction(line.split(",", -1)));
                } catch (TransactionException e) {
                    System.err.println("Erro: " + line + " | " + e.getMessage());
                }
                count++;
            }
        }

        return transactions;
    }

    private Transaction getTransaction(String[] lineData) throws TransactionException {
        if (lineData.length < 11) {
            throw new TransactionException("Invalid column count: " + lineData.length);
        }

        int step;
        try {
            step = Integer.parseInt(lineData[0].trim());
        } catch (NumberFormatException e) {
            throw new TransactionException("Invalid step value: " + lineData[0], e);
        }

        if (step < 1) {
            throw new TransactionException("step should be positive: " + step);
        }

        TransactionType transactionType = Arrays.stream(TransactionType.values())
                .filter(x -> x.name().equalsIgnoreCase(lineData[1].trim()))
                .findFirst()
                .orElseThrow(() -> new TransactionException("Invalid transaction type: " + lineData[1]));

        BigDecimal amount = validateAndGetBigDecimalValue(lineData[2], "amount");

        TransactionCustomer customerOrig = new TransactionCustomer(
                validateName(lineData[3], "nameOrig"),
                validateAndGetBigDecimalValue(lineData[4], "oldBalanceOrig"),
                validateAndGetBigDecimalValue(lineData[5], "newBalanceOrig")
        );
        TransactionCustomer customerDest = new TransactionCustomer(
                validateName(lineData[6], "nameDest"),
                validateAndGetBigDecimalValue(lineData[7], "oldBalanceDest"),
                validateAndGetBigDecimalValue(lineData[8], "newBalanceDest")
        );
        boolean isFraud = parseFlag(lineData[9], "isFraud");
        boolean isFlaggedFraud = parseFlag(lineData[10], "isFlaggedFraud");

        return new Transaction(
                step,
                transactionType,
                amount,
                customerOrig,
                customerDest,
                isFraud,
                isFlaggedFraud
        );
    }

    private String validateName(String data, String tag) throws TransactionException {
        if (data == null || data.isBlank()) {
            throw new TransactionException(tag + " should not be empty");
        }
        return data.trim();
    }

    private boolean parseFlag(String data, String tag) throws TransactionException {
        String value = data == null ? "" : data.trim();
        if (value.equals("1")) return true;
        if (value.equals("0")) return false;
        throw new TransactionException("Invalid " + tag + " value: " + data);
    }

    private BigDecimal validateAndGetBigDecimalValue(String data, String tag) throws TransactionException {
        if(data == null || data.isBlank()) {
            throw new TransactionException(tag + " should not be empty");
        }

        try {
            BigDecimal value = new BigDecimal(data.trim().replace(",", "."));

            if(value.signum() < 0) {
                throw new TransactionException(tag + " should be positive: " + data);
            }

            return value;

        } catch (NumberFormatException e) {
            throw new TransactionException("Invalid " + tag + " value: " + data, e);
        }
    }
}
