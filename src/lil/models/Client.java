package src.lil.models;

import src.lil.Enums.SubscriptionType;
import src.lil.common.DBConnection;

import java.io.IOException;
import java.sql.*;
import java.util.Calendar;

public class Client extends User {
    protected String shippingAddress;
    protected SubscriptionType subscriptionType;
    protected String creditCardNumber;


    @Override
    public boolean register() throws Exception {
        try{
            Connection connection = DBConnection.getInstance().getConnection();
            //check if exist
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT *  FROM clients WHERE client_id=" + userId);
            if(rs.getRow() != 0 ) {
                System.out.println("Client already exist");
                return false;
            }
            String SQL_INSERT = "INSERT INTO clients (client_id, client_name, client_phone, client_bankAccount, client_email," +
                    " client_password, client_creditCard, client_shippingAddress, client_subscriptionType, client_block,store_id," +
                    "client_balance)" +
                    " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement updateUserQuery = connection.prepareStatement(SQL_INSERT);
            updateUserQuery.setInt(1,userId);
            updateUserQuery.setString(2,name);
            updateUserQuery.setString(3,phone);
            updateUserQuery.setString(4,bankAccount);
            updateUserQuery.setString(5,email);
            updateUserQuery.setString(6,password);
            updateUserQuery.setString(7,creditCardNumber);
            updateUserQuery.setString(8,shippingAddress);
            updateUserQuery.setString(9,subscriptionType.toString());
            updateUserQuery.setBoolean(10, isBlocked);
            updateUserQuery.setString(11,storeId);
            updateUserQuery.setString(12,balance);
            updateUserQuery.executeUpdate();
            connection.close();
            return true;
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }

    public Client(){super();}

    public Client(int userId, String name, String phone, String bankAccount, String shippingAddress, String email, String password,  SubscriptionType subscriptionType, String creditCardNumber, String store_id, String balance) {
        super(userId,name,phone,bankAccount,email,password,store_id,balance);
        this.creditCardNumber = creditCardNumber;
        this.subscriptionType = subscriptionType;
        if(this.subscriptionType == SubscriptionType.Monthly){
            this.balance = "50";
        }
        else if(this.subscriptionType == SubscriptionType.Yearly){
            this.balance = "300";
        }
        this.shippingAddress = shippingAddress;
    }

    public Client(ResultSet rs) throws SQLException {
        this(rs.getInt("client_id"), rs.getString("client_name"), rs.getString("client_phone"), rs.getString("client_bankAccount"), rs.getString("client_shippingAddress"), rs.getString("client_email"), rs.getString("client_password"), SubscriptionType.valueOf(rs.getString("client_subscriptionType")), rs.getString("client_creditCard"), rs.getString("store_id"), rs.getString("client_balance"));
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public SubscriptionType getSubscriptionType() {
        return subscriptionType;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public void setSubscriptionType(SubscriptionType subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
    public boolean equals (Object obj) {
        if (this==obj) return true;
        Client emp = (Client) obj ;
        return this.name.equals(emp.getName()) && this.subscriptionType.equals(emp.getSubscriptionType())
                && this.bankAccount.equals(emp.getBankAccount())&& this.email.equals(emp.getEmail())
                && this.password.equals(emp.getPassword())&& this.phone.equals(emp.getPhone())
                && this.userId == (emp.getUserId()) && this.isBlocked == (emp.getIsConnected())
                && this.creditCardNumber.equals(emp.getCreditCardNumber()) && this.shippingAddress.equals(emp.getShippingAddress());
    }

    public void addComplain(String complainTitle, String complainText, String email, String phone, String adress) throws SQLException, Order.NotFound, IOException {
        int complain_id;
        String complain_title,complain_text,store_adress,contact_phone,contact_email;
        Date date = new Date(Calendar.getInstance().getTime().getTime());
        complain_title=complainTitle;
        complain_text= complainText;
        store_adress=adress;
        contact_phone=phone;
        contact_email=email;
        try (Connection conn = DBConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO complains VALUES (?, ?, ?, ?, ?, ?, ?)");
            preparedStatement.setString(1, contact_email);
            preparedStatement.setString(2, contact_phone);
            preparedStatement.setString(3, complain_title);
            preparedStatement.setString(4, complain_text);
            preparedStatement.setString(5, store_adress);
            preparedStatement.setDate(6,date);
            preparedStatement.setBoolean(7,false);
            try {
                preparedStatement.executeUpdate();
                System.out.println("Added new complain");
                preparedStatement.close();
                conn.close();
            }catch(SQLException se) {
                se.printStackTrace();
            }
        }catch(SQLException se) {
            se.printStackTrace();
        }
    }

}

