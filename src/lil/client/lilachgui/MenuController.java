package src.lil.client.lilachgui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.CheckBox;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import src.lil.client.Instance;
import src.lil.models.Item;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MenuController extends LilachController{


	@FXML
	private TableView<Item> menue_tableview;

	@FXML
	private TableColumn<Item, String> id_cul;

	@FXML
	private TableColumn<Item, ImageView> pic_cul;

	@FXML
	private TableColumn<Item, Double> price_cul;

	@FXML
	private TableColumn<CheckBox,CheckBox> checkbox_cul;

	@FXML
	private Button add_cart_order;

	@FXML
	void handle_order_cart(MouseEvent event) {

	}

	@FXML
	public void initialize() {
		this.check_logins();
		isLoggedin();
		displayItems();
	}

	@FXML
	public void handle_login_butt(ActionEvent event) throws IOException {
		get_scene("LoginPage.fxml", "Login");
	}

	@FXML
	public void handle_signup_butt(ActionEvent event) throws IOException {
		get_scene("SignupPage.fxml", "Sign up");
	}

	@FXML
	public void handle_complain_butt(ActionEvent event) throws IOException {
		get_scene("ComplainPage.fxml", "Complain!");
	}

	@FXML
	public void handle_manage_butt(ActionEvent event) throws IOException {
		get_scene("ManageUsersPage.fxml", "User managment.");
	}

	@FXML
	public void handle_menu_butt(ActionEvent event) throws IOException {

		get_scene("MenuPage.fxml", "Welcome to Lilach.");
	}

	@FXML
	public void handle_my_order_butt(ActionEvent event) throws IOException {
		get_scene("myOrdersPage.fxml", "My orders history");
	}

	private void displayItems() {
//		Item item = new Item();
//		List<Item> items =item.filterItems("FLOWER");
		id_cul.setCellValueFactory(new PropertyValueFactory<>("id"));
		pic_cul.setCellValueFactory(new PropertyValueFactory<>("image1"));
		price_cul.setCellValueFactory(new PropertyValueFactory<>("price"));
		checkbox_cul.setCellValueFactory(new PropertyValueFactory<>("select"));
		menue_tableview.setItems(getItems());
	}

	public ObservableList<Item>getItems(){
		ObservableList<Item> items = FXCollections.observableArrayList();
		Item item1=new Item();
		Item item2=new Item();
		Item item3=new Item();
		item1.setPrice(70.0);
		item1.setId(1);
		item1.setImage("flower.jpg");
		item1.setImage1(new ImageView());
//		Image tmp=new Image(item1.getImage());
//		item1.getImage1().setImage(tmp);

		item1.setSelect(new CheckBox());
		items.add(item1);
		item2.setPrice(60.0);
		item2.setId(2);
		item2.setImage("flower.jpg");
		item2.setImage1(new ImageView());
//		item2.getImage1().setImage(new Image(item2.getImage()));
		item2.setSelect(new CheckBox());
		items.add(item2);
		item3.setPrice(100.0);
		item3.setId(3);
		item3.setImage("flower.jpg");
		item3.setImage1(new ImageView());
//		item3.getImage1().setImage(new Image(item3.getImage()));
		item3.setSelect(new CheckBox());
		item3.getSelect().setAlignment(Pos.CENTER);
		items.add(item3);

		return items;
	}
	public void isLoggedin(){
		if (Instance.getCurrentUser() == null)
			checkbox_cul.setVisible(false);
	}
}

