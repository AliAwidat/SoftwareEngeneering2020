package src.lil.client.lilachgui;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class ReportViewerController extends LilachController {


    @FXML
    private ChoiceBox<?> whichSto;

    @FXML
    private ChoiceBox<?> ReportTy;

    @FXML
    private Button viewReport;

    @FXML
    private Text StoreMange;

}
