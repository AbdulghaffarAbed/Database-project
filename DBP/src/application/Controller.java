package application;

import java.sql.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;

import javax.swing.JOptionPane;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

public class Controller implements Initializable {

	private static String dbUsername = "root"; // database username
	private static String dbPassword = "0568706983"; // database password
	private static String URL = "127.0.0.1"; // server location
	private static String port = "3306"; // port that mysql uses
	private static String dbName = "DB_project"; // database on mysql to connect to\
	private static Connection connected;
	private ArrayList<Employee> employeeDataList;
	private ObservableList<Employee> employeeData;
	int selectedIndex = -1;

	// Table identity FXML id

	@FXML // fx:id="table"
	private TableView<Employee> table; // Value injected by FXMLLoader

	@FXML // fx:id="employee_ID"
	private TableColumn<Employee, Integer> employee_ID; // Value injected by FXMLLoader

	@FXML // fx:id="employee_name"
	private TableColumn<Employee, String> employee_name; // Value injected by FXMLLoader

	@FXML // fx:id="dateOfBirth"
	private TableColumn<Employee, LocalDate> dateOfBirth; // Value injected by FXMLLoader

	@FXML // fx:id="jobType"
	private TableColumn<Employee, String> jobType; // Value injected by FXMLLoader

	@FXML // fx:id="experience"
	private TableColumn<Employee, String> experience; // Value injected by FXMLLoader

	@FXML // fx:id="salaryPerHour"
	private TableColumn<Employee, Double> salaryPerHour; // Value injected by FXMLLoader

	@FXML // fx:id="salaryPerMonth"
	private TableColumn<Employee, Double> salaryPerMonth; // Value injected by FXMLLoader

	// TextFields identity FXML id

	@FXML
	private TextField IDTxt;

	@FXML
	private TextField nameTxt;

	@FXML // fx:id="dateOfBirthTxt"
	private DatePicker dateOfBirthTxt; // Value injected by FXMLLoader

	@FXML // fx:id="workPlaceTxt"
	private ComboBox<String> workPlaceTxt; // Value injected by FXMLLoader

	@FXML
	void selectWorkPlaceComboBox(ActionEvent event) {

	}

	@FXML
	private TextField experienceTxt;

	@FXML
	private TextField salaryPerHourTxt;

	@FXML
	private TextField salaryPerMonthTxt;
	
    @FXML
    private Button updateButton;
    
    @FXML
    private ComboBox<String> workPlaceSearch;


	// Create table view and insert Employee data inside it
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		employeeDataList = new ArrayList<>(); // Create array list to save employee data inside it
		ObservableList<String> workPlaceComboBox = FXCollections.observableArrayList("Gypsum", "Photograph",
				"Manufacturing", "Finishing");
		workPlaceTxt.setItems(workPlaceComboBox);
		workPlaceSearch.setItems(workPlaceComboBox);
		
		try {

			getEmployeeData();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			refreshTable();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

	}

	
	//To connect between employee page and clock page
	   @FXML
	    public void SwithchToClock (ActionEvent event) throws IOException{   
			Parent screen2 = FXMLLoader.load(getClass().getResource("Clock.fxml"));
	        Scene scene2 = new Scene(screen2);
	        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();     
	        stage2.setScene(scene2);
	        stage2.show();  
		}
	

	
	

	
	public void refreshTable() throws ClassNotFoundException, SQLException {
		makeConnection();
		// Assign data for all columns
		employee_ID.setCellValueFactory(new PropertyValueFactory<Employee, Integer>("employee_ID"));
		employee_name.setCellValueFactory(new PropertyValueFactory<Employee, String>("employee_name"));
		dateOfBirth.setCellValueFactory(new PropertyValueFactory<Employee, LocalDate>("dateOfBirth"));
		jobType.setCellValueFactory(new PropertyValueFactory<Employee, String>("jobType"));
		experience.setCellValueFactory(new PropertyValueFactory<Employee, String>("experience"));
		salaryPerHour.setCellValueFactory(new PropertyValueFactory<Employee, Double>("salaryPerHour"));
		salaryPerMonth.setCellValueFactory(new PropertyValueFactory<Employee, Double>("salaryPerMonth"));
		employeeData = FXCollections.observableArrayList(employeeDataList);
		// show columns data in the table*/
		table.setItems(employeeData);
	}

	private void makeConnection() throws ClassNotFoundException, SQLException {
		Connect temp = new Connect(dbName, dbPassword, dbUsername, URL, port);
		connected = temp.makeConnection();

	}

	private void getEmployeeData() throws SQLException, ClassNotFoundException {

		makeConnection();
		employeeDataList.clear(); // This statement used when we want to edit on data that will remove old data to
									// insert a new one
		Statement stm = connected.createStatement();
		String sql = "SELECT * FROM employee ORDER BY employee_id ";
		ResultSet res = stm.executeQuery(sql);

		while (res.next()) {
			employeeDataList.add(new Employee(Integer.parseInt(res.getString(1)), res.getString(2),
					LocalDate.parse(res.getString(3)), res.getString(4), res.getString(5),
					Double.parseDouble(res.getString(6)), Double.parseDouble(res.getString(7))));
		}

		res.close();
		stm.close();
		connected.close();
	}

	
	
	
	
	
	
	
	
	
	
	
	
	// Insert data from user into sql employee table
	public void addData() throws ClassNotFoundException, SQLException, InvocationTargetException, NullPointerException,
			RuntimeException {

		// exception to check input process accuracy
		textFieldVisibleTrue();
		if (workPlaceTxt.getValue() != null && salaryPerHourTxt.getText() != null && salaryPerMonthTxt.getText() != null
				&& experienceTxt.getText() != null && nameTxt.getText() != null) {

			try {

				makeConnection();
				Statement stm = connected.createStatement();

				// write sql statement consist data from user
				String sql = "insert into employee(employee_ID,employee_name,date_of_birth,job_type,experience,salary_hourly_rate,salary_per_month) "
						+ "	  values(" + Integer.parseInt(IDTxt.getText()) + ",'" + nameTxt.getText() + "','"
						+ dateOfBirthTxt.getValue() + "','"
						+ workPlaceTxt.getSelectionModel().getSelectedItem().toString() + "','"
						+ experienceTxt.getText() + "'," + Double.parseDouble(salaryPerHourTxt.getText()) + ","
						+ Double.parseDouble(salaryPerMonthTxt.getText()) + ")";

				stm.executeUpdate(sql); // execute sql statement in workbench

				stm.close();
				connected.close();

			} catch (SQLException e) { // If there's an sql error such as enter repeated primary key value
				JOptionPane.showMessageDialog(null, "Error!\nThe entry ID is already exist");
				IDTxt.setText(null);

			} catch (ClassNotFoundException e) {
				e.printStackTrace();

			} catch (NumberFormatException ne) {
				JOptionPane.showMessageDialog(null, "Error!\nPlease check your input values");
			}

			// If ID value contains a letter
		} else {
			JOptionPane.showMessageDialog(null, "Error!\nThere is an empty field");
		}

		getEmployeeData();
		refreshTable();
		ResetDataFields();
	}

	
	
	
	
	
	
	
	
	
	
	// To insert default value in date Text field
	Date date = new Date();
	LocalDate localDate = convertDateToLocalDateUsingInstant(date);

	//To convert created date to Local Date
	public static LocalDate convertDateToLocalDateUsingInstant(Date date) {
		return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	}

	
	
	
	
	
	
	
	public void deleteData() throws ClassNotFoundException, SQLException {

		nameTxt.setText("x");
		dateOfBirthTxt.setValue(localDate);
		workPlaceTxt.setValue("Manufacturing");
		experienceTxt.setText("x");
		salaryPerHourTxt.setText("0");
		salaryPerMonthTxt.setText("0");

		nameTxt.setVisible(false);
		experienceTxt.setVisible(false);
		salaryPerHourTxt.setVisible(false);
		salaryPerMonthTxt.setVisible(false);
		workPlaceTxt.setVisible(false);
		dateOfBirthTxt.setVisible(false);
		boolean found = employeeDataList.contains(IDTxt.getText());
		// System.out.println(found);

		try {
			makeConnection();
			Statement stm = connected.createStatement();

			// write sql statement consist data from user
			String sql = "delete from employee where employee_ID = " + Integer.parseInt(IDTxt.getText());

			stm.executeUpdate(sql); // execute sql statement in workbench
			stm.close();
			connected.close();

		} catch (SQLException e) { // If there's an sql error such as enter repeated primary key value

			JOptionPane.showMessageDialog(null, "Error!\nThe entry ID number is already exist"); // show window that
																									// tell user
																									// about
																									// the error
			IDTxt.setText(null);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NumberFormatException ne) {
			JOptionPane.showMessageDialog(null, "Error!\nPlease check your input values");

		}
		/*
		 * if (found == false) JOptionPane.showMessageDialog(null,
		 * "No data deleted the entry id is not exist in the table");
		 */

		getEmployeeData();
		refreshTable();
		ResetDataFields();
		textFieldVisibleTrue();

	}

	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * used to select row of data from table help to update data for this row
	 */

	int tempID = 0;

	public void selectData(MouseEvent event) { // action when mouse click on data row in the table
		updateButton.setDisable(false);
		textFieldVisibleTrue();
		selectedIndex = table.getSelectionModel().getSelectedIndex(); // assign selected row index value to select index
																		// variable

		if (selectedIndex <= -1) {
			return;
		}

		tempID = employee_ID.getCellData(selectedIndex);
		// if user select line then the data in these line will be shown in text fields
		IDTxt.setText(employee_ID.getCellData(selectedIndex).toString());
		nameTxt.setText(employee_name.getCellData(selectedIndex).toString());
		dateOfBirthTxt.setValue(dateOfBirth.getCellData(selectedIndex));
		workPlaceTxt.setValue(jobType.getCellData(selectedIndex).toString());
		experienceTxt.setText(experience.getCellData(selectedIndex).toString());
		salaryPerHourTxt.setText(salaryPerHour.getCellData(selectedIndex).toString());
		salaryPerMonthTxt.setText(employee_ID.getCellData(selectedIndex).toString());

	}

	// to edit data in the text fields
	public void editData() throws ClassNotFoundException, SQLException {
		
		
		
		
		textFieldVisibleTrue();

		// store the new data in string variables
		if (workPlaceTxt.getValue() != null && salaryPerHourTxt.getText() != null && salaryPerMonthTxt.getText() != null
				&& experienceTxt.getText() != null && nameTxt.getText() != null) {
			try {
				makeConnection();
				Statement stm = connected.createStatement();
				String idValue = IDTxt.getText();
				String nameValue = nameTxt.getText();
				LocalDate dateOfBirthValue = dateOfBirthTxt.getValue();
				String workPlaceValue = workPlaceTxt.getSelectionModel().getSelectedItem().toString();
				String experienceValue = experienceTxt.getText();
				String salaryPerHourValue = salaryPerHourTxt.getText();
				String salaryPerMonthValue = salaryPerMonthTxt.getText();

				// update sql data by the input data from user
				String sql = "update employee  Set  employee_ID=" + Integer.parseInt(idValue) + ",employee_name=' "
						+ nameValue + "',date_of_birth='" + dateOfBirthValue + "',job_type='" + workPlaceValue
						+ "',experience='" + experienceValue + "',salary_hourly_rate="
						+ Double.parseDouble(salaryPerHourValue) + ",salary_per_month="
						+ Double.parseDouble(salaryPerMonthValue) + "where employee_ID=" + tempID;
				stm.executeUpdate(sql); // execute sql statement in workbench
				stm.close();
			} catch (SQLException e) { // If there's an sql error such as enter repeated primary key value

				JOptionPane.showMessageDialog(null, "Error!\nThe entry ID is already exist");
				IDTxt.setText(null);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (NumberFormatException ne) {
				JOptionPane.showMessageDialog(null, "Error!\nPlease check your input values");
			}

		} else
			JOptionPane.showMessageDialog(null, "Error!\nThere is an empty field");

		connected.close();
		getEmployeeData();
		refreshTable();
		ResetDataFields();
		updateButton.setDisable(true);
	}

	
	
	
	public void sectionSearch() throws ClassNotFoundException, SQLException {
		makeConnection();
		employeeDataList.clear();
		Statement stm = connected.createStatement();
		String sql = "select *\r\n" + 
				"from employee e \r\n" + 
				"where e.job_type='"+workPlaceSearch.getValue()+"'\r\n" + 
				"order by e.employee_id ;";
		ResultSet res = stm.executeQuery(sql);

		while (res.next()) {
			employeeDataList.add(new Employee(Integer.parseInt(res.getString(1)), res.getString(2),
					LocalDate.parse(res.getString(3)), res.getString(4), res.getString(5),
					Double.parseDouble(res.getString(6)), Double.parseDouble(res.getString(7))));
		}
		
		refreshTable();
		res.close();
		stm.close();
		connected.close();
	}
	
	
	public void refrechDataTable(ActionEvent event) throws ClassNotFoundException, SQLException {
		employeeDataList = new ArrayList<>(); // Create array list to save employee data inside it
		ObservableList<String> workPlaceComboBox = FXCollections.observableArrayList("Gypsum", "Photograph",
				"Manufacturing", "Finishing");
		workPlaceTxt.setItems(workPlaceComboBox);
		workPlaceSearch.setItems(workPlaceComboBox);
		
		try {

			getEmployeeData();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			refreshTable();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	

	
	

	void ResetDataFields() {
		IDTxt.setText(null);
		nameTxt.setText(null);
		dateOfBirthTxt.setValue(null);
		workPlaceTxt.setValue(null);
		experienceTxt.setText(null);
		salaryPerHourTxt.setText(null);
		salaryPerMonthTxt.setText(null);
	}

	// Make all fields visible to the user on the screen
	void textFieldVisibleTrue() {

		nameTxt.setVisible(true);
		experienceTxt.setVisible(true);
		salaryPerHourTxt.setVisible(true);
		salaryPerMonthTxt.setVisible(true);
		workPlaceTxt.setVisible(true);
		dateOfBirthTxt.setVisible(true);

	}
	
	
}
