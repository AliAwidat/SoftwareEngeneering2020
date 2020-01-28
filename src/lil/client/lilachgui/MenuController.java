package src.lil.client.lilachgui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import src.lil.Enums.ItemType;
import src.lil.client.Instance;
import src.lil.models.Item;

import java.io.IOException;
import java.util.List;

public class MenuController extends LilachController{

	public Item boque_items;
	ObservableList<String>combo_list=FXCollections.observableArrayList("White","Red","Blue");
	@FXML
	Spinner<Integer> spinner;
	@FXML
	private ComboBox<String> combo_box;
	@FXML
	private ImageView go_btn;
	@FXML
	private Label finish_order_label;
	@FXML
	private TableView<Item> menue_tableview;

	@FXML
	private TableColumn<Item, CheckBox> create_boque_cul;

	@FXML
	private Button creat_boque_btn;

	@FXML
	private AnchorPane anchor_bar;

	@FXML
	private TableColumn<Item, String> id_cul;

	@FXML
	private TableColumn<Item, ImageView> pic_cul;

	@FXML
	private TableColumn<Item, Double> price_cul;

	@FXML
	private TableColumn<Item, ItemType> type_cul;

	@FXML
	private TableColumn<Item,CheckBox> checkbox_cul;


	@FXML
	private Button add_cart_order;

	@FXML
	private TableColumn<Item,String>Type_cul;

	@FXML
	private TextField to_field;

	@FXML
	private TextField from_field;

	private FilteredList<Item> filtered_list;

	private ObservableList<Item>original_list;
	@FXML
	void handle_order_cart(MouseEvent event) throws IOException {
		List<Item>tmp =getCheckedItems();
		selected_items.addAll(tmp);

	}

	@FXML
	public void initialize() {
		this.check_logins();
		displayItems();
	}


	private void displayItems() {
//		Gson gson=new Gson();
		List<Item> items;
		items=Item.filterItems(" <> 'CUSTOM'",1);
		combo_box.setValue("None");
		combo_box.setItems(combo_list);
		id_cul.setCellValueFactory(new PropertyValueFactory<>("id"));
		pic_cul.setCellValueFactory(new PropertyValueFactory<Item,ImageView>("object_image"));
		type_cul.setCellValueFactory(new PropertyValueFactory<>("type"));
		price_cul.setCellValueFactory(new PropertyValueFactory<>("price"));
		checkbox_cul.setCellValueFactory(new PropertyValueFactory<>("checked"));
		create_boque_cul.setCellValueFactory(new PropertyValueFactory<>("flowers_number"));
		boque_items=new Item();
		menue_tableview.setItems(getItems(items));
		original_list= menue_tableview.getItems();
	}

	public ObservableList<Item>getItems(List getFromDB){
		ObservableList<Item> items = FXCollections.observableArrayList();
		if (Instance.getCurrentUser() == null){
			for(Object item : getFromDB){
				Item toItem = (Item)item;
				toItem.checked.setVisible(false);
				finish_order_label.setVisible(false);
				if(toItem.getType().equals("FLOWER"))
					toItem.getFlowers_number().setVisible(true);
				else
					toItem.getFlowers_number().setVisible(false);
			}
		}
		items.addAll(getFromDB);

		return items;
	}

	public void set_mouse_hand(){
		add_cart_order.getScene().setCursor(Cursor.HAND);
	}

	public void handle_go_btn(MouseEvent mouseEvent) throws IOException {
		filtered_list= new FilteredList<>(original_list);
		Label error_msg = new Label("Wrong parameters!");
		error_msg.maxHeight(17);
		error_msg.maxWidth(160);
		try{
			if(combo_box.getValue().equalsIgnoreCase("None")){
					if (to_field.getText().isEmpty() && from_field.getText().isEmpty()) {
						filtered_list = new FilteredList<>(original_list);
						return;
					}if (!to_field.getText().isEmpty() && from_field.getText().isEmpty()) {
					filtered_list.setPredicate(
							t -> {
								if (t.getPrice() <= Double.parseDouble(to_field.getText()))
									return true;
								return false; // or true
							}
					);
					menue_tableview.setItems(filtered_list);
				} else if (to_field.getText().isEmpty() && !from_field.getText().isEmpty()) {
					filtered_list.setPredicate(
							t -> {
								if (t.getPrice() <= Double.parseDouble(from_field.getText()))
									return true;
								return false; // or true
							}
					);
					menue_tableview.setItems(filtered_list);
				} else if (Double.parseDouble(to_field.getText()) < Double.parseDouble(from_field.getText())) {
					anchor_bar.getChildren().add(error_msg);
					return;
				} else {
					filtered_list.setPredicate(
							t -> {
								if (t.getPrice() <= Double.parseDouble(to_field.getText()) && t.getPrice() >= Double.parseDouble(from_field.getText()))
									return true;
								return false; // or true
							}
					);
					menue_tableview.setItems(filtered_list);
				}
			}else{
				if (to_field.getText().isEmpty() && from_field.getText().isEmpty()) {
					filtered_list.setPredicate(
							t -> {
								if (t.getDominantColor().equalsIgnoreCase(combo_box.getValue()))
									return true;
								return false; // or true
							}
					);
					menue_tableview.setItems(filtered_list);
					return;
				}else if (!to_field.getText().isEmpty() && from_field.getText().isEmpty()) {
					filtered_list.setPredicate(
							t -> {
								if (t.getPrice()<= Double.parseDouble(to_field.getText()) && t.getDominantColor().equalsIgnoreCase(combo_box.getValue()))
									return true;
								return false; // or true
							}
					);
					menue_tableview.setItems(filtered_list);
				} else if (to_field.getText().isEmpty() && !from_field.getText().isEmpty()) {
					filtered_list.setPredicate(
							t -> {
								if (t.getPrice()>= Double.parseDouble(from_field.getText()) && t.getDominantColor().equalsIgnoreCase(combo_box.getValue()))
									return true;
								return false; // or true
							}
					);
					menue_tableview.setItems(filtered_list);
				} else if (Double.parseDouble(to_field.getText()) < Double.parseDouble(from_field.getText())) {
					anchor_bar.getChildren().add(error_msg);
					return;
				} else {
					filtered_list.setPredicate(
							t -> {
								if (t.getPrice() <= Double.parseDouble(to_field.getText()) && t.getPrice() >= Double.parseDouble(from_field.getText()) && t.getDominantColor().equalsIgnoreCase(combo_box.getValue()))
									return true;
								return false; // or true
							}
					);
					menue_tableview.setItems(filtered_list);
				}
			}
		}catch (NumberFormatException e) {
			anchor_bar.getChildren().add(error_msg);
			e.printStackTrace();
		}

	}

	public ObservableList<Item>getCheckedItems(){
		ObservableList<Item> items_to_order = FXCollections.observableArrayList();
		for(Object item : menue_tableview.getItems()){
			Item toItem = (Item)item;
			if(toItem.getChecked().isSelected())
				items_to_order.add(toItem);
				toItem.setChecked(new CheckBox());
		}
		return items_to_order;
	}

	public ObservableList<Item>getCoustomCheckedItems(){
		ObservableList<Item> items_to_order = FXCollections.observableArrayList();
		for(Object item : menue_tableview.getItems()){
			Item toItem = (Item)item;
			if(toItem.getFlowers_number().isSelected())
				items_to_order.add(toItem);
			toItem.setChecked(new CheckBox());
		}

		return items_to_order;
	}

	public void handle_creat_boq(MouseEvent mouseEvent) {
		Item tmp=new Item();
		tmp.getFlowersInItem().addAll(getCoustomCheckedItems());
		selected_items.add(tmp);
	}
}

