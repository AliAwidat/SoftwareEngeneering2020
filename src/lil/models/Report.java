package src.lil.models;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import src.lil.common.DBConnection;
import src.lil.controllers.ReportInterface;
import src.lil.models.Order.NotFound;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Report implements ReportInterface {
	public Integer getComplains_count;
	private File monthlyReport,quarterReport,complainsReport;
	private Date lastUpdated = null;
	private String report_name,type;
	public CheckBox to_compare;
	public Button view_report;
	private Double monthly_income;
	private int  complains_count;
	public Report() {

	}

	public Report(ResultSet rs) throws SQLException{
		//super();
		this.fillFieldsFromResultSet(rs);
	}

	public static List<Report> getReports() {
		//filters
			List<Report> reports=new ArrayList<Report>();
			try (Connection db = DBConnection.getInstance().getConnection();){
				try(ResultSet rs = db.prepareStatement("SELECT * FROM reports").executeQuery()){
					while (rs.next()) {
						Report repo = new Report(rs);reports.add(repo);
					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return null;
				}
				db.close();
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}
			return reports;
		}


	public void fillFieldsFromResultSet(ResultSet rs) throws SQLException{
		this.report_name = rs.getString("report_name");
		this.type = rs.getString("report_type");
		this.monthly_income= rs.getDouble("monthly_income");
		this.to_compare = new CheckBox();
		this.view_report=new Button("View");
//		this.view_report.setOnAction(e -> {
//
//		});
		this.complains_count=rs.getInt("complains_count");
		view_report.setStyle("-fx-background-color: #FFA500");
	}

	public CheckBox getTo_compare(){
		return this.to_compare;
	}
	public void setTo_compare(CheckBox cb){
		this.to_compare = cb;
	}
	public void addReport(Double monthlyIncome,int complainsCount){

	}
	public void prepareMonthlyReport() throws SQLException, NotFound, IOException{
		
		String sql= null;
		String pattern = "MMddyyyyHHmmss";
		String user_id,item,order_date,contact_phone,delivery_location,contact_name,order_price,store_id = null;
		int order_id;
		// Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date according to the chosen pattern
		DateFormat df = new SimpleDateFormat(pattern);
		this.report_name=df.format(new Date())+".txt";
		String monthly_path="src/lil/models/reports/MonthlyReports/MonthlyReport"+report_name+".txt";
		this.type="Monthly";
//		Open a text file and create an output stream to Store the income data  
		File targetFile = new File(monthly_path);
		targetFile.createNewFile();
		FileWriter fileWriter = new FileWriter(targetFile);

//	    Read Orders IDs and the amount of $ paid from the result set
	    try (Connection conn = DBConnection.getInstance().getConnection()){
	    	  try {
	    		  Statement stmt = conn.createStatement();
	    		  sql = "SELECT * FROM orders";
	    		  ResultSet rs = stmt.executeQuery(sql);

	    		  fileWriter.write("Order date ----- " + " ----- Order id ----- ----- Item ----- ----- Total Cost ----- ----- Delivery Location\n");
	    		  while(rs.next()) {
					  if ("1".equals(rs.getString("store_id"))) {
						  user_id = rs.getString("user_id");
						  order_id = rs.getInt("order_Id");
						  contact_phone = rs.getString("receiver_phone");
						  delivery_location = rs.getString("delivery_location");
						  store_id = rs.getString("store_id");
						  contact_name = rs.getString("contact_name");
						  order_date = rs.getString("order_Date");
						  order_price = rs.getString("order_price");
						  String finalOrderInfo = " | " + order_date + " | " + order_id + " | " + store_id + " | " + order_price + " | " + delivery_location +"\n";
						  try {
							  fileWriter.write(finalOrderInfo);
						  } catch (IOException e) {
							  // TODO Auto-generated catch block
							  fileWriter.write(e.toString());
						  }
					  }
				  }
	    		    try {
	    		    	fileWriter.write("\n||==================================||\n");
	    		    	fileWriter.write("||===========Complains====Report======||\n");
	    		    	fileWriter.write("||==================================||\n");
	    		    	fileWriter.write(usingBufferedReader(this.complainsReport.toString()));
	    		    } catch (IOException e) {
	    		        // TODO Auto-generated catch block
	    		        e.printStackTrace();
	    		    }
    			  fileWriter.close();
    			  rs.close();
    			  stmt.close();
    			  conn.close();
	    		  this.monthlyReport=targetFile;
	            }catch(SQLException se) {
	            	fileWriter.write(se.toString());
	            	fileWriter.write("SQLException: ");
	            	fileWriter.write(se.getMessage());
	            	fileWriter.write("SQLState: ");
	            	fileWriter.write(se.getSQLState());
	            	fileWriter.write("VendorError: ");
	            	fileWriter.write(se.getErrorCode());
	            }
	    }catch(SQLException se) {
        	fileWriter.write(se.toString());
        	fileWriter.write("SQLException: ");
        	fileWriter.write(se.getMessage());
        	fileWriter.write("SQLState: ");
        	fileWriter.write(se.getSQLState());
        	fileWriter.write("VendorError: ");
        	fileWriter.write(se.getErrorCode());
        }
	}

	public void prepareComplainsReport() throws SQLException, NotFound, IOException{
		
		String sql= null;
		Date date;
		String pattern = "MMddyyyyHHmmss";

		// Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date according to the chosen pattern
		DateFormat df = new SimpleDateFormat(pattern);
		int complain_id;
		String complain_title,complain_text,store_adress,dateAsString=null,contact_phone,contact_email;
		String path="src/lil/models/reports/ComplainsReports/complainsReport"+df.format(new Date())+".txt";
//		Open a text file and create an output stream to Store the income data
		File targetFile = new File(path);
		targetFile.createNewFile();
		FileWriter fileWriter = new FileWriter(targetFile);
		
//	    Read Orders IDs and the amount of $ paid from the result set
	    	 try {
	    		  Connection conn = DBConnection.getInstance().getConnection();
	    		  Statement stmt = conn.createStatement();
	    		  sql = "SELECT * FROM complains";
	    		  ResultSet rs = stmt.executeQuery(sql);
	    		  while(rs.next()) {
	    			  complain_id = rs.getInt("complain_id");
	    			  complain_title = rs.getString("complain_title");
	    			  date = rs.getDate("complain_date");
	    			  if(date!=null) 
	    				  dateAsString = df.format(date);
	    			  complain_text = rs.getString("complain_text");
	    			  store_adress = rs.getString("store_adress");
	    			  contact_email = rs.getString("contact_email");
	    			  contact_phone = rs.getString("contact_phone");
	    			  String finalOrderInfo = "Complain date: " + dateAsString + " Complain id: " + complain_id + " Complain title: " + complain_title + " Store adress: " + store_adress + " Phone: " + contact_phone + " Email: " + contact_email
	    					  +"\n" + "Complain content: " + complain_text + "\n";
	    			  try {
	    			  fileWriter.write(finalOrderInfo);
	    			  fileWriter.write("||==================================||\n");
	    			  } catch (IOException e) {
						// TODO Auto-generated catch block
	    				  fileWriter.write(e.toString());
					  }
	    		  }
	    		  this.complainsReport=targetFile;
    			  fileWriter.close();
    			  rs.close();
    			  stmt.close();
    			  conn.close();	 
	            }catch(SQLException se) {
	            	fileWriter.write(se.toString());
	            	fileWriter.write("SQLException: ");
	            	fileWriter.write(se.getMessage());
	            	fileWriter.write("SQLState: ");
	            	fileWriter.write(se.getSQLState());
	            	fileWriter.write("VendorError: ");
	            	fileWriter.write(se.getErrorCode());
	            }

	    }


	public void prepareQuarterReport() throws SQLException, NotFound, IOException{

		// Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date according to the chosen pattern    
		String pattern = "MMddyyyyHHmmss";
		DateFormat df = new SimpleDateFormat(pattern);
		int current_complains_count=0,last_complains_count=5,current_total_income=0,last_total_income=400;
		//Maps that contain the store name as a key, and as a value it contains either a certain store income/ratio 
		Map current_stores_income=new HashMap(),last_stores_income=new HashMap(),stores_income_ratio=new HashMap();
		double complains_ratio,total_income_ratio;
		String sql= null,dateAsString=null;
		
		lastUpdated = new Date(Calendar.getInstance().getTime().getTime());
		dateAsString=df.format(lastUpdated);
		
//		Open a text file and create an output stream to store income data  
		File targetFile = new File("src\\lil\\models\\reports\\QuarterReports\\quarterReport-"+df.format(new Date())+".txt");
		targetFile.createNewFile();
		FileWriter fileWriter = new FileWriter(targetFile);

    	 try {
    		  Connection conn = DBConnection.getInstance().getConnection();
    		  Statement stmt = conn.createStatement();
    		  sql = "SELECT * FROM complains";
    		  ResultSet rs = stmt.executeQuery(sql);
    		  while(rs.next()) {
    			  current_complains_count++;
    		  }
    		  
    		  sql = "SELECT * FROM prices";
    		  rs = stmt.executeQuery(sql);
    		  while(rs.next()) {
    			  current_total_income+=rs.getInt(2);
    			  int store_id=rs.getInt(1),income=rs.getInt(2);;
    			  current_stores_income.put(store_id, income);
    		  }

    		  last_stores_income.put(1, 100);
    		  last_stores_income.put(2, 250);
    		  last_stores_income.put(3, 150);
    		  
    		  total_income_ratio=(double)current_total_income/last_total_income;
    		  
    		  stores_income_ratio.put(1, (double)200/100);
    		  stores_income_ratio.put(2, (double)350/250);
    		  stores_income_ratio.put(3, (double)250/150);
    		  
    		  complains_ratio=(double)current_complains_count/last_complains_count;


			  try {
				  fileWriter.write("||===============Quarter report for last 4 months. Date updated: "+ dateAsString+"====================||\n");
				  fileWriter.write("||======================================================================================================||\n");
				  fileWriter.write("||==================================Stores total monthly income:========================================||\n");
				  Iterator iterator1 = current_stores_income.entrySet().iterator();
				  Iterator iterator2 = last_stores_income.entrySet().iterator();
				  Iterator iterator3 = stores_income_ratio.entrySet().iterator();
			      while (iterator1.hasNext()) {
				        Map.Entry pair1 = (Map.Entry)iterator1.next();
				        Map.Entry pair2 = (Map.Entry)iterator2.next();
				        Map.Entry pair3 = (Map.Entry)iterator3.next();
				        fileWriter.write("Store_ID: "+pair1.getKey()+" || This month's income: "+pair1.getValue()+" || Last month's income: "+pair2.getValue()+" || Ratio: "+pair3.getValue()+"\n");
			       }
			      fileWriter.write("||======================================================================================================||\n");
			      fileWriter.write("Total network income: "+current_total_income+" || Last month's total income: "+last_total_income+ " Ratio: "+total_income_ratio+"==================================||\n");
				  fileWriter.write("||======================================================================================================||\n");
				  fileWriter.write("||Complains count: "+ current_complains_count+" || Last month's complain count:"+last_complains_count+" || Complains Ratio: "+complains_ratio+"                           ||\n");
				  fileWriter.write("||======================================================================================================||\n");
				  
			  } catch (IOException e) {
				// TODO Auto-generated catch block
				  fileWriter.write(e.toString());
			  }
    		  this.quarterReport=targetFile;
			  fileWriter.close();
			  rs.close();
			  stmt.close();
			  conn.close();	 
            }catch(SQLException se) {
            	fileWriter.write(se.toString());
            	fileWriter.write("SQLException: ");
            	fileWriter.write(se.getMessage());
            	fileWriter.write("SQLState: ");
            	fileWriter.write(se.getSQLState());
            	fileWriter.write("VendorError: ");
            	fileWriter.write(se.getErrorCode());
            	fileWriter.close();
            }
	}
	
	
	public File getMonthlyReport() {
		return this.monthlyReport;
	}
	public File getQuarterReport() {
		return this.quarterReport;
	}
	public File getComplainsReport() {
		return this.complainsReport;
	}
	
	private static String usingBufferedReader(String filePath) 
	{
	    StringBuilder contentBuilder = new StringBuilder();
	    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) 
	    {
	 
	        String sCurrentLine;
	        while ((sCurrentLine = br.readLine()) != null) 
	        {
	            contentBuilder.append(sCurrentLine).append("\n");
	        }
	    } 
	    catch (IOException e) 
	    {
	        e.printStackTrace();
	    }
	    return contentBuilder.toString();
	}
	
	public void sendStoreMonthlyReport() {
		  Properties props = System.getProperties();
	        String host = "smtp.gmail.com";
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.user", "lilach.ltd");
	        props.put("mail.smtp.password", "");
	        props.put("mail.smtp.port", "587");
	        props.put("mail.smtp.auth", "true");

	        Session session = Session.getDefaultInstance(props);
	        MimeMessage message = new MimeMessage(session);

	        try {
	            String from="lilach.ltd@gmail.com";
	            String[] gmail2={"aliawidat1@gmail.com"};
	            message.setFrom(new InternetAddress(from));
	            InternetAddress[] toAddress = new InternetAddress[gmail2.length];

	            // To get the array of addresses
	            for( int i = 0; i < gmail2.length; i++ ) {
	                toAddress[i] = new InternetAddress(gmail2[i]);
	            }

	            for (InternetAddress address : toAddress) {
	                message.addRecipient(Message.RecipientType.TO, address);
	            }

	            message.setSubject("Store Monthly Report!");
	            message.setText("You're strore's monthly orders and income report, is here!\n"+ usingBufferedReader(this.monthlyReport.toString()));
	            Transport transport = session.getTransport("smtp");
	            transport.connect(host, "lilach.ltd@gmail.com", "umsrnjzmyvmkttyh");
	            transport.sendMessage(message, message.getAllRecipients());
	            transport.close();

	        } catch (MessagingException ae) {
	            ae.printStackTrace();
	        }
	   }
	public void sendQuarterReport() {
		  Properties props = System.getProperties();
	        String host = "smtp.gmail.com";
	        props.put("mail.smtp.starttls.enable", "true");
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.user", "lilach.ltd");
	        props.put("mail.smtp.password", "");
	        props.put("mail.smtp.port", "587");
	        props.put("mail.smtp.auth", "true");

	        Session session = Session.getDefaultInstance(props);
	        MimeMessage message = new MimeMessage(session);

	        try {
	            String from="lilach.ltd@gmail.com";
	            String[] gmail2={"aliawidat1@gmail.com"};
	            message.setFrom(new InternetAddress(from));
	            InternetAddress[] toAddress = new InternetAddress[gmail2.length];

	            // To get the array of addresses
	            for( int i = 0; i < gmail2.length; i++ ) {
	                toAddress[i] = new InternetAddress(gmail2[i]);
	            }

	            for (InternetAddress address : toAddress) {
	                message.addRecipient(Message.RecipientType.TO, address);
	            }

	            message.setSubject("Network Quarter Report!");
	            message.setText("Dear network manager, quarter report numbers are here!\n"+ usingBufferedReader(quarterReport.toString()));
	            Transport transport = session.getTransport("smtp");
	            transport.connect(host, "lilach.ltd@gmail.com", "umsrnjzmyvmkttyh");
	            transport.sendMessage(message, message.getAllRecipients());
	            transport.close();
	        } catch (MessagingException ae) {
	            ae.printStackTrace();
	        }
	   }


	public Double getMonthlyIncome() {
		return this.monthly_income;
	}
	public String getReport_name(){
		return this.report_name;
	}
//	public Integer getComplains_count(){return this.complains_count;}
}

