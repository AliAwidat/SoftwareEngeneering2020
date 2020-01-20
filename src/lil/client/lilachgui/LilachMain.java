package src.lil.client.lilachgui;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class LilachMain extends Application{

	
	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		URL url = getClass().getResource("MenuPage.fxml"); 
		AnchorPane pane = FXMLLoader.load(url);
		Scene scene = new Scene(pane);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Welcome to Lilach.");
		primaryStage.show();
	}

}
