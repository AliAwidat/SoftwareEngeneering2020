package src.lil.client.lilachgui;

import java.util.List;

import com.google.gson.Gson;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import src.lil.client.Instance;

public class SignupController extends LilachController {
	@FXML
	private TextField user_id_txt;

	@FXML
	private TextField phone_num_txt;

	@FXML
	private TextField email_txt;

	@FXML
	private TextField bank_acc_txt;

	@FXML
	private TextField credit_txt;

	@FXML
	private ComboBox<String> store_add_box;

	@FXML
	private TextField password_txt;

	@FXML
	private Button register_butt;

	@FXML
	private TextField fullname_txt;

	@FXML
	private ComboBox<String> sub_type_box;

	////////////////////////////////////////

	List<String> addresses;

	////////////////////////////////////////
	@FXML
	public void initialize() {
		this.check_logins();
		Gson gson = new Gson();
		try {
			Instance.resetResponse();
			Instance.getClientConsole().get_client().sendToServer("get stores");
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (Instance.getResponse() == null) {
			System.out.println("");
		}
		addresses = gson.fromJson(Instance.getResponse(), List.class);
		for (String string : addresses) {
			store_add_box.getItems().add(string);
		}
		
	}

	@FXML
	public void handle_reg_butt(ActionEvent event) {

	}
}
