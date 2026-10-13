package br.com.zenon.fraud;

import br.com.zenon.fraud.Transaction.TransactionType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class TransactionIngestor {
    private String fileName;
    private List<Transaction> transactions;

    public TransactionIngestor(String fileName) {
        this.fileName = fileName;
    }

    public List<Transaction> getTransactions(int maxLines) {
        transactions = new ArrayList<>();

        if(fileName == null || fileName.isBlank()) {
            throw new IllegalArgumentException("Nome do arquivo não pode ser branco ou nulo.");
        }

        Path path = Paths.get(fileName);

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
            String line;
            int count = 0;
            while((line = bufferedReader.readLine()) != null && count < maxLines) {
                if(count == 0) {
                    count++;
                    continue;
                }

                String[] lineData = line.split(",");


//                int step,
//                Transaction.TransactionType type,
//                BigDecimal amount,
//                TransactionCustomer customerOrig,
//                          String name, BigDecimal oldBalance, BigDecimal newBalance
//                TransactionCustomer customerDest,
//                          String name, BigDecimal oldBalance, BigDecimal newBalance
//                boolean isFraud,
//                boolean isFlaggedFraud

                Transaction transaction = new Transaction(
                        Integer.parseInt(lineData[0].trim()),
                        TransactionType.valueOf(lineData[1].trim()),
                        new BigDecimal(lineData[2].trim()),
                        new TransactionCustomer(
                                lineData[3].trim(),
                                new BigDecimal(lineData[4].trim()),
                                new BigDecimal(lineData[5].trim())
                        ),
                        new TransactionCustomer(
                                lineData[6].trim(),
                                new BigDecimal(lineData[7].trim()),
                                new BigDecimal(lineData[8].trim())
                        ),
                        (Integer.parseInt(lineData[9].trim()) == 1),
                        (Integer.parseInt(lineData[10].trim()) == 1)
                );

                transactions.add(transaction);
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return transactions;
    }
}
