package src.lil.client.lilachgui;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import src.lil.client.Instance;
import src.lil.models.Client;
import src.lil.models.Order;

public class OrderHistory extends  LilachController {
    @FXML
    private TableColumn<Order, String> order_id;

    @FXML
    private TableColumn<Order, String> contName;

    @FXML
    private TableColumn<Order, String> recPhone;

    @FXML
    private TableColumn<Order, String>  shippHour;

    @FXML
    private TableColumn<Order, String> ShippDate;

    @FXML
    private TableColumn<Order, String> greating;

    @FXML
    private TableColumn<Order, String> delLoc;

    @FXML
    private TableColumn<Order, String>  price;
    @FXML
    private TableView<Order> OrderTableView;
    @FXML
    private Button cancel_order;


    private Order obj;


    @FXML
    void orderCurr(ActionEvent event) {

    }

    private ObservableList<Order>original_list;

    public void initialize() throws Order.NotFound, SQLException, IOException {

        this.check_logins();
        displayOrders();
        TryToDelete();
    }
    public void displayOrders() throws SQLException, Order.NotFound {
        int id = ((Client) Instance.getCurrentUser()).getUserId();
        List<Order> orderHist = new ArrayList<>();
        orderHist=Order.filterOrders(id);
        order_id.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        contName.setCellValueFactory(new PropertyValueFactory<>("contactName"));
        recPhone.setCellValueFactory(new PropertyValueFactory<>("receiver_phone"));
        shippHour.setCellValueFactory(new PropertyValueFactory<>("ShippingHour"));
        ShippDate.setCellValueFactory(new PropertyValueFactory<>("ShippingDate"));
        greating.setCellValueFactory(new PropertyValueFactory<>("greatingText"));
//        price.setCellValueFactory(new PropertyValueFactory<>("orderCost"));
        OrderTableView.setItems(getOrder(orderHist));
        original_list= OrderTableView.getItems();
    }

    public ObservableList<Order> getOrder(List getFromDB){
        ObservableList<Order> orders = FXCollections.observableArrayList();
        if (Instance.getCurrentUser() == null){
            for(Object order : getFromDB){
                Order toOrder = (Order)order;
            }
        }
        orders.addAll(getFromDB);

        return orders;
    }

    void TryToDelete() throws IOException {

        OrderTableView.setRowFactory(tv -> {
            TableRow<Order> row = new TableRow<>();
            row.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        obj = row.getItem();
                        Gson gson = new Gson();
                        String element = gson.toJson(obj);
                        System.out.println(element);
                        element = "TryToDelete " + element;
                        try {
                            Instance.getClientConsole().get_client().sendToServer(element);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }

            });

            return row;
        });

    }

}
