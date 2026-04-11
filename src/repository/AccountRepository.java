package repository;

import app.DBConnection;
import domain.Account;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccountRepository {



    public List<Account> findAll() {

        List<Account> accounts = new ArrayList<>();

        String sql = "SELECT * FROM account";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()
        ) {

            while (rs.next()) {

                Account account = new Account(
                        rs.getString("account_number"),
                        rs.getString("customer_name"),
                        rs.getString("customer_email"),
                        rs.getDouble("balance"),
                        rs.getString("account_type")
                );

                accounts.add(account);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return accounts;
    }

    public void save(Account account) {

        String sql = "INSERT INTO account(account_number, customer_name, customer_email, balance, account_type) VALUES (?, ?, ?, ?, ?)";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, account.getAccountNumber());
            ps.setString(2, account.getName());
            ps.setString(3, account.getCustomerEmail());
            ps.setDouble(4, account.getBalance());
            ps.setString(5, account.getAccountType());

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Account saved successfully in database");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Optional<Account> findByAccountNumber(String accountNumber) {

        String sql = "SELECT * FROM account WHERE account_number = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            accountNumber = accountNumber.trim();

            ps.setString(1, accountNumber);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                Account account = new Account(
                        rs.getString("account_number"),
                        rs.getString("customer_name"),
                        rs.getString("customer_email"),
                        rs.getDouble("balance"),
                        rs.getString("account_type")
                );

                return Optional.of(account);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    public void updateBalance(String accountNumber, double balance) {

        String sql = "UPDATE account SET balance = ? WHERE account_number = ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setDouble(1, balance);
            ps.setString(2, accountNumber);

            int rows = ps.executeUpdate();

            if (rows > 0) {
                System.out.println("Balance updated successfully");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Account> searchByCustomerName(String name) {

        List<Account> accounts = new ArrayList<>();

        String sql = "SELECT * FROM account WHERE customer_name LIKE ?";

        try (
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)
        ) {

            ps.setString(1, "%" + name + "%");

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                Account account = new Account(
                        rs.getString("account_number"),
                        rs.getString("customer_name"),
                        rs.getString("customer_email"),
                        rs.getDouble("balance"),
                        rs.getString("account_type")
                );

                accounts.add(account);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return accounts;
    }
}