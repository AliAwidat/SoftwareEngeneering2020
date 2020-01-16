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
    private String DominantColor, receiver_phone,delLocation;
    private String orderDate;
    private OrderType orderType;
    private double price,priceDomain;
    private boolean Delivrey, greating;
    
    public void fillFieldsFromResultSet(ResultSet rs) throws SQLException {
        
    	this.userId = rs.getInt("user_Id");
        this.orderId = rs.getInt("order_Id");
        //this.orderType = rs.getOrderType("order_type");
        this.DominantColor = rs.getString("Domiant_Color");
        this.receiver_phone = rs.getString("receiver_phone");
        //this.contact = rs.getString("contact_info");
        this.orderDate = rs.getString("order_Date");
        this.price = rs.getDouble("item_price");
        this.priceDomain = rs.getDouble("price_Domain");
        this.Delivrey = rs.getBoolean("Delivrey");
        this.delLocation = rs.getString("delivrey_location");
        this.greating = rs.getBoolean("greating");
        
    }

    public Order(ResultSet rs) throws SQLException {
        super();

        this.fillFieldsFromResultSet(rs);
    }

    
    public Order(int userId,int orderId,OrderType orderType, String DomiantColor,String phoneNum,String contact,
    		      String delLocation,String orderdate, double price, double priceDomian, boolean Delivery, boolean greating) {
    	this.userId = userId;
        this.orderId = orderId;
        this.orderType = orderType;
        this.DominantColor = DomiantColor;
        this.receiver_phone = phoneNum;
        //this.contact = contact;
        this.orderDate = orderdate;
        this.price = price;
        this.priceDomain = priceDomian;
        this.delLocation = delLocation;
        this.Delivrey = Delivery; 
        this.greating = greating;
    }


    public static Order findById() throws SQLException, NotFound {
        return findById();
    }

    /**
     * find Order of a given Order ID
     *
     * @param id
     * @return matching Order
     * @throws SQLException
     * @throws NotFound
     */
    public Order findById(Integer id) throws SQLException, NotFound {
        try (Connection db = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("select * from Orders where orderId = ?")) {
            preparedStatement.setInt(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (!rs.next()) {
                    throw new NotFound();
                }

                Order order = new Order(rs);
                return order;
            }
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
        try (Connection db =DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("select count(*) as total from order where user_id = ?")) {
            preparedStatement.setInt(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (!rs.next()) {
                    return 0;
                }
                return rs.getInt("total");
            }
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
        try (Connection db = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = db.prepareStatement(
                "select * From Orders WHERE user_id = ?")) {
            preparedStatement.setInt(1, user_id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                List<Order> Orders = new ArrayList<>();
                while (rs.next()) {
                    Order order = new Order(rs);
                   // order._extraInfo.put("cityTitle", rs.getString("city_title"));
                    Orders.add(order);
                }
                db.close();
                return Orders;

            }
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
    public void insertIntoOrders() throws SQLException, NotFound, AlreadyExists {
        // insert city to table
        try(Connection db = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("insert into Orders (user_id, order_id, order_Type,item,Domiant_color,reciver_phone,order_Date,"
             		+ ",item_price,price_Domian,delivery,delivery_location,greating) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,)", Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, getuserId());
            preparedStatement.setInt(2, getorderId());
            //preparedStatement.setString(3,);
            //preparedStatement.setString(4, getItem());
            preparedStatement.setString(5, getDomiantColor());
            preparedStatement.setString(6, getReciverPhone());
            preparedStatement.setDate(7, java.sql.Date.valueOf(getOrderDate()));
            preparedStatement.setDouble(8, getPrice());
            preparedStatement.setDouble(9, getpriceDomain());
            preparedStatement.setBoolean(10, getDelivery());
            preparedStatement.setString(11, getDeliveryLocation());
            preparedStatement.setBoolean(12, getGreating());
            // run the insert command
            preparedStatement.executeUpdate();
            db.close();
            // get the auto generated id
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
            throw new AlreadyExists();
        }
    }

    public boolean register() {
    	return false;
    }

    private boolean getGreating() {
		return false;
	}

	private String getDeliveryLocation() {
		
		return delLocation;
	}

	private boolean getDelivery() {
		return false;
	}


	public static class AlreadyExists extends Exception {
    }

    public static class NotFound extends Exception {
    }


    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
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

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
    public void setpriceDomain(double priceDomain) {
        this.priceDomain = priceDomain;
    }

    public double getpriceDomain() {
        return priceDomain;
    }
    public String getDomiantColor() {
        return DominantColor;
    }
    public String getReciverPhone() {
        return receiver_phone;
    }



}