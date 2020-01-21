package src.lil.models;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import src.lil.common.DBConnection;
import src.lil.controllers.ComplainsInterface;
import src.lil.models.Order.NotFound;

public class Complain implements ComplainsInterface {
	public static int idCount=4;
	private int complain_id;
	private String contact_email;
	private String contact_phone;
	private String complain_title;
	private String complain_text;
	private String store_adress;
	private Date date;
	private String order_Id;
	private String user_id;
	public Complain( String contact_email, String contact_phone, String complain_title, String complain_text, String store_adress, Date date, String order_Id, String user_id){
		this.complain_text = complain_text;
		this.contact_email = contact_email;
		this.contact_phone = contact_phone;
		this.complain_title = complain_title;
		this.store_adress = store_adress;
		this.date = date;
		this.order_Id = order_Id;
		this.user_id = user_id;


	}

	public String getOrder_Id() {
		return order_Id;
	}

	public int getComplain_id() {
		return complain_id;
	}

	public Date getDate() {
		return date;
	}

	public String getUser_id() {
		return user_id;
	}

	public String getContact_email() {
		return contact_email;
	}

	public int getComplainId() {
		return complain_id;
	}

}
