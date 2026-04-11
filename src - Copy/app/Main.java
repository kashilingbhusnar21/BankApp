package app;

import Service.BankService;
import Service.impl.BankserviceImpl;

import java.util.Scanner;

//BankService bankService = new BankServiceImpl();
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankService bankService = new BankserviceImpl();

        // String scanner=sc.next
        boolean running = true;
        System.out.println("welcome t console bank ");

        //boolean running = true;
        while (running) {
            System.out.println("""
                         1.open account
                         2.deposit
                         3.withdraw
                         4.transfer
                         5.account statement
                         6.list account
                         7.search account by customer name
                         0.exit
                    """);
            System.out.println(" CHOOSE : ");
            String choice = scanner.nextLine().trim();
            System.out.println("choice : " + choice);

            switch (choice) {
                case "1" -> openAccount(scanner, bankService);
                case "2" -> deposit(scanner, bankService);
                case "3" -> withdraw(scanner, bankService);
                case "4" -> transfer(scanner, bankService);
                case "5" -> statement(scanner,bankService);
                case "6" -> listAccounts(scanner, bankService);
                case "7" -> SearchAccounts(scanner,bankService);
                case "0" -> running = false;

            }
        }
    }

    private static void openAccount(Scanner scanner, BankService bankService) {
        System.out.println("Customer name ");
        String name = scanner.nextLine().trim();
        System.out.println("Customer email : ");
        String email = scanner.nextLine().trim();

        System.out.println("AccountType (SAVINGS/CURRENT : ");
        String type = scanner.nextLine().trim();
        System.out.println("Initial  deposit (optional ,blank for 0 ) : ");
        String amount = scanner.nextLine().trim();
        Double intitial = Double.valueOf(amount);


        String accountNumber = bankService.openAccount(name, email, type);
        if (intitial > 0)
            bankService.deposit(accountNumber, intitial, " Initial deposit");
        System.out.println("account opened " + accountNumber);
    }

    private static void deposit(Scanner scanner, BankService bankService) {

        System.out.println("Account number : ");
        String accountNumber= scanner.nextLine().trim();
        System.out.println(" amount : ");
        try {
            Double amount = Double.valueOf(scanner.nextLine().trim());


            // Double amount = Double.valueOf(scanner.nextLine().trim());
            bankService.deposit(accountNumber, amount, "Deposit");
            System.out.println(" deposited");
        }catch (NumberFormatException e) {
            System.out.println("Please enter valid numeric amount");

    }}

    private static void withdraw(Scanner scanner, BankService bankService) {
        System.out.println("account number");
        String accountNumber = scanner.nextLine().trim();
        System.out.println("amount ");
        Double amount = Double.valueOf(scanner.nextLine().trim());
        bankService.withdraw(accountNumber, amount, "withdraw");
        System.out.println("withdrawn");
    }

    private static void transfer(Scanner scanner, BankService bankService) {
        System.out.println("from account : ");
        String from = scanner.nextLine().trim();
        System.out.println("to account : ");
        String to = scanner.nextLine().trim();
        System.out.println("amount : ");
        Double amount = scanner.nextDouble();
        bankService.transfer(from, to, amount, "transfer");

    }

    private static void statement(Scanner scanner,BankService bankService) {
        System.out.println(" account number ");
        String account= scanner.nextLine().trim();
        bankService.getstatement(account).forEach(t -> {
            System.out.println(
                    t.getAccountNumber() + " | " +
                            t.getTimestamp() + " | " +
                            t.getType() + " | " +
                            t.getAmount() + " | " +
                            t.getNote()
            );
        });
    }


    private static void listAccounts(Scanner scanner, BankService bankService) {
        bankService.Listaccounts().forEach(a -> {
            System.out.println(a.getAccountNumber() + " | " + a.getAccountType() + " | " + a.getBalance());
        });
    }

private static void SearchAccounts(Scanner scanner,BankService bankService){
    System.out.println("Customer name Contains :  ");
    String q=scanner.nextLine().trim();
    bankService.searchAccountByCustomername(q).forEach(account ->
                    System.out.println(account.getAccountNumber()+" | "+account.getAccountType()+" | "+account.getBalance())
            );

}


}
