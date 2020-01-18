package src.lil.models;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

import src.lil.common.DBConnection;
import src.lil.controllers.ComplainsInterface;
import src.lil.models.Order.NotFound;

public class Complain implements ComplainsInterface {
	public static int idCount=4;
	
	public Complain(){
		
	}	
	@Override
	public void addComplain(String complainTitle, String complainText, String email, String phone, String adress) throws SQLException, NotFound, IOException {
		// TODO Auto-generated method stub

		// Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date according to the chosen pattern
		int complain_id;
		String complain_title,complain_text,store_adress,contact_phone,contact_email;
		Date date = new Date(Calendar.getInstance().getTime().getTime());
		complain_id=idCount; idCount++;
		complain_title=complainTitle;
		complain_text= complainText;
		store_adress=adress;
		contact_phone=phone;
		contact_email=email;
	    try (Connection conn = DBConnection.getInstance().getConnection()){	    	
	    	  PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO complains VALUES (?, ?, ?, ?, ?, ?, ?)");
	          preparedStatement.setInt(1, complain_id);
	          preparedStatement.setString(2, contact_email);
	          preparedStatement.setString(3, contact_phone);
	          preparedStatement.setString(4, complain_title);
	          preparedStatement.setString(5, complain_text);
	          preparedStatement.setString(6, store_adress);
	          preparedStatement.setDate(7,date);
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

	@Override
	public void viewComplains() throws NotFound, IOException {
		String contact_email,contact_phone,complain_title,complain_text,store_adress,sql;
		int complain_id;
		Date date;
		// TODO Auto-generated method stub
		try{
			Connection conn = DBConnection.getInstance().getConnection();
	    	Statement stmt = conn.createStatement();
	    	sql = "SELECT * FROM complains";
	    	ResultSet rs = stmt.executeQuery(sql);
	    	while(rs.next()) {
		    	complain_id=rs.getInt("complain_id");
		    	contact_email=rs.getString("contact_email");
		    	contact_phone=rs.getString("contact_phone");
		    	complain_title=rs.getString("complain_title");
		    	complain_text=rs.getString("complain_text");
		    	store_adress=rs.getString("store_adress");
		    	date=rs.getDate("complain_date");
		    	System.out.println("Complain date: "+date.toString()+" Complain ID: " + complain_id + " Contact email: " + contact_email + " Contact phone: " + contact_phone + " Store adress: "+store_adress+"\nTitle: " + complain_title+"\nComplain: "+complain_text); 
	    	}
	    	rs.close();
	    	stmt.close();
	    	conn.close();
		}catch(SQLException se) {
			se.printStackTrace();
		}
		// need to print file content to GUI when its ready
	}

}
