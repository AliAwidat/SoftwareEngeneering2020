package src.lil.models;
import src.lil.Enums.*;
import src.lil.common.*;
import src.lil.models.Order.AlreadyExists;

import java.sql.*;

public class Item {
	private int id,updated=0;
	private String dominantColor="",image;
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
        this.image = rs.getString("image");
    }
    public static Item findById(Integer id) throws SQLException {
        try (Connection db = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("select * from items where item_Id=?")) {
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
    public static boolean delete(Integer id) throws SQLException, AlreadyExists {
        try (Connection db = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("delete from items where item_Id = ?")) {
            preparedStatement.setInt(1, id);
        	preparedStatement.executeUpdate();
        	db.close();
        	return true;
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            throw new AlreadyExists();
        }
    }
    
    public boolean insert() throws SQLException, AlreadyExists{
        try (Connection db = DBConnection.getInstance().getConnection();
                PreparedStatement preparedStatement = db.prepareStatement("insert into items (item_type,dominant_color,item_price,image,updated) values (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, this.type.toString());
        	preparedStatement.setString(2, this.dominantColor);
        	preparedStatement.setDouble(3, this.price);
        	preparedStatement.setString(4, this.image);
        	preparedStatement.setInt(5, this.updated);
        	preparedStatement.executeUpdate();
        	db.close();
        	return true;
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            throw new AlreadyExists();
        }
    }
        
    public static boolean update(Item item) throws SQLException, AlreadyExists{
        try (Connection db = DBConnection.getInstance().getConnection();
            PreparedStatement preparedStatement = db.prepareStatement("UPDATE items SET item_type=? ,dominant_color=? ,item_price=?, image=? ,updated=? WHERE item_Id=?")) {
        	preparedStatement.setString(1, item.type.toString());
        	preparedStatement.setString(2, item.dominantColor);
        	preparedStatement.setDouble(3, item.price);
        	preparedStatement.setString(4, item.image);
        	preparedStatement.setInt(5, 1);
        	preparedStatement.setInt(6, item.id);
        	preparedStatement.executeUpdate();
        	db.close();
        	return true;
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            throw new AlreadyExists();
        }
    }
    
    public Item (ItemType itemType,String dominantColor, Double itemPrice, String image) {
    	//this.id = itemId;
    	this.type = itemType;
	    this.dominantColor = dominantColor;
	    this.price = itemPrice;
	    this.updated = 0;
	    this.image = image;
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
   	
   	public String getImage() {
   		return image;
    }

   	public void setImage(String image) {
    	this.image = image;
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
