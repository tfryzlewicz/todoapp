package todoapp;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class PopUpController implements Initializable {

	@FXML 
	private TextField inputdate;
	@FXML
	private TextField inputtask;
	@FXML
	private Button add;
	@FXML
	private Label label1;
	@FXML
	private Label label2;
	@FXML
	public void handleActionEvent(ActionEvent event){
		Tasks task = new Tasks(inputdate.getText(), inputtask.getText());
		MainWindowController.lista.add(task);
		Connection c = null;
		Statement stmt = null;
		int rs;
		String dburl = "jdbc:mysql://localhost:3306/todo";
		String dbuser = "root";
		String dbpass = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection(dburl, dbuser, dbpass);
			stmt = c.createStatement();
			rs = stmt.executeUpdate("INSERT INTO tasks (date,task) VALUES"+"("+"'"+inputdate.getText()+"'"+", "+"'"+inputtask.getText()+"'"+");");
			System.out.println("Dodano");
		stmt.close();
		c.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Stage stage = (Stage) add.getScene().getWindow();
		stage.close();
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
	}

}
