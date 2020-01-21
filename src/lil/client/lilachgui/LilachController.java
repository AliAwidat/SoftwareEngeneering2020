package src.lil.client.lilachgui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import src.lil.client.Instance;

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
	protected ImageView cart_id;

	@FXML
	protected Button signout_btn1;

	/***************************************************/
	public void show_client_butt() {
		complain_btn.setVisible(true);
		myorders_btn.setVisible(true);
		cart_id.setVisible(true);
		manageusers_btn.setVisible(false);
	}

	public void show_manager_butt() {
		complain_btn.setVisible(true);
		manageusers_btn.setVisible(true);
		cart_id.setVisible(false);
		myorders_btn.setVisible(false);
	}

	public void show_sign_out_butt() {
		signout_btn1.setVisible(true);
		login_btn.setVisible(false);
		sigup_btn.setVisible(false);
	}

	public void hide_sign_out_butt() {
		signout_btn1.setVisible(false);
		login_btn.setVisible(true);
		sigup_btn.setVisible(true);
	}

	/***************************************************/

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
		if (Instance.getCurrentUser() != null) {
			stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

				@Override
				public void handle(WindowEvent event) {
					try {
						Instance.resetResponse();
						Instance.getClientConsole().get_client().sendToServer("Logout " + Instance.get_id());
					} catch (Exception e) {
						e.printStackTrace();
					}
					while (Instance.getResponse() == null) {
						}
					Instance.getClientConsole().get_client().quit();
				}
			});
		}
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

	@FXML
	public void handle_cart_click(MouseEvent event) throws IOException {
		AnchorPane pane = FXMLLoader.load(getClass().getResource("cartView.fxml"));
		Scene scene = new Scene(pane);
		Stage stage = (Stage) main_anchor_pane.getScene().getWindow();

		stage.setScene(scene);
		stage.setTitle("Cart");
		stage.show();
	}

	@FXML
	public void initialize() {
		if (Instance.getCurrentUser() == null) {
			signout_btn1.setVisible(false);
			complain_btn.setVisible(false);
			myorders_btn.setVisible(false);
			manageusers_btn.setVisible(false);
			cart_id.setVisible(false);
			hide_sign_out_butt();
		} else if (Instance.getCurrentUser().getClass().getName().contains("Client")) {
			show_client_butt();
			show_sign_out_butt();
		} else {
			show_manager_butt();
			show_sign_out_butt();
		}

	}

	@FXML
	public void handle_signout_butt() throws IOException {
		try {
			Instance.resetResponse();
			Instance.getClientConsole().get_client().sendToServer("Logout " + Instance.get_id());
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (Instance.getResponse() == null) {
			// System.out.println("Waiting for response...");
		}
		if (Instance.getResponse().equals("successful")) {
			Instance.setCurrentUser(null);
			this.handle_menu_butt(null);
		}
	}
}
