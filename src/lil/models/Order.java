package src.lil.models;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import src.lil.Enums.OrderType;
import src.lil.common.DBConnection;
import src.lil.controllers.OrderServices;

public class Order implements OrderServices {
    private int userId, orderId;
    private String DominantColor, receiver_phone,delLocation,priceDomain,item1;
    private Date orderDate;
    private OrderType orderType;
    private boolean Delivrey, greating;
  
    
    public void fillFieldsFromResultSet(ResultSet rs) throws SQLException {
        
    	this.userId = rs.getInt("user_Id");
        this.orderId = rs.getInt("order_Id");
        this.orderType = OrderType.valueOf(rs.getString("order_Type"));
        this.item1 = rs.getString("item");
        this.DominantColor = rs.getString("Domiant_Color");
        this.receiver_phone = rs.getString("receiver_phone");
        //this.contact = rs.getString("contact_info");
        this.orderDate = rs.getDate("order_Date");
        //this.price = rs.getDouble("item_price");
        this.priceDomain = rs.getString("price_Domain");
        this.Delivrey = rs.getBoolean("delivery");
        this.delLocation = rs.getString("delivery_location");
        this.greating = rs.getBoolean("greating");
        
    }

    public Order(ResultSet rs) throws SQLException {
        super();

        this.fillFieldsFromResultSet(rs);
    }

    
    public Order(int userId,int orderId,OrderType orderType,String item, String DomiantColor,String phoneNum,
    		Date orderdate, String priceDomian , boolean Delivery,String delLocation, boolean greating) {
    	this.userId = userId;
        this.orderId = orderId;
        this.orderType = orderType;
        this.item1 = item;
        this.DominantColor = DomiantColor;
        this.receiver_phone = phoneNum;
        //this.contact = contact;
        this.orderDate = orderdate;
        this.priceDomain = priceDomian;
        this.delLocation = delLocation;
        this.Delivrey = Delivery; 
        this.greating = greating;
    }


    public Order() {
		// TODO Auto-generated constructor stub
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
             PreparedStatement preparedStatement = db.prepareStatement("insert into orders (user_id, order_id, order_Type, item,Domiant_color, receiver_phone,order_Date,price_Domain,delivery,delivery_location,greating ) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)")) {
            preparedStatement.setInt(1, getuserId());
            preparedStatement.setInt(2, getorderId());
            preparedStatement.setString(3,getOrderType().toString());
            preparedStatement.setString(4, getItem1());
            preparedStatement.setString(5, getDomiantColor());
            preparedStatement.setString(6, getReciverPhone());
            preparedStatement.setDate(7, (java.sql.Date) (getOrderDate()));
            preparedStatement.setString(8, getpriceDomain());
            preparedStatement.setBoolean(9, getDelivery());
            preparedStatement.setString(10, getDeliveryLocation());
            preparedStatement.setBoolean(11, getGreating());
            // run the insert command
            preparedStatement.executeUpdate();
            db.close();
            return true;
            // get the auto generated id
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            throw new AlreadyExists();
        }
    }


	public String getItem1() {
		// TODO Auto-generated method stub
		return item1;
	}

	public boolean register() {
    	return false;
    }

    private boolean getGreating() {
		return false;
	}

	public String getDeliveryLocation() {
		
		return delLocation;
	}

	private boolean getDelivery() {
		return false;
	}


	public static class AlreadyExists extends Exception {
    }

    public static class NotFound extends Exception {
    }


    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
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
                throw new AlreadyExists();
            }
    	}
}
    
    
    
    