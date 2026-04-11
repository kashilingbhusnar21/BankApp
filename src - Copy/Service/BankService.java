package Service;

import domain.Account;
import domain.Transaction;
import repository.AccountRepository;

import java.util.List;
import java.util.Map;

public interface BankService {
    String openAccount(String name, String email, String accountType);

    List<Account> Listaccounts();

    void deposit(String accontNumber, Double amount, String note);

    void withdraw(String accountNumber, Double amount, String withdraw);

    void transfer(String from, String to, Double amount, String transfer);


    List<Transaction> getstatement(String account);

     List<Account> searchAccountByCustomername(String q);

}

//
    // void transfer(String from, String to, Double amount, String transfer);
//}
