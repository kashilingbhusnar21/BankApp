package domain;

public class Account {
    private String accountNumber;
    //private String customerId;
    private String Name;
    private String CustomerEmail;
    private Double balance;
    private String accountType;

    public Account(String accountNumber, String Name,String CustomerEmail, Double balance, String accountType) {
        this.accountNumber = accountNumber;
        this.Name=Name;
        this.CustomerEmail=CustomerEmail;
      //  this.customerId = customerId;
        this.balance = balance;
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getCustomerEmail() {
        return CustomerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        CustomerEmail = customerEmail;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
}
