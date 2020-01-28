package src.lil.client.lilachgui;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
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
	private TableView<Item> selected;

	@FXML
	private TableColumn<Item, String> sel_item_cul;

	@FXML
	private TableColumn<Item, ItemType> sel_type_cul;

	@FXML
	private TableColumn<Item, String> sel_color_cul;

	@FXML
	private TableColumn<Item, Double> sel_price_cul;

	@FXML
	public void initialize() {
		this.check_logins();
		Client currUser = ((Client)Instance.getCurrentUser());
		Contactname.setText(currUser.getName());
		ReceiverPho.setText(currUser.getName());
		sel_item_cul.setCellValueFactory(new PropertyValueFactory<>("id"));
		sel_type_cul.setCellValueFactory(new PropertyValueFactory<>("type"));
		sel_color_cul.setCellValueFactory(new PropertyValueFactory<>("dominantColor"));
		sel_price_cul.setCellValueFactory(new PropertyValueFactory<>("price"));
		selected.setItems(selected_items);

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
			JsonObject myData = new JsonObject();
			myData.addProperty("contact_name",Contactname.getText());
			myData.addProperty("receiver_phone",ReceiverPho.getText());
			myData.addProperty("Shipping_Hour",ShippingTime.getText());
			myData.addProperty("Shipping_Date",ShippingDate.getText());
			myData.addProperty("greating",AddGreating.isSelected());
			myData.addProperty("greating_text",greating.getText());
			myData.addProperty("WithDelivery",WithDelivery.isSelected());
			myData.addProperty("delivery_location",Deliverylocation.getText());
			Gson gson = new Gson();
			String element = gson.toJson(myData);
			element = "SubmitPurchase" + element;
			Instance.getClientConsole().get_client().sendToServer(element);
			msg_to_client.setText("purchase sent successfully :)");

		}
		catch (Exception e){
			msg_to_client.setText("Something went wrong ! please Try again");
		}
	}

}
