package src.lil.client.lilachgui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public abstract class LilachController {

	@FXML
	protected AnchorPane main_anchor_pane;

	@FXML
	protected Button menu_btn;

	@FXML
	protected Button myorders_btn;

	@FXML
	protected Button complain_btn;

	@FXML
	protected Button manageusers_btn;

	@FXML
	protected Button sigup_btn;

	@FXML
	protected Button login_btn;

	@FXML
	protected ListView<?> menu_items_list;

	@FXML
	protected ImageView logo;

	@FXML
	public void handle_login_butt(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("LoginPage.fxml"));
		Scene scene = new Scene(pane);

		Stage stage = (Stage) main_anchor_pane.getScene().getWindow();
		stage.setScene(scene);
		stage.setTitle("Login");
		stage.show();
	}

	@FXML
	public void handle_signup_butt(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("SignupPage.fxml"));
		Scene scene = new Scene(pane);
		Stage stage = (Stage) main_anchor_pane.getScene().getWindow();

		stage.setScene(scene);
		stage.setTitle("Sign up");
		stage.show();
	}

	@FXML
	public void handle_complain_butt(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("ComplainPage.fxml"));
		Scene scene = new Scene(pane);
		Stage stage = (Stage) main_anchor_pane.getScene().getWindow();

		stage.setScene(scene);
		stage.setTitle("Complain!");
		stage.show();
	}

	@FXML
	public void handle_manage_butt(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("ManageUsersPage.fxml"));
		Scene scene = new Scene(pane);
		Stage stage = (Stage) main_anchor_pane.getScene().getWindow();

		stage.setScene(scene);
		stage.setTitle("User managment.");
		stage.show();
	}

	@FXML
	public void handle_menu_butt(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("MenuPage.fxml"));
		Scene scene = new Scene(pane);
		Stage stage = (Stage) main_anchor_pane.getScene().getWindow();

		stage.setScene(scene);
		stage.setTitle("Welcome to Lilach.");
		stage.show();
	}

	@FXML
	public void handle_my_order_butt(ActionEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("myOrdersPage.fxml"));
		Scene scene = new Scene(pane);
		Stage stage = (Stage) main_anchor_pane.getScene().getWindow();

		stage.setScene(scene);
		stage.setTitle("My orders history");
		stage.show();
	}
}
