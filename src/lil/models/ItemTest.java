package src.lil.models;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import src.lil.Enums.ItemType;

class ItemTest {
//	Item getFromDb = new Item(ItemType.BOUQUET,"",0.0);
//	@Test
//    public void addItem() throws Exception{
//    	Item addToDb = new Item(ItemType.BOUQUET,"",20.0);
//        if(!(addToDb.insert())) {System.out.println("Failed");}
//    }
//	@Test
//    public void findItem() throws Exception{
//    	Item getFromDb =  Item.findById(6);
//        if(getFromDb==null) {System.out.println("Failed");}
//    }
//	@Test
//    public void updateItem() throws Exception{
////		getFromDb.setId(6);
//		getFromDb.setPrice(50.0);
////		getFromDb.setDominantColor("");
////		getFromDb.setType(ItemType.BOUQUET);
////		getFromDb.setUpdated(1);
//    	boolean updated = Item.update(getFromDb);
//        if(!(updated)) {System.out.println("Failed");}
//    }
//	@Test
//    public void deleteItem() throws Exception{
//    	boolean deleted = Item.delete(8);
//        if(!(deleted)) {System.out.println("Failed");}
//    }
//	@Test
//    public void findItem() throws Exception{
//   	Item getFromDb =  Item.findById(2);
//   	System.out.println(getFromDb.getType());
//   	if(getFromDb==null) System.out.println("Failed");}
//   	
//	@Test
//    public void changePriceAllStores() throws Exception{
//		System.out.println(Item.changePriceAllStores(2, 0.8));
//   	//System.out.println(getFromDb.getType());
//   	//if(getFromDb==null) {System.out.println("Failed");}
//  }
//	@Test
//    public void changePriceAllItems() throws Exception{
//		System.out.println(Item.changePriceAllItems(1, 0.8));
//   	//System.out.println(getFromDb.getType());
//   	//if(getFromDb==null) {System.out.println("Failed");}
//  }
	@Test
    public void insettest() throws Exception{
	Item newItem= new Item(ItemType.BOUQUET,"RED",100.0,"");
	newItem.insert();
	}
}

