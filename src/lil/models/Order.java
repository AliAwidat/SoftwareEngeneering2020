package src.lil.models;
import java.sql.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;
import src.lil.Enums.OrderType;
import src.lil.Enums.SubscriptionType;
import src.lil.common.DBConnection;
import src.lil.controllers.OrderServices;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Order implements OrderServices {
    private int userId, orderId;
    private String DominantColor, receiver_phone,delLocation,priceDomain,storeid,contactName,greatingText,orderCost;
    private OrderType orderType;
    private boolean Delivrey, greating;
    List<Integer> items = new ArrayList<>();
    List<String> Colors;
    String ShippingHour;
    //    LocalTime ShippingHour;
//    LocalDate ShippingDate;
    String ShippingDate;
    //    LocalDateTime orderDate;
    String orderDate;

    //    @Override
//    public String toString(){
//        return "["
//    }
    public void fillFieldsFromResultSet(ResultSet rs) throws SQLException {


//    	this.userId = rs.getInt("user_Id");
        this.orderId = rs.getInt("order_Id");
//        this.orderType = OrderType.valueOf(rs.getString("order_Type"));
//        itemHolder(rs.getString("item"), items);
//        this.DominantColor = rs.getString("Dominant_Color");
//        colorsContainer(rs.getString("colors"), Colors);
        this.contactName = rs.getString("contact_name");
        this.receiver_phone = rs.getString("receiver_phone");
        this.ShippingHour=rs.getString("Shipping_Hour");
        this.ShippingDate=rs.getString("Shipping_Date");
        this.greating = rs.getBoolean("greating");
        this.greatingText = rs.getString("greating_text");
        this.Delivrey = rs.getBoolean("delivery");
        this.delLocation = rs.getString("delivery_location");
//        hourToString(rs.getString("Shipping_Hour"), ShippingHour);
//        DateToString(rs.getString("Shipping_Date"), ShippingDate);
//        this.storeid = rs.getString("store_id");
//       this.orderDate=rs.getString("order_Date");
//        ShippingDateToString(rs.getString("order_Date"),orderDate);
        this.orderCost = rs.getString("order_price");


    }


    public void hourToString(String CurrentHour,LocalTime h) {
        CurrentHour = h.toString();
        return;
    }
    public void DateToString(String CurrentHour,LocalDate d) {
        CurrentHour = d.toString();
        return;
    }
    public void ShippingDateToString(String CurrentHour,LocalDateTime d) {
        CurrentHour = d.toString();
        return;
    }
    public void itemHolder(String holder,List<Integer> items) {
        StringTokenizer str= new StringTokenizer(holder,",") ;
        while(str.hasMoreTokens()) {

            items.add(Integer.parseInt(str.nextToken()));
        }
        return ;
    }
    public void colorsContainer(String holder, List<String> Colors) {
        StringTokenizer str= new StringTokenizer(holder,",") ;
        while(str.hasMoreTokens()) {

            Colors.add(str.nextToken());
        }
        return ;
    }

    //    /**
//     * adding  items to cart
//     *
//     * @param id
//     * @return  set of items
//     * @throws SQLException
//     * @throws NotFound
//     */
    public List<Integer> addToCart(int item_id) {
        this.items.add(item_id);
        return items;
    }

    public Order(ResultSet rs) throws SQLException {
        this.fillFieldsFromResultSet(rs);
    }
    //    public Order(int userId,String contactNam , String phoneNum,LocalTime Shipping_Hour,LocalDate Shipping_Date,boolean greating,String greatingText,boolean Delivery,String delLocation,LocalDateTime orderDate,String storeId )throws SQLException, NotFound, AlreadyExists{
    public Order(int userId,String contactNam , String phoneNum,String Shipping_Hour,String Shipping_Date,boolean greating,String greatingText,boolean Delivery,String delLocation,String orderDate,String storeId )throws SQLException, NotFound, AlreadyExists{
//    public Order(int userId, String phoneNum,LocalDateTime orderDate,  boolean Delivery,String delLocation,LocalTime Shipping_Hour,LocalDate Shipping_Date, boolean greating,String greatingText, String contactNam,String storeId) throws SQLException, NotFound, AlreadyExists {
        this.userId = userId;
        this.orderId = orderId;
        this.receiver_phone = phoneNum;
        this.ShippingDate = Shipping_Date;
        this.ShippingHour = Shipping_Hour;
        this.delLocation = delLocation;
        this.Delivrey = Delivery;
        this.greating = greating;
        this.storeid = storeId;
        this.contactName = contactNam;
        this.greatingText =greatingText;
        this.orderDate = orderDate;
//        this.orderCost = OrderCost();

    }

    public Order() {
    }

    //    /**
//     * find Order of a given Order ID
//     *
//     * @param id
//     * @return matching Order
//     * @throws SQLException
//     * @throws NotFound
//     */
    public Order findOrderById(Integer id) throws SQLException {
        try {Connection db = DBConnection.getInstance().getConnection();
            Statement statment = db.createStatement();
            ResultSet rs = statment.executeQuery("select * from orders where order_id ="+orderId);
            rs.next();
            Order order = new Order();
            order.fillFieldsFromResultSet(rs);
            db.close();
            return order;

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
    public Order findOrderById() throws SQLException {
        try {Connection db = DBConnection.getInstance().getConnection();
            Statement statment = db.createStatement();
            ResultSet rs = statment.executeQuery("select * from orders where order_id ="+orderId);
            rs.next();
            Order order = new Order();
            order.fillFieldsFromResultSet(rs);
            db.close();
            return order;

        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    //    /**
//     * count number of order of a user
//     *
//     * @param id
//     * @return number of matching order
//     * @throws SQLException
//     * @throws NotFound
//     */
    public  int countForUser(Integer id) throws SQLException, NotFound {
        try {Connection db =DBConnection.getInstance().getConnection();
            Statement statment = db.createStatement();
            ResultSet rs = statment.executeQuery("SELECT COUNT(*) AS total FROM orders WHERE user_id ="+userId);
            rs.next();
            db.close();
            return rs.getInt("total");
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }


    public static List<Order> filterOrders(int id) throws SQLException {
        List<Order> orders = new ArrayList<>();
        try {Connection db =DBConnection.getInstance().getConnection();
            Statement statment = db.createStatement();
            ResultSet rs = statment.executeQuery("SELECT * FROM orders WHERE user_id ="+ id);
            List<Order> Orders = new ArrayList<>();
            while (rs.next()) {
                Order order = new Order(rs);
                orders.add(order);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
        return orders;
    }

    /**
     * find Order of a given user
     *
     //     * @param user_id
     * @return List of matching orders
     * @throws SQLException
     * @throws NotFound
     */

    public  List<Order> findAllByUserId(Integer id) throws SQLException, NotFound {
        try {Connection db =DBConnection.getInstance().getConnection();
            Statement statment = db.createStatement();
            ResultSet rs = statment.executeQuery("SELECT * FROM orders WHERE user_id ="+ id);
            List<Order> Orders = new ArrayList<>();
            while (rs.next()) {
                Order order = new Order();
                order.fillFieldsFromResultSet(rs);
                Orders.add(order);

            }
            return Orders;
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
            return null;

        }
    }
    /**
     * add order (with all related details) to the data base
     *
     * @throws SQLException
     * @throws NotFound
     * @throws AlreadyExists
     */

    public boolean insertIntoOrders() throws Exception {
        if(items.isEmpty()){
            throw new Exception("cart is empty");
        }
        if(Optional.ofNullable(userId).orElse(0) == 0){
            throw new Exception("user is Undefined");
        }
        try (Connection db = DBConnection.getInstance().getConnection()){
            PreparedStatement preparedStatement = db.prepareStatement("insert into orders(user_id,contact_name,receiver_phone,Shipping_Hour,Shipping_Date,greating,greating_text,delivery,delivery_location,order_Date) value (?,?,?,?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1,String.valueOf(getuserId()));
            preparedStatement.setString(2, getContactName());
            preparedStatement.setString(3, getReceiver_phone());
            preparedStatement.setString(4,getTime());
            preparedStatement.setString(5, getShippingDate());
            preparedStatement.setBoolean(6, getGreating());
            preparedStatement.setString(7, getGreatingText());
            preparedStatement.setBoolean(8, getDelivery());
            preparedStatement.setString(9, getDeliveryLocation());
            preparedStatement.setString(10, DateTimeFormatter.ofPattern("dd-MM-yyyy").format(LocalDate.now()));
            try{
                preparedStatement.executeUpdate();
                db.close();
                preparedStatement.close();
            }catch(SQLException se) {
                se.printStackTrace();
            }
            return true;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }


    /**
     * add order (with all related details) to the data base
     *
     * @throws SQLException
     * @throws NotFound
     * @throws AlreadyExists
     */



    private String getOrderCost() {
        return orderCost;
    }
    public String getShippingHour() {

        return ShippingHour;
    }

    public static class AlreadyExists extends Exception {
    }

    public static class NotFound extends Exception {
    }

    public String getTime() {

        return ShippingHour;

    }
    public String getStoreId() {

        return storeid;
    }

    public String getColors() {
        String res = "";
        for (String color : Colors) {
            res += (color) + ",";
        }
        res = res.substring(0, res.length()-1);
        return res;
    }

    public String getItems() {
        String res = "";
        for (Integer integer : items) {
            res += Integer.toString(integer) + ",";
        }
        res = res.substring(0, res.length()-1);
        return res;
    }

    private boolean getGreating() {
        return greating;
    }

    public String getDeliveryLocation() {

        return delLocation;
    }

    private boolean getDelivery() {
        return Delivrey;
    }

    public String getShippingDate() {
        return ShippingDate.toString();
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setId(int orderId) {
        this.orderId = orderId;
    }

    public int getuserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public void setpriceDomain(String priceDomain) {
        this.priceDomain = priceDomain;
    }

    public String getpriceDomain() {
        return priceDomain;
    }
    public String getDomiantColor() {
        return DominantColor;
    }
    public String getReceiver_phone() {
        return receiver_phone;
    }

    /**
     * delete an order by order id
     *
     * @param id
     * @throws SQLException
     * @throws NotFound
     * @throws AlreadyExists
     */

    public  boolean DeleteOrder() throws SQLException, NotFound, AlreadyExists {
        try {
            Connection db =DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = db.prepareStatement("DELETE FROM orders WHERE order_id = ?");
            System.out.println(getOrderId());
            preparedStatement.setInt(1, getOrderId());
            preparedStatement.executeUpdate();
            db.close();
            return true;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }
    /**
     * return the Order price
     *
     //     * @param order_id
     * @throws SQLException
     * @throws NotFound
     * @throws AlreadyExists
     */
    public  String OrderCost(List<Item>items ) throws SQLException, NotFound, AlreadyExists {
        double OrderPrice= 0.0;
        try {
            Connection db = DBConnection.getInstance().getConnection();
            for (Item itrr : items) {
                double price = itrr.getPrice();
                OrderPrice+= price;
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection db = DBConnection.getInstance().getConnection();
            PreparedStatement statement =  db.prepareStatement("select client_subscriptionType from clients where client_id = " + userId);
            ResultSet result = statement.executeQuery();
            result.next();
            db.close();
            SubscriptionType sub = SubscriptionType.valueOf(result.getString(1));
            double discount =1;
            if(sub == SubscriptionType.Monthly)
                discount = 0.75;
            else if (sub == SubscriptionType.Yearly)
                discount = 0.5;
            Double finalSum = (OrderPrice) *discount;
            return finalSum.toString();
        }

        catch (Exception e) {
            System.out.println(e.getMessage());

        }
        return "";

    }
    public String getContactName() {
        return contactName;
    }
    public String getGreatingText() {
        return greatingText;
    }

    /**
     * return the hour time difference
     *
     //     * @param order_id
     * @throws SQLException
     * @throws NotFound
     */
    public double orderTimeDiff() {

        String time = "";
        try {
            LocalDate currDate = LocalDate.now();
            LocalTime currentHour = LocalTime.now();
            String str = getShippingHour();
            String str2 = getShippingDate();
            DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("HH:mm:ss");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(str2, formatter);
            LocalTime hour = LocalTime.parse(str, formatter1);
            int s, m, h;
            if (date.getDayOfMonth() > currDate.getDayOfMonth()) {
                return Double.parseDouble(orderCost);
            } else {
                if (hour.getSecond() > currentHour.getSecond()) {
                    currentHour.minusMinutes(1);
                    currentHour.plusSeconds(60);

                }
                s = currentHour.getSecond() - hour.getSecond();
                if (hour.getMinute() > currentHour.getMinute()) {
                    currentHour.minusHours(1);
                    currentHour.plusMinutes(60);
                }
                m = currentHour.getMinute() - hour.getMinute();

                h = currentHour.getHour() - hour.getHour();
                time = "(" + String.valueOf(h) + ":" + String.valueOf(m) + ":" + String.valueOf(s) + ")";
                if ((h > 3) || ((h == 3) & (m >= 0 & s > 0))) {
                    //    		System.out.println("this order was purchased before: "+ time + ", you can cancel this order and get a full refund");
                    return Double.parseDouble(orderCost);
                } else if (h == 0) System.out.println(" sorry, too late to cancel this order");
                else {
                    //    		System.out.println("this order was purchased before: "+ time + ", you can cancel this order and get a half price refund");
                    return Double.parseDouble(orderCost) * 0.5;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0.0;
        }
        return 0.0;
    }

    public void setShippingHour(String shippingHour) {
        ShippingHour = shippingHour;
    }

    public void setOrderCost(String orderCost) {
        this.orderCost = orderCost;
    }

    public void setShippingDate(String shippingDate) {
        ShippingDate = shippingDate;
    }
    public void setOrderId(int orderid) {
        orderId = orderid;
    }
}