package src.lil.client.lilachgui;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import src.lil.client.Instance;
import src.lil.models.Item;
import src.lil.models.User;

public class ManageStoreCont extends LilachController {
	@FXML
	TableView<Item> menue_tableview;
	
	@FXML
	TableColumn<Item, String> id_cul;
	
	@FXML
	TableColumn<Item, String> name_cul;
	
	@FXML
	TableColumn<Item, String> price_cul;

	@FXML
	TableColumn<Item, String> checkbox_cul;
	
	@FXML
	private Button update_btn;
	
	@FXML
	private Button update_all_btn;
	
	private ObservableList<Item>original_list;

    @FXML
    void handle_update_all(MouseEvent event) {

    }

    @FXML
    void handle_update_selected(MouseEvent event) {

    }
    
    @FXML
    public void initialize() {
    	this.check_logins();
    	display_items();
    }
    
    @SuppressWarnings("unchecked")
	public ObservableList<Item>getItems(List<Item> getFromDB){
		ObservableList<Item> items = FXCollections.observableArrayList();
		
			
			for(Item item : getFromDB){
				System.out.println(item.getType());
				Item toItem = item;
				toItem.checked.setVisible(false);
				finish_order_label.setVisible(false);
			}
		
		items.addAll(getFromDB);

		return items;
	}
    public void display_items() {
    	List<Item> items;
		items=Item.filterItems(" false",Integer.parseInt(((User)Instance.getCurrentUser()).getStoreId()));
		id_cul.setCellValueFactory(new PropertyValueFactory<>("id"));
		name_cul.setCellValueFactory(new PropertyValueFactory<>("type"));
		price_cul.setCellValueFactory(new PropertyValueFactory<>("price"));
		checkbox_cul.setCellValueFactory(new PropertyValueFactory<>("checked"));
		menue_tableview.setItems(getItems(items));
		original_list= menue_tableview.getItems();
		
    }
}
