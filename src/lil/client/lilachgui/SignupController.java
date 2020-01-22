package src.lil.client.lilachgui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
public class SignupController extends LilachController {


	    @FXML
	    private TextField ContactId;

	    @FXML
	    private TextField FullName;

	    @FXML
	    private TextField phoneNum;

	    @FXML
	    private TextField email;

	    @FXML
	    private TextField bankAcc;

	    @FXML
	    private TextField balance;

	    @FXML
	    private TextField creditCard;

	    @FXML
	    private ComboBox<?> StoreNum;

	    @FXML
	    private TextField pass;

	    @FXML
	    private TextField SubType;

	    @FXML
	    private Button register;

	    @FXML
	    private ImageView cart_id1;

	    @FXML
	    private Button complain_btn;

	    @FXML
	    private Button manageusers_btn;

	    @FXML
	    private Button menu_btn;

	    @FXML
	    private Button myorders_btn;

	    @FXML
	    private ImageView cart_id;

	    
	    
		@FXML
		public void handle_Register_butt(ActionEvent event) throws IOException {
			AnchorPane pane = FXMLLoader.load(getClass().getResource("SignupPage.fxml"));
			Scene scene = new Scene(pane);
			Stage stage = (Stage) main_anchor_pane.getScene().getWindow();
			stage.setScene(scene);
			stage.show();
		}
	    
	    
	}
