package src.lil.models;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import src.lil.common.DBConnection;
import src.lil.controllers.ReportInterface;
import src.lil.models.Order.NotFound;

public class Report implements ReportInterface {
	private File incomeReport,ordersReport,quarterReport,complainsReport;
	
	public Report() {
		
	}
	
	public Report(File report, Enum reportType) {
		if(reportType.equals(src.lil.Enums.ReportType.OrdersReport)) {
			ordersReport=report;
		}else if(reportType.equals(src.lil.Enums.ReportType.ComplainsReport)) {
			complainsReport = report; 
		}else if(reportType.equals(src.lil.Enums.ReportType.QuarterlyEarningsReport)) {
			quarterReport=report;
		}else {
			System.out.println("Enum Unkown");
		}
	}
	public void prepareMonthlyReport() throws SQLException, NotFound, IOException{
		
		String sql= null;
		Date date;
		Date current_date = new Date(0);
		String pattern = "MM/dd/yyyy HH:mm:ss";

		// Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date according to the chosen pattern
		DateFormat df = new SimpleDateFormat(pattern);
		int order_id;
		String item,price_Domain,delivery_location,dateAsString;
//		Open a text file and create an output stream to Store the income data  
		File targetFile = new File("src/lil/models/reports/MonthlyReports/incomeReport" + df.format(current_date) +".txt");
		FileWriter fileWriter = new FileWriter(targetFile);

//	    Read Orders IDs and the amount of $ paid from the result set
	    try (Connection conn = DBConnection.getInstance().getConnection()){
	    	  try {
	    		  Statement stmt = conn.createStatement();
	    		  sql = "SELECT * FROM orders.*";
	    		  ResultSet rs = stmt.executeQuery(sql);
	    		  fileWriter.write("Order date ----- " + "Order id ----- Item ----- Total Cost ----- Delivery Location\n");
	    		  while(rs.next()) {
	    			  order_id = rs.getInt("order_id");
	    			  item = rs.getString("item");
	    			  date = rs.getDate("order_Date");
	    			  dateAsString = df.format(date);
	    			  price_Domain = rs.getString("price_Domain");
	    			  delivery_location= rs.getString("delivery_location");
	    			  String finalOrderInfo = dateAsString + " " +order_id + " " + item + " " + price_Domain + " " + delivery_location + "\n";
	    			  try {
	    			  fileWriter.write(finalOrderInfo);
	    			  } catch (IOException e) {
						// TODO Auto-generated catch block
	    				  fileWriter.write(e.toString());
					}
	    		  }
	    		  this.ordersReport=targetFile;
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
		Date current_date = new Date(0);
		String pattern = "MM/dd/yyyy HH:mm:ss";

		// Create an instance of SimpleDateFormat used for formatting 
		// the string representation of date according to the chosen pattern
		DateFormat df = new SimpleDateFormat(pattern);
		int complain_id;
		String complain_title,complain_text,store_adress,dateAsString,contact_phone,contact_email;
//		Open a text file and create an output stream to Store the income data  
		File targetFile = new File("src/lil/models/reports/complainsReport" + df.format(current_date) + ".txt");
		FileWriter fileWriter = new FileWriter(targetFile);

//	    Read Orders IDs and the amount of $ paid from the result set
	    try (Connection conn = DBConnection.getInstance().getConnection()){
	    	  try {
	    		  Statement stmt = conn.createStatement();
	    		  sql = "SELECT * FROM complains";
	    		  ResultSet rs = stmt.executeQuery(sql);
	    		  while(rs.next()) {
	    			  complain_id = rs.getInt("complain_id");
	    			  complain_title = rs.getString("complain_title");
	    			  date = rs.getDate("complain_date");
	    			  dateAsString = df.format(date);
	    			  complain_text = rs.getString("complain_text");
	    			  store_adress = rs.getString("store_adress");
	    			  contact_email = rs.getString("contact_email");
	    			  contact_phone = rs.getString("contact_phone");
	    			  String finalOrderInfo = "Complain date: " + dateAsString + " Complain id: " + complain_id + " Complain title: " + complain_title + " Store adress: " + store_adress + " Phone: " + contact_phone + " Email: " + contact_email
	    					  +"\n" + "Complain content: \n" + complain_text + "\n";
	    			  try {
	    			  fileWriter.write(finalOrderInfo);
	    			  } catch (IOException e) {
						// TODO Auto-generated catch block
	    				  fileWriter.write(e.toString());
					     }
	    			  fileWriter.close();
	    			  rs.close();
	    			  stmt.close();
	    			  conn.close();
	    		  }
	    		  this.ordersReport=targetFile;
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

	public void prepareQuarterReport() throws SQLException, NotFound, IOException{
		
		
	}
	
}
