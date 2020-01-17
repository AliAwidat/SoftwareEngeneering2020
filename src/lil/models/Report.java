package src.lil.models;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import src.lil.common.DBConnection;
import src.lil.controllers.ReportInterface;
import src.lil.models.Order.NotFound;

public class Report implements ReportInterface {
	private File monthlyReport,quarterReport,complainsReport;
	
	public Report() {
		
	}
	
	public void prepareMonthlyReport() throws SQLException, NotFound, IOException{
		
		String sql= null;
		Date date;
		String pattern = "MM/dd/yyyy HH:mm:ss";

		// Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date according to the chosen pattern
		DateFormat df = new SimpleDateFormat(pattern);
		int order_id;
		String item,price_Domain,delivery_location,dateAsString;
//		Open a text file and create an output stream to Store the income data  
		File targetFile = new File("src\\lil\\models\\reports\\MonthlyReports\\MonthlyReport.txt");
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
	    			  order_id = rs.getInt("order_id");
	    			  item = rs.getString("item");
	    			  date = rs.getDate("order_Date");
	    			  dateAsString = df.format(date);
	    			  price_Domain = rs.getString("price_Domain");
	    			  delivery_location= rs.getString("delivery_location");
	    			  String finalOrderInfo = " | " + dateAsString + " | " +order_id + " | " + item + " | " + price_Domain + " | " + delivery_location + "\n";
	    			  try {
	    			  fileWriter.write(finalOrderInfo);
	    			  } catch (IOException e) {
						// TODO Auto-generated catch block
	    				  fileWriter.write(e.toString());
					}
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
		String pattern = "MM/dd/yyyy HH:mm:ss";

		// Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date according to the chosen pattern
		DateFormat df = new SimpleDateFormat(pattern);
		int complain_id;
		String complain_title,complain_text,store_adress,dateAsString=null,contact_phone,contact_email;
//		Open a text file and create an output stream to Store the income data  
		File targetFile = new File("C:\\Users\\ali\\git\\SoftwareEngeneering2020\\src\\lil\\models\\reports\\ComplainsReports\\complainsReport.txt");
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
	    		  this.monthlyReport=targetFile;
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
	            message.setText("You strore's monthly orders and income report, is here!\n"+ usingBufferedReader(monthlyReport.toString()));
	            Transport transport = session.getTransport("smtp");
	            transport.connect(host, "lilach.ltd@gmail.com", "umsrnjzmyvmkttyh");
	            transport.sendMessage(message, message.getAllRecipients());
	            transport.close();
	        } catch (MessagingException ae) {
	            ae.printStackTrace();
	        }
	   }
	
	public static void main(String [] args) {
		Report report= new Report();
		try {
			report.prepareMonthlyReport();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotFound e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		report.sendStoreMonthlyReport();
    }
}
