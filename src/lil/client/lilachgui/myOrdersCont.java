package src.lil.client.lilachgui;



import java.sql.SQLException;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import src.lil.Enums.SubscriptionType;
import src.lil.client.Instance;

import src.lil.models.Order;
import src.lil.models.User;

import src.lil.Enums.ItemType;
import src.lil.client.Instance;
import src.lil.models.Client;
import src.lil.models.Item;

import java.io.IOException;


public class myOrdersCont extends LilachController {

	@FXML
	private TextField Contactname;

	@FXML
	private TextField ReceiverPho;

	@FXML
	private TextField ShippingTime;

	@FXML
	private TextField DeliveryLoc;


	@FXML
	private TextField ShippingDate;

	@FXML
	private CheckBox Delivery;

	@FXML
	private CheckBox AddGreating;

	@FXML
	private ImageView cart_id1;

	@FXML
	private TextField OrderId;

	@FXML
	private Button purchase;

	@FXML
	private CheckBox WithDelivery;


	@FXML
	private Text msg_to_client;

	@FXML
	private TextArea Deliverylocation;
	@FXML
	private TextArea greating;
	@FXML
	private Text star4;

	@FXML
	private Text star3;

	@FXML
	private Text star2;

	@FXML
	private Text star1;

	@FXML

	private TextField orderCost;


	public void initialize() {

			this.check_logins();
			Client currUser = ((Client) Instance.getCurrentUser());
//			Order currOrder = ((Order) Instance.getCurrentUser());
			Contactname.setText(currUser.getName());
			ReceiverPho.setText(currUser.getPhone());
//			int id= currOrder.getorderId();
//			OrderId.setSelectionEnd(id);
//			orderCost.setText(currOrder.OrderCost());


	@FXML
	public void initialize() {
		this.check_logins();
		Client currUser = ((Client)Instance.getCurrentUser());
		Contactname.setText(currUser.getName());
		ReceiverPho.setText(currUser.getName());
		System.out.println("here1");
		sel_item_cul.setCellValueFactory(new PropertyValueFactory<>("id"));
		System.out.println("here2");
		sel_type_cul.setCellValueFactory(new PropertyValueFactory<>("type"));
		System.out.println("here3");
		sel_color_cul.setCellValueFactory(new PropertyValueFactory<>("dominantColor"));
		System.out.println("here4");
		sel_price_cul.setCellValueFactory(new PropertyValueFactory<>("price"));
		System.out.println("here5");
		selected.setItems(selected_items);
		System.out.println("here6");

//		catch(Exception e){
//			System.out.println(e.getMessage());
//		}

	}
	@FXML
	void handle_Add_greating_butt(ActionEvent event){
		if (AddGreating.isSelected()) {
			greating.setVisible(true);
		}
		else {
			greating.setVisible(false);
		}
	}
	@FXML
	void handle_Add_dele_butt(ActionEvent event){
		if (WithDelivery.isSelected()) {
			Deliverylocation.setVisible(true);
		}
		else {
			Deliverylocation.setVisible(false);
		}
	}
	@FXML
	void handle_purchase_butt(ActionEvent event) throws IOException {
		try{
			if(Contactname.getText().isEmpty() || ReceiverPho.getText().isEmpty() || ShippingTime.getText().isEmpty()||ShippingDate.getText().isEmpty()){
				if(Contactname.getText().isEmpty()){
					Contactname.setStyle("-fx-background-color: yellow;");
					star1.setVisible(true);
				}
				if(ReceiverPho.getText().isEmpty()) {
					ReceiverPho.setStyle("-fx-background-color: yellow;");
					star2.setVisible(true);
				}
				if(ShippingTime.getText().isEmpty()) {
					ShippingTime.setStyle("-fx-background-color: yellow;");
					star3.setVisible(true);
				}
				if(ShippingDate.getText().isEmpty()) {
					ShippingDate.setStyle("-fx-background-color: yellow;");
					star4.setVisible(true);
				}
				msg_to_client.setText("Please fill in the yellow fields");
				return;
			}
//			System.out.println(((Client) Instance.getCurrentUser()).getUserId());
//			Order submitOrder = new Order(((Client) Instance.getCurrentUser()).getUserId(),Contactname.getText(),ReceiverPho.getText(),ShippingTime.getText()
//												,ShippingDate.getText(),AddGreating.isSelected(),greating.getText(),WithDelivery.isSelected(),Deliverylocation.getText(),""
//								,((Client)Instance.getCurrentUser()).getStoreId());

			Gson gson = new Gson();
			JsonObject myData = new JsonObject();
			myData.addProperty("userId",String.valueOf(((Client) Instance.getCurrentUser()).getUserId()));
			myData.addProperty("contactName",Contactname.getText());
			myData.addProperty("receiver_phone",ReceiverPho.getText());
			myData.addProperty("ShippingHour",ShippingTime.getText());
			myData.addProperty("ShippingDate",ShippingDate.getText());
			myData.addProperty("greating",AddGreating.isSelected());
			myData.addProperty("greatingText",greating.getText());
			myData.addProperty("Delivrey",WithDelivery.isSelected());
			myData.addProperty("delLocation",Deliverylocation.getText());
			myData.addProperty("orderDate","");
			myData.addProperty("storeid",((Client)Instance.getCurrentUser()).getStoreId());
			String json = gson.toJson(myData);
//			json = "SubmitPurchase" + json;
//			try {
//				Instance.getClientConsole().get_client().sendToServer(json);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//			while(Instance.getResponse()==null) {
//				System.out.println("Waiting...");
//			}
			String element = gson.toJson(myData);
			System.out.println(element);
			element = "SubmitPurchase " + element;
			Instance.getClientConsole().get_client().sendToServer(element);
			msg_to_client.setText("purchase sent successfully :)");

		}
		catch (Exception e){
			msg_to_client.setText("Something went wrong ! please Try again");
		}
	}
//	Gson gson = new Gson();
//		user_id_txt.setStyle("-fx-background-color: white;");
//		email_txt.setStyle("-fx-background-color: white;");
//		id_lable_id.setFill(Color.BLACK);
//		error_msg.setText("");
//		if (check_input()) {
//		SubscriptionType val;
//		if (sub_type_box.getValue().equals("None")) {
//			val = SubscriptionType.nonSubscription;
//		} else if (sub_type_box.getValue().equals("Monthly")) {
//			val = SubscriptionType.Monthly;
//		} else {
//			val = SubscriptionType.Yearly;
//		}
//		Client register = new Client(Integer.parseInt(user_id_txt.getText()), fullname_txt.getText(),
//
//				phone_num_txt.getText(), bank_acc_txt.getText(), address_txt.getText(), email_txt.getText(), password_txt.getText(),
//				val, credit_txt.getText(),  store_add_box.getValue().split("-")[1], 0.0);
//
//		String json = gson.toJson(register);
//		try {
//			Instance.resetResponse();
//			Instance.getClientConsole().get_client().sendToServer("register " + json);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		while(Instance.getResponse()==null) {
//			System.out.println("Waiting...");
//		}

}
