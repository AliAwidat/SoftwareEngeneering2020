package src.lil.models;
import src.lil.Enums.*;
import src.lil.common.*;
import java.sql.*;

public class Item {
	private int id,updated=0;
	private String dominantColor;
	private ItemType type;
	private Double price;
	
	
	public Item(ResultSet rs) throws SQLException{
		super();
		this.fillFieldsFromResultSet(rs);
	}
	
	public void fillFieldsFromResultSet(ResultSet rs) throws SQLException {
        this.id = rs.getInt("item_Id");
        this.dominantColor = rs.getString("dominant_color");
        this.type = ItemType.valueOf(rs.getString("item_type"));
        this.price = rs.getDouble("item_price");
        this.updated = rs.getInt("updated");
    }
    public static Item findById(Integer id) throws SQLException {
        try (Connection db = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("select * from items where id = ?")) {
            preparedStatement.setInt(1, id);

            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (!rs.next()) {
                 //   throw new NotFound();
                }
                Item item = new Item(rs);
                return item;
            }
        }
    }
    public static void delete(Integer id) throws SQLException {
        try (Connection db = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("drop * from items where id = ?")) {
            preparedStatement.setInt(1, id);
        }
    }
    
    public void insert() throws SQLException{
        try (Connection db = DBConnection.getInstance().getConnection();
                PreparedStatement preparedStatement = db.prepareStatement("insert into items (id,type,dominant_color,price,updated) values (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(2, this.type.toString());
        	preparedStatement.setString(3, this.dominantColor);
        	preparedStatement.setDouble(4, this.price);
        	preparedStatement.setInt(5, this.updated);
        	
        }
    }
        
    public void update(Item item) throws SQLException{
        try (Connection db = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = db.prepareStatement("insert into items (id,type,dominant_color,price,updated) values (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS)) {
        	preparedStatement.setInt(1, item.id);
        	preparedStatement.setString(2, item.type.toString());
        	preparedStatement.setString(3, item.dominantColor);
        	preparedStatement.setDouble(4, item.price);
        	preparedStatement.setInt(5, 1);
        }
    }
    
        
    public int getId() {
    	return id;
    }

    public void setId(int id) {
    	this.id = id;
    }

    public int getUpdated() {
   		return updated;
   	}

   	public void setUpdated(int updated) {
  		this.updated = updated;
   	}

   	public String getDominantColor() {
   		return dominantColor;
    }

   	public void setDominantColor(String dominantColor) {
    	this.dominantColor = dominantColor;
    }

    public ItemType getType() {
    	return type;
    }

    public void setType(ItemType type) {
    	this.type = type;
    }

    public Double getPrice() {
    	return price;
    }

    public void setPrice(Double price) {
    	this.price = price;
    }
    
}
