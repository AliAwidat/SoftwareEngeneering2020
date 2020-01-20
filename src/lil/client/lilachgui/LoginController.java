package src.lil.client.lilachgui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LoginController extends LilachController {
    @FXML
    private TextField user_name;

    @FXML
    void handle_sign_up_butt(ActionEvent event) throws IOException {
    	this.handle_signup_butt(event);
    }
    
}
