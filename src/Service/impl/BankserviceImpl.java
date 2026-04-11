package Service.impl;
import java.sql.*;
import app.DBConnection;
import Service.BankService;
import domain.Account;
import domain.Customer;
import domain.Transaction;
import domain.Type;
import repository.AccountRepository;
import repository.CustomerRepository;
import repository.TransactionRepository;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

public  class BankserviceImpl implements BankService{
    private final AccountRepository accountRepository=new AccountRepository();
    private final TransactionRepository transactionRepository=new TransactionRepository();
    private final CustomerRepository customerRepository=new CustomerRepository();

    @Override
    public String openAccount(String name, String customerEmail, String accountType) {

        try {

            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Customer name cannot be empty");
            }

            if (customerEmail == null || customerEmail.isBlank()) {
                throw new IllegalArgumentException("Customer email cannot be empty");
            }

            if (accountType == null || accountType.isBlank()) {
                throw new IllegalArgumentException("Account type cannot be empty");
            }

            accountType = accountType.toUpperCase();

            if (!accountType.equals("SAVINGS") && !accountType.equals("CURRENT")) {
                throw new IllegalArgumentException("Invalid account type. Only SAVINGS or CURRENT allowed");
            }

          //  String customerId = UUID.randomUUID().toString();

            String accountNumber = getAccountNumber();

            Account account = new Account(
                    accountNumber,
                   // customerId,
                    name,
                    customerEmail,
                    0.0,
                    accountType
            );

            accountRepository.save(account);

            return accountNumber;

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }

        return null;
    }


    @Override
    public List<Account> Listaccounts() {

        return accountRepository.findAll().stream()
                .sorted(Comparator.comparing(Account::getAccountNumber))
                .collect(Collectors.toList());
    }

    @Override
    public void deposit(String accountNumber, Double amount, String note) {
        try {
            if (amount <= 0) {
                throw new IllegalArgumentException("amount must be greater than 0");
            }

           Account account = accountRepository.findByAccountNumber(accountNumber)
                    .orElseThrow(() -> new RuntimeException("account not found " + accountNumber));

           double newBalance = account.getBalance() + amount;

            account.setBalance(newBalance);

            accountRepository.updateBalance(accountNumber, newBalance);
            Transaction transaction = new Transaction(
                   // UUID.randomUUID().toString(),
                    accountNumber,
                    amount,
                    LocalDateTime.now(),
                    Type.DEPOSIT,
                    note
            );

            transactionRepository.add(transaction);

            System.out.println("Deposit successful");
        } catch (IllegalArgumentException e) {
            System.out.println("invalid input " + e.getMessage());
        } catch (RuntimeException e) {
            System.out.println(" opertaion failed " + e.getMessage());
        }
    }



    @Override
    public void withdraw(String accountNumber, Double amount, String note) {

        try {

            if (accountNumber == null || accountNumber.isBlank()) {
                throw new IllegalArgumentException("Account number cannot be empty");
            }

            if (amount == null) {
                throw new IllegalArgumentException("Amount cannot be null");
            }

            if (amount <= 0) {
                throw new IllegalArgumentException("Amount must be greater than 0");
            }

            Account account = accountRepository.findByAccountNumber(accountNumber)
                    .orElseThrow(() -> new RuntimeException("Account not found: " + accountNumber));

            if (account.getBalance() < amount) {
                throw new RuntimeException("Insufficient balance");
            }

            double newBalance = account.getBalance() - amount;

            account.setBalance(newBalance);

            accountRepository.updateBalance(accountNumber, newBalance);

            Transaction transaction = new Transaction(
                  //  UUID.randomUUID().toString(),
                    accountNumber,
                    amount,
                    LocalDateTime.now(),
                    Type.WITHDRAW,
                    note
            );

            transactionRepository.add(transaction);

            System.out.println("Withdraw successful");

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());

        } catch (RuntimeException e) {
            System.out.println("Operation failed: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
        }
    }

    @Override
    public void transfer(String fromAcc, String toAcc, Double amount, String note) {

        try {

            if (fromAcc == null || fromAcc.isBlank() || toAcc == null || toAcc.isBlank()) {
                throw new IllegalArgumentException("Account number cannot be empty");
            }

            if (fromAcc.equals(toAcc)) {
                throw new IllegalArgumentException("Cannot transfer to same account");
            }

            if (amount == null || amount <= 0) {
                throw new IllegalArgumentException("Amount must be greater than 0");
            }

            Account from = accountRepository. findByAccountNumber(fromAcc)
                    .orElseThrow(() -> new RuntimeException("From account not found"));

            Account to = accountRepository.findByAccountNumber(toAcc)
                    .orElseThrow(() -> new RuntimeException("To account not found"));

            if (from.getBalance() < amount) {
                throw new RuntimeException("Insufficient balance");
            }

            from.setBalance(from.getBalance() - amount);
            to.setBalance(to.getBalance() + amount);

            accountRepository.updateBalance(fromAcc, from.getBalance());
            accountRepository.updateBalance(toAcc, to.getBalance());

            Transaction transaction = new Transaction(
                  //  UUID.randomUUID().toString(),
                    fromAcc,
                    amount,
                    LocalDateTime.now(),
                    Type.TRANSFER_IN,
                    note
            );

            transactionRepository.add(transaction);

            System.out.println("Transfer successful");

        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input: " + e.getMessage());

        } catch (RuntimeException e) {
            System.out.println("Operation failed: " + e.getMessage());

        } catch (Exception e) {
            System.out.println("Unexpected error occurred");
            e.printStackTrace();
        }
    }

    @Override
    public List<Transaction> getstatement(String account) {
        return transactionRepository.findByAccount(account)
                .stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp))
                        .collect(Collectors.toList());


    }

    @Override


    /* @Override
    public List<Account> searchAccountByCustomername(String q) {
        String query=(q == null) ? " ":q.toLowerCase();
        List<Account> result=new ArrayList<>();
        for(Customer c : customerRepository.findAll()) {
            if(c.getName().toLowerCase().contains(query));
               // result.addAll(accountRepository.findByCustomerId(c.getId()));
        }
        result.sort(Comparator.comparing(Account::getAccountNumber));
        return result;
    }
*/
    public List<Account> searchAccountByCustomername(String q) {
        return accountRepository.searchByCustomerName(q);
    }
    private String getAccountNumber() {

        String sql = "SELECT MAX(id) FROM account";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            if (rs.next()) {
                int nextId = rs.getInt(1) + 1;
                return String.format("AC%06d", nextId);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "AC000001";
    }


    }

