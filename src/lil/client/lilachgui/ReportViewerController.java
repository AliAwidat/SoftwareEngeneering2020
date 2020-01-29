package src.lil.client.lilachgui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import src.lil.models.Report;

import java.util.List;


public class ReportViewerController extends LilachController{

	@FXML
	public Button report_search_btn;
	@FXML
	public  TableView<Report> reports_table;
	@FXML
	private DatePicker date_picker;

	@FXML
	private TableColumn<Report, String> report_name_cul;

	@FXML
	private TableColumn<Report, String> report_type_cul;

	@FXML
	private TableColumn<Report, Button> view_content_cul;

	@FXML
	private TableColumn<Report, CheckBox> compare_check_cul;

	@FXML
	private Button compare_btn;

	@FXML
	private LineChart<String, Double> Income_chart;

	@FXML
	private LineChart<String, Integer> complains_chart;

	@FXML
	private Button clear_charts_btn;

	@FXML
	public void initialize() {

		this.check_logins();
		displayReports();


	}

	public void displayReports(){
		List<Report> reportsList ;
		reportsList= Report.getReports();
		report_name_cul.setCellValueFactory(new PropertyValueFactory<>("report_name"));
		report_type_cul.setCellValueFactory(new PropertyValueFactory<>("report_type"));
		view_content_cul.setCellValueFactory(new PropertyValueFactory<>("view_report"));
		compare_check_cul.setCellValueFactory(new PropertyValueFactory<>("to_compare"));
		reports_table.setItems(getObservedReports(reportsList));
	}

	private ObservableList<Report> getObservedReports(List reportsList) {
			javafx.collections.ObservableList<Report> reports = FXCollections.observableArrayList();
//			for(Object report : reportsList){
////				Report toReport = (Report)report;
////				toReport.view_report.setOnAction(e -> {
//////					To-Do set on action
////				});
//
//		}
			reports.addAll(reportsList);

			return reports;
		}


	private String getMonth(Report rep) {
		if (rep.getReport_name().contains("01/01/"))
			return "Jan";
		if (rep.getReport_name().contains("02"))
			return "Feb";
		if (rep.getReport_name().contains("03"))
			return "Mar";
		if (rep.getReport_name().contains("04"))
			return "Apr";
		if (rep.getReport_name().contains("05"))
			return "May";
		if (rep.getReport_name().contains("06"))
			return "Jun";
		if (rep.getReport_name().contains("07"))
			return "Jul";
		if (rep.getReport_name().contains("08"))
			return "Aug";
		if (rep.getReport_name().contains("09"))
			return "Sept";
		if (rep.getReport_name().contains("10"))
			return "Oct";
		if (rep.getReport_name().contains("11"))
			return "Nov";
		if (rep.getReport_name().contains("12"))
			return "Dec";
		return "";
	}

	@FXML
	public void handle_compare_btn(ActionEvent event) {
		XYChart.Series<String,Double> series = new XYChart.Series<>();
		for(Report rep : reports_table.getItems()) {
			System.out.println(getMonth(rep)+rep.getMonthlyIncome());
			series.getData().add(new XYChart.Data<String,Double>(getMonth(rep),rep.getMonthlyIncome()));
		}
		Income_chart.getData().addAll(series);
	}



	@FXML
	public void handle_report_search(ActionEvent event) {

	}

	@FXML
	public void handle_clear_charts(ActionEvent mouseEvent) {
		Income_chart.getData().clear();
	}

}
