//package src.lil.models;
//
//
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//import java.io.File;
//import java.io.IOException;
//import java.sql.SQLException;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import src.lil.models.Order.NotFound;
//
//public class ReportTest{
//	
//	@BeforeEach
//	void setUp() throws Exception {
//		
//	}
//
//	@Test
//	void testComplainsReport() throws IOException, NotFound {
//		Report report= new Report();
//		try {
//			report.prepareComplainsReport();
//		}catch(SQLException se){
//			se.printStackTrace();
//		}
//		File file = report.getComplainsReport();
//		
//		assertTrue(file.exists());
//	}
//	
//	@Test
//	void testMonthlyReport() throws NotFound, IOException{
//		Report report = new Report();
//		try {
//			report.prepareMonthlyReport();
//		}catch(SQLException se) {
//			se.printStackTrace();
//		}
//		File file = report.getMonthlyReport();
//		assertTrue(file.exists());
//	}
//}
