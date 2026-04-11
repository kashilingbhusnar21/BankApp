package repository;

import domain.Transaction;

import java.util.*;

public class TransactionRepository {
    private final Map<String, List<Transaction>> txByAccount=new HashMap<>();



    public void add(Transaction transaction) {
        System.out.println("Saving transaction for account: " + transaction.getAccountNumber());

        List<Transaction> list = txByAccount.computeIfAbsent(
                transaction.getAccountNumber(),
                k -> new ArrayList<>()
        );

        list.add(transaction);
    }
    public  List<Transaction> findByAccount(String account) {
        return new ArrayList<>(txByAccount.getOrDefault(account,Collections.emptyList()));
    }
}
