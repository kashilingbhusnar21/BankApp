package domain;

import java.time.LocalDateTime;

public class Transaction {
   // private String id;
    private String AccountNumber;
    private Double amount;
    private LocalDateTime timestamp;
    private Type type;
    private String note;

    public Transaction( String AccountNumber, Double amount, LocalDateTime timestamp, Type type, String note) {
       // this.id = id;
        this.AccountNumber = AccountNumber;
        this.amount = amount;
        this.timestamp = timestamp;
        this.type = type;
        this.note = note;


    }



    public String getAccountNumber() {
        return AccountNumber;
    }

    public void setAccountNumber(String AccountNumber) {
        this.AccountNumber = AccountNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

