package src.lil.client.lilachgui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

public class LoginController extends LilachController {
    
	private String user_id = "";
	private String password = "";
	
	/************************************************/
	
	@FXML
    private TextField userid_txt;

    @FXML
    private PasswordField password_txt;

    @FXML
    private Text error_txt;

    @FXML
    private Button loginbutton;
    
    @FXML
    void handle_id_input(KeyEvent event) {
    	user_id = userid_txt.getText();
    }

    @FXML
    void handle_login(ActionEvent event) {
    	error_txt.setText("id: " + user_id + " pw: " + password);
    }

    @FXML
    void handle_password_input(KeyEvent event) {
    	password = password_txt.getText();
    }
    
}
