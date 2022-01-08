package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javax.swing.JOptionPane;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ClockReportController implements Initializable {
	private static ArrayList<ClockReport> ClockRep;

	private static String dbUsername = "root"; // database username
	private static String dbPassword = "0568706983"; // database password
	private static String URL = "127.0.0.1"; // server location
	private static String port = "3306"; // port that mysql uses
	private static String dbName = "DB_project"; // database on mysql to connect to\
	private static Connection connected;

	private static ArrayList<ClockReport> ClkData;

	@FXML
	private TableView<ClockReport> Emp_Table_fx;
	@FXML
	private TableColumn<ClockReport, Integer> EmpID_fx;
	@FXML
	private TableColumn<ClockReport, String> Emp_Name_fx, Section_fx, HW_fx;

	@FXML
	private TableColumn<ClockReport, Double> HourkyRate_fx, Salary_fx;

	@FXML
	private Button Show, Back;
	@FXML
	private TextField StartDate, EndDate;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	@FXML
	public void ShowData(ActionEvent event) {
		ClkData = new ArrayList<ClockReport>();
		String From, To;
		From = StartDate.getText();
		To = EndDate.getText();
		if (From.isEmpty() || To.isEmpty()) {
			JOptionPane.showMessageDialog(null, "Please Fill the two text field with dates");

		} else {
			if (isValidDate(From) && isValidDate(To)) {
				ClkData = new ClockReportController().prepareClockReport(From, To);
				// employee_ID,employee_name,job_type,time_sum, hourly_rate,salary
				EmpID_fx.setCellValueFactory(new PropertyValueFactory<ClockReport, Integer>("employee_ID"));
				Emp_Name_fx.setCellValueFactory(new PropertyValueFactory<ClockReport, String>("employee_name"));
				Section_fx.setCellValueFactory(new PropertyValueFactory<ClockReport, String>("job_type"));
				HW_fx.setCellValueFactory(new PropertyValueFactory<ClockReport, String>("time_sum"));
				HourkyRate_fx.setCellValueFactory(new PropertyValueFactory<ClockReport, Double>("hourly_rate"));
				Salary_fx.setCellValueFactory(new PropertyValueFactory<ClockReport, Double>("salary"));

				Emp_Table_fx.setItems(FXCollections.observableArrayList(ClkData));

			} else
				JOptionPane.showMessageDialog(null, "Please Fill the two text field with dates");

		}
	}

	@FXML
	public void SwithchToSection(ActionEvent event) throws IOException {
		Parent screen2 = FXMLLoader.load(getClass().getResource("Clock.fxml"));
		Scene scene2 = new Scene(screen2);
		Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage2.setScene(scene2);
		stage2.show();
	}

	private void makeConnection() throws ClassNotFoundException, SQLException {
		Connect temp = new Connect(dbName, dbPassword, dbUsername, URL, port);
		connected = temp.makeConnection();

	}

	public ArrayList<ClockReport> clkReport(int emp, String From, String To)
			throws ClassNotFoundException, SQLException {
		makeConnection();

		ClockRep = new ArrayList<ClockReport>();
		String SQL = "select C.employee_ID,E.employee_name,E.job_type,timediff(C.out_time,C.entry_time),E.salary_hourly_rate\r\n"
				+ "From clock C,employee E\r\n" + "where E.employee_ID=C.employee_ID and E.employee_ID=" + emp
				+ " and C.entry_date>'" + From + "' and C.entry_date<'" + To + "';";
		Statement statement = connected.createStatement();
		ResultSet rs = statement.executeQuery(SQL);

		while (rs.next()) {
			ClockRep.add(new ClockReport(Integer.parseInt(rs.getString(1)), rs.getString(2), rs.getString(3),
					rs.getString(4), Double.parseDouble(rs.getString(5))));
		}

		rs.close();
		statement.close();
		connected.close();
		return ClockRep;
	}

	public ArrayList<ClockReport> prepareClockReport(String From, String To) {
		int numOfEmp = 0;

		// get the num of model ids
		try {
			numOfEmp = CheckEmpIDNum();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int[] empArr = new int[numOfEmp];

		// creating and make array of model ids
		try {
			empArr = CheckEmpID();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// the final list to be printed
		ArrayList<ClockReport> FinalModel = new ArrayList<ClockReport>();

		for (int i = 0; i < empArr.length; i++) {
			ClockReport FinishedData = new ClockReport();
			String FinalHours = "00:00:00";

			// array list that will get the info about each model
			// all info in this array list have the same model id
			ArrayList<ClockReport> TempModel = new ArrayList<ClockReport>();
			try {
				TempModel = clkReport(empArr[i], From, To);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (TempModel.isEmpty()) {
				continue;
			} else {

				while (!TempModel.isEmpty()) {
					ClockReport dataFromArr = new ClockReport();
					dataFromArr = TempModel.remove(TempModel.size() - 1);
					FinishedData.setEmployee_ID(dataFromArr.getEmployee_ID());
					FinishedData.setEmployee_name(dataFromArr.getEmployee_name());
					FinishedData.setHourly_rate(dataFromArr.getHourly_rate());
					FinishedData.setJob_type(dataFromArr.getJob_type());
					FinalHours = CalculateNumOfHours(FinalHours, dataFromArr.getTime_sum());

				}

				FinishedData.setTime_sum(FinalHours);
				String[] time = new String[3];
				time = FinalHours.split(":");
				Double h = 0.0;
				h += Double.parseDouble(time[0]);
				h += (Double.parseDouble(time[1]) / 60);
				FinishedData.setSalary(h * FinishedData.getHourly_rate());
				FinalModel.add(FinishedData);
				// the end of the for loop
			}
		}
		
		/*for(int i=0; i < FinalModel.size(); i++){
            System.out.println( FinalModel.get(i) );
        }*/
		return FinalModel;
	}

	public int[] CheckEmpID() throws ClassNotFoundException, SQLException {

		int numberOfID = 0;
		numberOfID = new ClockReportController().CheckEmpIDNum();
		int[] arr = new int[numberOfID];
		String SQL = "select E.employee_ID\r\n" + "from employee E;";
		Statement statement = connected.createStatement();
		ResultSet rs = statement.executeQuery(SQL);
		int i = 0;
		while (rs.next()) {
			arr[i] = Integer.parseInt(rs.getString(1));
			i++;
		}
		rs.close();
		statement.close();
		connected.close();

		return arr;
	}

	// this is to check if the emp number to create array of that size
	public int CheckEmpIDNum() throws ClassNotFoundException, SQLException {
		makeConnection();
		int numberOfID = 0;
		String SQL = "select count(E.employee_ID)\r\n" + "from employee E;";
		Statement statement = connected.createStatement();
		ResultSet rs = statement.executeQuery(SQL);
		while (rs.next()) {
			numberOfID = Integer.parseInt(rs.getString(1));
		}
		rs.close();
		statement.close();
		return numberOfID;

	}

	public static String CalculateNumOfHours(String First, String Second) {
		int minute1 = 0, minute2 = 0, hour1 = 0, hour2 = 0, hourSum = 0, minuteSum = 0;
		String[] firstTime = new String[3];
		String[] secondTime = new String[3];
		firstTime = First.split(":");
		secondTime = Second.split(":");
		hour1 = Integer.parseInt(firstTime[0]);
		minute1 = Integer.parseInt(firstTime[1]);
		hour2 = Integer.parseInt(secondTime[0]);
		minute2 = Integer.parseInt(secondTime[1]);
		hourSum = hour1 + hour2;
		minuteSum = minute1 + minute2;
		minuteSum += (hourSum * 60);
		hourSum = minuteSum / 60;
		minuteSum = minuteSum % 60;
		String Hour = "", Minute = "";
		if (hourSum < 10) {
			Hour = "0" + hourSum;
		} else
			Hour = "" + hourSum;

		if (minuteSum < 10) {
			Minute = "0" + minuteSum;
		} else
			Minute = "" + minuteSum;

		String Last = Hour + ":" + Minute + ":00";

		return Last;

	}

	public static boolean isValidDate(String DateToCheck) {
		if (DateToCheck == null) {
			return false;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		try {
			dateFormat.parse(DateToCheck.trim());
		} catch (ParseException e) {
			return false;
		}
		return true;
	}

}