package src.lil.models;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
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
    List<Integer> items;
    private LocalTime ShippingHour;
    LocalDate ShippingDate;
    LocalDateTime orderDate; 
    
    public void fillFieldsFromResultSet(ResultSet rs) throws SQLException {
        
    	this.userId = rs.getInt("user_Id");
        this.orderId = rs.getInt("order_Id");
        this.orderType = OrderType.valueOf(rs.getString("order_Type"));
        itemHolder(rs.getString("item"), items);
        this.DominantColor = rs.getString("Domiant_Color");
        this.receiver_phone = rs.getString("receiver_phone");
        this.priceDomain = rs.getString("price_Domain");
        this.Delivrey = rs.getBoolean("delivery");
        this.delLocation = rs.getString("delivery_location");
        this.greating = rs.getBoolean("greating");
        this.storeid = rs.getString("store_id");
        this.contactName = rs.getString("contact_name");
        hourToString(rs.getString("Shipping_Hour"), ShippingHour);
        DateToString(rs.getString("Shipping_Date"), ShippingDate);
        ShippingDateToString(rs.getString("order_Date"),orderDate);
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
    /**
     * adding  items to cart
     *
     * @param id
     * @return  set of items
     * @throws SQLException
     * @throws NotFound
     */
    public List<Integer> addToCart(int item_id) {
    	this.items.add(item_id);
    	return items;
    }
    
    
    public Order(ResultSet rs) throws SQLException {
        this.fillFieldsFromResultSet(rs);
    }

    
    public Order(int userId,int orderId,OrderType orderType,List<Integer> item, String DomiantColor,String phoneNum,
    		 LocalDateTime orderDate, String priceDomian , boolean Delivery,String delLocation,LocalTime Shipping_Hour,LocalDate Shipping_Date, boolean greating,String greatingText, String contactNam,String storeId) throws SQLException, NotFound, AlreadyExists {
    	this.userId = userId;
        this.orderId = orderId;
        this.orderType = orderType;
        this.items = item;
        this.DominantColor = DomiantColor;
        this.receiver_phone = phoneNum;
        this.ShippingDate = Shipping_Date;
        this.ShippingHour = Shipping_Hour;
        this.priceDomain = priceDomian;
        this.delLocation = delLocation;
        this.Delivrey = Delivery; 
        this.greating = greating;
        this.storeid = storeId;
        this.contactName = contactNam;
        this.greatingText =greatingText;
        this.orderDate = orderDate;
        this.orderCost = OrderCost();
        
    }


    public Order() {
	}



    /**
     * find Order of a given Order ID
     *
     * @param id
     * @return matching Order
     * @throws SQLException
     * @throws NotFound
     */
    public Order findOrderById(Integer id) throws SQLException, NotFound {
        try {Connection db = DBConnection.getInstance().getConnection();
        		Statement statment = db.createStatement();
        		ResultSet rs = statment.executeQuery("select * from orders where order_id ="+orderId);
        		rs.next();
                Order order = new Order();
                order.fillFieldsFromResultSet(rs);
                return order;
            }
    	catch(Exception e) {
       	System.out.println(e.getMessage());
    	return null;
        }
    }

    /**
     * count number of order of a user
     *
     * @param id
     * @return number of matching order
     * @throws SQLException
     * @throws NotFound
     */
    public  int countForUser(Integer id) throws SQLException, NotFound {
        try {Connection db =DBConnection.getInstance().getConnection();
         		Statement statment = db.createStatement();
        		ResultSet rs = statment.executeQuery("SELECT COUNT(*) AS total FROM orders WHERE user_id ="+userId);
        		rs.next();
                return rs.getInt("total");
            }
       	catch(Exception e) {
       		System.out.println(e.getMessage());
        	return 0;
            }
        }
    

    /**
     * find Order of a given user
     *
     * @param user_id
     * @return List of matching orders
     * @throws SQLException
     * @throws NotFound
     */
    @Override
    public List<Order> findAllByUserId(Integer user_id) throws SQLException, NotFound {
        try {Connection db =DBConnection.getInstance().getConnection();
 		Statement statment = db.createStatement();
		ResultSet rs = statment.executeQuery("SELECT * FROM orders WHERE user_id ="+userId);
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
	@Override
    public boolean insertIntoOrders() throws SQLException, NotFound, AlreadyExists {
        // insert city to table
        try(
        	Connection db = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("insert into orders (user_id, order_id, order_Type, item,Domiant_color, receiver_phone,order_Date,price_Domain,delivery,delivery_location,Shipping_Hour,Shipping_Date,greating,greating_text,contact_name,store_id,order_price ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? ,?)"); {

            preparedStatement.setInt(1, getuserId());
            preparedStatement.setInt(2, getorderId());
            preparedStatement.setString(3,getOrderType().toString());
            preparedStatement.setString(4, getItems());
            preparedStatement.setString(5, getDomiantColor());
            preparedStatement.setString(6, getReciverPhone());
            preparedStatement.setString(7, getOrderDate());
            preparedStatement.setString(8, getpriceDomain());
            preparedStatement.setBoolean(9, getDelivery());
            preparedStatement.setString(10, getDeliveryLocation());
            preparedStatement.setString(11,getTime());
            preparedStatement.setString(12, getShippingDate());
            preparedStatement.setBoolean(13, getGreating());
            preparedStatement.setString(14, getGreatingText());
            preparedStatement.setString(15, getContactName());
            preparedStatement.setString(16, getStoreId());
            preparedStatement.setString(17, orderCost);
            System.out.println(OrderCost());
            // run the insert command
            preparedStatement.executeUpdate();
            db.close();
            return true;
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	return false;
        }
    }
	
    private String getOrderCost() {
		return orderCost;
	}
	private String getOrderDate() {
		
		return orderDate.toString();
	}

	public static class AlreadyExists extends Exception {
    }

    public static class NotFound extends Exception {
    }
    
	public String getTime() {
		
		return ShippingHour.toString();
		
		}
	public String getStoreId() {
		
		return storeid;
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

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public int getorderId() {
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
    public String getReciverPhone() {
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
    public  boolean DeleteOrder(Integer order_id) throws SQLException, NotFound, AlreadyExists {
        try (
        		Connection db =DBConnection.getInstance().getConnection();
        		PreparedStatement preparedStatement = db.prepareStatement("DELETE FROM orders WHERE order_id = ?")){
        		preparedStatement.setInt(1, getorderId());
                preparedStatement.executeUpdate();
                db.close();
                return true;
            } catch (Exception e) {
            	System.out.println(e.getMessage());
            	return false;
            }
    	}
    /**
     * return the Order price
     *
     * @param order_id
     * @throws SQLException
     * @throws NotFound
     * @throws AlreadyExists 
     */
    public  String OrderCost() throws SQLException, NotFound, AlreadyExists {
    	try {
	    	Connection db = DBConnection.getInstance().getConnection();
	    	String in_string = "(";
	    	for (Integer _integer : this.items) {
	    		in_string+= _integer.toString() +",";
	    	}
	    	in_string = in_string.substring(0, in_string.length()-1);
	    	in_string+=")";
	    	
	    	 PreparedStatement statement =  db.prepareStatement("select sum(price) from prices where store_id = ? and item_id IN" + in_string);
	         statement.setInt(1, Integer.parseInt(this.getStoreId()));
	    	 ResultSet result = statement.executeQuery();
	         result.next();
	         String sum = result.getString(1);
	         statement =  db.prepareStatement("select client_subscriptionType from clients where client_id = " + userId);
	    	 result = statement.executeQuery();
	         result.next();
	         SubscriptionType sub = SubscriptionType.valueOf(result.getString(1));
	         double discount =1;
	         if(sub == SubscriptionType.Monthly)
	        	 discount = 0.75;
	         else if (sub == SubscriptionType.Yearly)
	        	 discount = 0.5;
	         Double finalSum = Double.parseDouble(sum) *discount;

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
     * @param order_id
     * @throws SQLException
     * @throws NotFound
     *  
     */
    public String orderTimeDiff(Order order)  {
    	try {
    	LocalTime currentHour = LocalTime.now();
    	String time ;
    	int s,m,h;
    	if(order.ShippingHour.getSecond() > currentHour.getSecond()){
           currentHour.minusMinutes(1);
            currentHour.plusSeconds(60);
            
        }
        s = currentHour.getSecond() - order.ShippingHour.getSecond();
        if(order.ShippingHour.getMinute() > currentHour.getMinute()){
        	currentHour.minusHours(1);
        	currentHour.plusMinutes(60);
        }
        m = currentHour.getMinute() - order.ShippingHour.getMinute();
        h = currentHour.getHour() - order.ShippingHour.getHour();
    	time = "(" + String.valueOf(h) + ":" + String.valueOf(m) + ":" + String.valueOf(s) + ")";
    	return (h > 3)||((h == 3) & (m >= 0 & s > 0))  ? " sorry, too late to cancel this order" : "this order was purchased before: "+ time; 
    	}
    	catch(Exception e){
    		System.out.println(e.getMessage());
    	 	return "";
    	}
    	
      
    }
}
   