package src.lil.models;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Date;

import org.junit.jupiter.api.Test;

import src.lil.Enums.OrderType;

class OrderTest {
    //CHECKED
//	@Test
//    public void checkInsertIntoOrders() throws Exception{
//    	
//    	Order addToDb = new Order(313138943,7878,OrderType.Other,"", "red","0501132522",
//    			new Date(0),"250-300", true,"pika", true) ; 
//    	if(!(addToDb.insertIntoOrders())) {System.out.println("Failed");}
//    }
//	    
	//CHECKED
//    @Test
//    public void checkfindAllByUserId() throws Exception{
//    	Order myLastOrder =new Order(5652,15,OrderType.Other,"", "blue","0548875671",
//    			new Date(0),"100-150", true,"shaar ha-gai", true) ; 
//    	System.out.println(myLastOrder.findAllByUserId(5652));
//        //if(!myLastOrder.equals(WantedOrder)){System.out.println("Failed to get sameUser");}
//    }
	//CHECKED
    @Test
    public void checkfindOrderById() throws Exception{
    	Order myLastOrder =new Order(313138943,888,OrderType.Other,"", "red","0501132522",
    			new Date(0),"250-300", true,"pika", true) ; 
        Order e = myLastOrder.findOrderById(888);
        System.out.println(e.getDeliveryLocation());
    }
    //CHECKED
//    @Test
//    public void checkcountForUser() throws Exception{
//    	Order myLastOrder =new Order(5652,15,OrderType.Other,"", "blue","0548875671",
//    			new Date(0),"100-150", true,"shaar ha-gai", true) ; 
//        int e = myLastOrder.countForUser(5652);
//        System.out.println(e);
//    }
    //CHECKED
//	  @Test
//	  public void checkDeleteOrder() throws Exception{
//	  	Order myLastOrder =new Order(5652,15,OrderType.Other,"", "blue","0548875671",
//	  			new Date(0),"100-150", true,"shaar ha-gai", true) ; 
//	      boolean e = myLastOrder.DeleteOrder(15);
//	      System.out.println(e);
//	  }
}
