package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class ClockController implements Initializable {

	private static String dbUsername = "root";
	private static String dbPassword = "0568706983";
	private static String URL = "127.0.0.1";
	private static String port = "3306";
	private static String dbName = "DB_project";
	private static Connection connected;
	public  ArrayList<Clock> clockData;
	private ObservableList<Clock> clockDataList;

	// Table identity FXML id

	@FXML // fx:id="TableClock"
	private TableView<Clock> TableClock; // Value injected by FXMLLoader

	@FXML // fx:id="Employee_ID_fx"
	private TableColumn<Clock, Integer> Employee_ID_fx; // Value injected by FXMLLoader

	@FXML // fx:id="Employee_name_fx"
	private TableColumn<Clock, String> Employee_name_fx; // Value injected by FXMLLoader

	@FXML // fx:id="Work_on_fx"
	private TableColumn<Clock, String> Work_on_fx; // Value injected by FXMLLoader

	@FXML // fx:id="Entry_Time_fx"
	private TableColumn<Clock, String> Entry_Time_fx; // Value injected by FXMLLoader

	@FXML // fx:id="Out_Time_fx"
	private TableColumn<Clock, String> Out_Time_fx; // Value injected by FXMLLoader

	@FXML // fx:id="Entry_Date_fx"
	private TableColumn<Clock, LocalDate> Entry_Date_fx; // Value injected by FXMLLoader

	// TextFields identity FXML id

	@FXML // fx:id="Employee_id_txt"
	private TextField Employee_id_txt; // Value injected by FXMLLoader

	@FXML // fx:id="Entry_Time_txt"
	private TextField Entry_Time_txt; // Value injected by FXMLLoader

	@FXML // fx:id="Out_Time_txt"
	private TextField Out_Time_txt; // Value injected by FXMLLoader


    @FXML // fx:id="Entry_Date_txt"
    private DatePicker Entry_Date_txt; // Value injected by FXMLLoader

	// Add button ID

	@FXML // fx:id="Add_bt"
	private Button Add_bt; // Value injected by FXMLLoader

	
	
	
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// Employee_name_fx,Work_on_fx,Entry_Time_fx,Out_Time_fx,Entry_Date_fx
		// employee_name,job_type,entry_time,out_time,entry_date
		clockData = new ArrayList<>(); // Create array list to save employee data inside it

		try {
			getClockData();
			refreshTable();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	

	}

	
	
	
	   @FXML
	    public void SwithchToClockReport (ActionEvent event) throws IOException{   
			Parent screen2 = FXMLLoader.load(getClass().getResource("ClockReport.fxml"));
	        Scene scene2 = new Scene(screen2);
	        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();     
	        stage2.setScene(scene2);
	        stage2.show();  
		}
	
	   @FXML
	    public void SwithchToEmployee (ActionEvent event) throws IOException{   
			Parent screen2 = FXMLLoader.load(getClass().getResource("window.fxml"));
	        Scene scene2 = new Scene(screen2);
	        Stage stage2 = (Stage) ((Node) event.getSource()).getScene().getWindow();     
	        stage2.setScene(scene2);
	        stage2.show();  
		}
	
	
	
	public void refreshTable() throws ClassNotFoundException, SQLException {
		makeConnection();
		// Assign data for all columns
		Employee_ID_fx.setCellValueFactory(new PropertyValueFactory<Clock, Integer>("employee_ID"));
		Employee_name_fx.setCellValueFactory(new PropertyValueFactory<Clock, String>("employee_name"));
		Work_on_fx.setCellValueFactory(new PropertyValueFactory<Clock, String>("job_type"));
		Entry_Time_fx.setCellValueFactory(new PropertyValueFactory<Clock, String>("entry_time"));
		Out_Time_fx.setCellValueFactory(new PropertyValueFactory<Clock, String>("out_time"));
		Entry_Date_fx.setCellValueFactory(new PropertyValueFactory<Clock, LocalDate>("entry_date"));
		clockDataList = FXCollections.observableArrayList(clockData);
		// show columns data in the table*/
		TableClock.setItems(clockDataList);
	}

	
	
	
	private void makeConnection() throws ClassNotFoundException, SQLException {
		Connect temp = new Connect(dbName, dbPassword, dbUsername, URL, port);
		connected = temp.makeConnection();
	}

	
	
	
	private void getClockData() throws SQLException, ClassNotFoundException {
		
		makeConnection();
		clockData.clear(); // This statement used when we want to edit on data that will remove old data to
							// insert a new one
		
		Statement stm = connected.createStatement();
		String sql = "select C.employee_ID,E.employee_name,E.job_type,C.entry_time,C.out_time,entry_date\r\n"
				+ "From clock C,employee E\r\n" + "where E.employee_ID=C.employee_ID;" ;
		ResultSet res = stm.executeQuery(sql);

		while (res.next()) {
			clockData.add(new Clock(Integer.parseInt(res.getString(1)), res.getString(2), res.getString(3),
					res.getString(4), res.getString(5), LocalDate.parse(res.getString(6))));
		}

		res.close();
		stm.close();
		connected.close();
	}

	public void addClockData() throws ClassNotFoundException, SQLException {
		// exception to check input process accuracy

				if (Employee_id_txt.getText().matches("[0-9]+") && Employee_id_txt.getText()!=null  &&  Entry_Time_txt.getText()!=null
						&& Out_Time_txt.getText()!=null ) {
					try {
						makeConnection();
						Statement stm = connected.createStatement();

						// write sql statement consist data from user
						String sql = "insert into clock(employee_ID,entry_date,entry_time,out_time)values("+Employee_id_txt.getText()+",'"+Entry_Date_txt.getValue()+"','"
								+Entry_Time_txt.getText()+"','"+Out_Time_txt.getText()+"')";

						stm.executeUpdate(sql); // execute sql statement in workbench
						

						stm.close();
						connected.close();

					} catch (SQLException e) { // If there's an sql error such as enter repeated primary key value

						JOptionPane.showMessageDialog(null, "Error!\nThe entry ID is already exist"); 
						Employee_id_txt.setText(null);
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					}catch (NumberFormatException ne) {
						JOptionPane.showMessageDialog(null, "Error!\nSalary fields must consist numerically value");

					}
					
						// If ID value contains a letter
				} else if(Employee_id_txt.getText().matches("[0-9]+")==false)
					JOptionPane.showMessageDialog(null, "Error!\nID must has numerically value only");
				else
					JOptionPane.showMessageDialog(null, "Error!\nThere is an empty field");
				getClockData();
				refreshTable();
				ResetClockTextFields();

	}
	
	
	void ResetClockTextFields() {
		Employee_id_txt.setText(null);
		Entry_Date_txt.setValue(null);
		Entry_Time_txt.setText(null);
		Out_Time_txt.setText(null);
		
	}
}
