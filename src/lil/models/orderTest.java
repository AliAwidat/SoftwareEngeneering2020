package src.lil.models;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.time.format.DateTimeFormatter;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import junit.framework.TestCase;
import src.lil.Enums.OrderType;

class orderTest extends TestCase {
	
	List<Integer> my_list = new ArrayList();
	LocalTime ShippingHour = LocalTime.parse("16:17:00");
	LocalDate ShippingDate = LocalDate.parse("2020-01-25");
	LocalDateTime orderDate = LocalDateTime.now();
    //CHECKED
	@Test
    public void checkInsertIntoOrders() throws Exception{
		my_list.add(2);
		my_list.add(3);
    	Order TestOrder = new Order(4444,4541112,OrderType.FromTheCatlaog,my_list, "","0522245689",
    			orderDate,"2-10", true,"Bab jacobs",ShippingHour,ShippingDate, true ,"2ere b jacobs","basel", "1") ; 
    	Assert.assertTrue(TestOrder.insertIntoOrders()== true);
    }
	
//	    
//	//CHECKED
//    @Test
//    public void checkfindAllByUserId() throws Exception{
//    	Order myLastOrder =new Order(91,1,OrderType.FromTheCatlaog,my_list, "","0501778832",
//    			orderDate,"2-10", true,"Bab jacobs",ShippingHour,ShippingDate, true ,"2ere b jacobs","basel", "8") ; 
//    	System.out.println(myLastOrder.findAllByUserId(5652));
//        //if(!myLastOrder.equals(WantedOrder)){System.out.println("Failed to get sameUser");}
//    }
//	//CHECKED
//    @Test
//    public void checkfindOrderById() throws Exception{
//    	Order myLastOrder =new Order(91,1,OrderType.FromTheCatlaog,my_list, "","0501778832",
//    			orderDate,"2-10", true,"Bab jacobs",ShippingHour,ShippingDate, true ,"2ere b jacobs","basel", "8"); 
//        Order e = myLastOrder.findOrderById(888);
//        System.out.println(e.getDeliveryLocation());
//    }
//   // CHECKED
//    @Test
//    public void checkcountForUser() throws Exception{
//    	Order myLastOrder =new Order(91,1,OrderType.FromTheCatlaog,my_list, "","0501778832",
//    			orderDate,"2-10", true,"Bab jacobs",ShippingHour,ShippingDate, true ,"2ere b jacobs","basel", "8") ; 
//        int e = myLastOrder.countForUser(5652);
//        System.out.println(e);
//    }
//   // CHECKED
//	  @Test
//	  public void checkDeleteOrder() throws Exception{
//	  	Order myLastOrder =new Order(91,1,OrderType.FromTheCatlaog,my_list, "","0501778832",
//    			orderDate,"2-10", true,"Bab jacobs",ShippingHour,ShippingDate, true ,"2ere b jacobs","basel", "8") 
//	      boolean e = myLastOrder.DeleteOrder(15);
//	      System.out.println(e);
//	  }
//	// CHECKED
//	  @Test
//	  public void checkOrderCost() throws Exception{
//	  	Order myLastOrder =new Order(91,1,OrderType.FromTheCatlaog,my_list, "","0501778832",
//    			orderDate,"2-10", true,"Bab jacobs",ShippingHour,ShippingDate, true ,"2ere b jacobs","basel", "8"); 
//	      String  e = myLastOrder.OrderCost(myLastOrder);
//	      System.out.println(e);
//	  }
//	// CHECKED
//	  @Test
//	  public void checkorderTimeDiff() throws Exception{
//	  	Order myLastOrder =new Order(91,1,OrderType.FromTheCatlaog,my_list, "","0501778832",
//    			orderDate,"2-10", true,"Bab jacobs",ShippingHour,ShippingDate, true ,"2ere b jacobs","basel", "8") ; 
//	      String  e = myLastOrder.orderTimeDiff(myLastOrder);
//	      System.out.println(e);
//	  }
}
