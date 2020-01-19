package src.lil.models;

import src.lil.common.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public abstract class User {
    protected int userId;
    protected String name;
    protected String phone;
    protected String bankAccount;
    protected boolean isBlocked;
    protected String email;
    protected String password;
    protected String store_id;
    protected String balance;

    public User(){}
    public User(int userId, String name, String phone, String bankAccount,String email, String password, String storeId, String balance){
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.bankAccount = bankAccount;
        this.email = email;
        this.password = password;
        this.isBlocked = false;
        this.store_id = storeId;
        this.balance = balance;

    }

    public abstract boolean register() throws Exception;

    public String getName() {
        return name;
    }

    public int getUserId() {
        return userId;
    }

    public String getPhone() {
        return phone;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public boolean getIsConnected() {
        return isBlocked;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean pay(double amount){
        try{
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT client_balance  FROM clients WHERE client_id=" + userId);
            rs.next();
            String balance = rs.getString(1);
            balance = String.valueOf(Double.parseDouble(balance) - amount);
            String SQL_INSERT = "UPDATE clients" +
                    "SET client_balance = " +balance +
                    "WHERE client_id=" + userId;
            PreparedStatement updateUserQuery = connection.prepareStatement(SQL_INSERT);
            updateUserQuery.executeUpdate();
            connection.close();
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

}
