package src.lil.client.lilachgui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
	private TextField greating;

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
	public void initialize() {
		this.check_logins();
	}
}
