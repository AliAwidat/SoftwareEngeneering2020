package src.lil.models;
import src.lil.Enums.*;
import src.lil.common.*;
import src.lil.models.Order.AlreadyExists;
import java.lang.String;
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
        //this.price = rs.getDouble("item_price");
        this.updated = rs.getInt("updated");
        this.image = rs.getString("image");

    }
    public static Item findById(Integer id) throws SQLException, AlreadyExists {
        try (Connection db = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("select * from items where item_Id=?")){
            preparedStatement.setInt(1, id);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                if (!rs.next()) {
                 //   throw new NotFound();
                }
                Item item = new Item(rs);
                return item;
            }catch (Exception e) {
            	System.out.println(e.getMessage());
                throw new AlreadyExists();
            }
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            throw new AlreadyExists();
        }
    }
    //change price of one item in all stores
    public static boolean changePriceAllStores(Integer itemId,Double newPrice) throws AlreadyExists {
        try (Connection db = DBConnection.getInstance().getConnection();
        	PreparedStatement preparedStatement2 = db.prepareStatement("update prices set price =price*"+newPrice+" where item_Id =?")){
            preparedStatement2.setInt(1, itemId);
            preparedStatement2.executeUpdate();
            db.close();
            return true;
        } catch (Exception e) {
           	System.out.println(e.getMessage());
            throw new AlreadyExists();
        }
    }
//    //discount all items
//    public static boolean changePriceAllItems(Integer itemId,Double newPrice) throws AlreadyExists {
//        try (Connection db = DBConnection.getInstance().getConnection();
//        	PreparedStatement preparedStatement2 = db.prepareStatement("update prices set price =price*?")){
//            preparedStatement2.setDouble(1, newPrice);
//            preparedStatement2.setInt(2, itemId);
//            preparedStatement2.executeUpdate();
//            db.close();
//            return true;
//        } catch (Exception e) {
//           	System.out.println(e.getMessage());
//            throw new AlreadyExists();
//        }
//    }
    //discount all items in one store
    public static boolean changePricesInStore(Integer storeId,Double newPrice) throws AlreadyExists { // item id's "1,5,3,100"
        try (Connection db = DBConnection.getInstance().getConnection();
        	PreparedStatement preparedStatement2 = db.prepareStatement("update prices set price = price*? where store_id =?")){
            preparedStatement2.setDouble(1, newPrice);
            preparedStatement2.setInt(2, storeId);
            preparedStatement2.executeUpdate();
            db.close();
            return true;
        } catch (Exception e) {
           	System.out.println(e.getMessage());
            throw new AlreadyExists();
        }
    }
    
    public static boolean delete(Integer id) throws SQLException, AlreadyExists {
        try (Connection db = DBConnection.getInstance().getConnection();
             PreparedStatement preparedStatement = db.prepareStatement("delete from items where item_Id = ?")) {
            preparedStatement.setInt(1, id);
        	preparedStatement.executeUpdate();
        	db.close();
        } catch (Exception e) {
        	System.out.println(e.getMessage());
            throw new AlreadyExists();
        }
        try (Connection db2 = DBConnection.getInstance().getConnection();
        		PreparedStatement preparedStatement2 = db2.prepareStatement("delete from prices where item_Id = ?")) {
               preparedStatement2.setInt(1, id);
           	preparedStatement2.executeUpdate();
           	db2.close();
           	return true;
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	throw new AlreadyExists();
        }
        
    }
    
    public boolean insert() throws SQLException, AlreadyExists{
    	int itemId=0;
    	String list="";
        try (Connection db = DBConnection.getInstance().getConnection();
                PreparedStatement preparedStatement = db.prepareStatement("insert into items (item_type,dominant_color,item_price,image,updated) values (?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, this.type.toString());
        	preparedStatement.setString(2, this.dominantColor);
        	preparedStatement.setDouble(3, this.price);
        	preparedStatement.setString(4, this.image);
        	preparedStatement.setInt(5, this.updated);
        	preparedStatement.executeUpdate();
        	db.close();
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	System.out.println("insert ");
        	return false;

           // throw new AlreadyExists();
        }
        try (Connection db1 = DBConnection.getInstance().getConnection();
        ResultSet rs1= db1.prepareStatement("select max(item_Id) from items").executeQuery()){
            if (rs1.next()) {
            	itemId = rs1.getInt(1);
            }
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	System.out.println("max ");
        	return false;

           // throw new AlreadyExists();
        }
        try (Connection db3 = DBConnection.getInstance().getConnection();
        ResultSet rs= db3.prepareStatement("select distinct store_id from prices").executeQuery()){
	        while(rs.next()) {
	        	int fuken=this.price.intValue();
	        	list += "("+rs.getInt(1)+","+fuken+","+itemId+"),";
	        }
        } catch (Exception e) {
        	System.out.println(e.getMessage());
        	System.out.println("distinct ");
        	return false;

        //	throw new AlreadyExists();
        }
	        list = list.substring(0, list.length()-1) + ";";
	        System.out.println(list);
	        try (Connection db2 = DBConnection.getInstance().getConnection();
	        		PreparedStatement preparedStatement2 = db2.prepareStatement("INSERT INTO prices(store_id, price, item_id) values ?")) {
	               preparedStatement2.setString(1, list);
	           	preparedStatement2.executeUpdate();
	           	db2.close();
	        } catch (Exception e) {
	        	System.out.println(e.getMessage());
	        	System.out.println("insert price ");
	        	//throw new AlreadyExists();
	        	return false;
	        }
	        return true;
    } 
    public static boolean update(Item item) throws SQLException, AlreadyExists{
        try (Connection db = DBConnection.getInstance().getConnection();

            PreparedStatement preparedStatement = db.prepareStatement("UPDATE items SET item_type=? ,dominant_color=? , image=? ,updated=? WHERE item_Id=?")) {
        	preparedStatement.setString(1, item.type.toString());
        	preparedStatement.setString(2, item.dominantColor);
        	//preparedStatement.setDouble(3, item.price);

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
