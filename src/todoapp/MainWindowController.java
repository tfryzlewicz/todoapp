/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package todoapp;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class MainWindowController implements Initializable {
	public static ObservableList<Tasks> lista = FXCollections.observableArrayList();
	private String user;
	@FXML
	private TableView<Tasks> table;
	@FXML
	private TableColumn<Tasks, String> tabledate;
	@FXML
	private TableColumn<Tasks, String> tabletask;
	@FXML
	public void onAdd(ActionEvent e) throws IOException{
		FXMLLoader loader = new FXMLLoader();  
		Parent root = (Parent) loader.load(getClass().getResource("PopUp.fxml"));  
		Scene scene = new Scene(root);  
		Stage stage = new Stage();
		stage.initModality(Modality.APPLICATION_MODAL);  
		stage.setScene(scene);  
		stage.setTitle("PopUp");  
		stage.show();  
	}
	
	@FXML
	public void onDelete(ActionEvent e){
	    int selectedIndex = table.getSelectionModel().getSelectedIndex();
	    Tasks temp = table.getSelectionModel().getSelectedItem();
	    if (selectedIndex >= 0) {
	        Connection c = null;								//create Class
			Statement stmt = null;
			String dburl = "jdbc:mysql://localhost:3306/todo";
			String dbuser = "root";
			String dbpass = "";
			try {
				Class.forName("com.mysql.jdbc.Driver");
				c = DriverManager.getConnection(dburl, dbuser, dbpass);
				stmt = c.createStatement();
				stmt.executeUpdate("DELETE FROM tasks WHERE task = " + "'"+temp.getTask()+"'" + "and date = " + "'"+temp.getDate()+"'");
				stmt.close();
				c.close();
			} catch (ClassNotFoundException ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			} catch (SQLException ee) {
				// TODO Auto-generated catch block
				ee.printStackTrace();
			}
	        table.getItems().remove(selectedIndex);	        
		}
	    else{
	        Alert alert = new Alert(AlertType.WARNING);
	        alert.setContentText("Please select a task in the table.");
	        alert.showAndWait();
	    }
	}
	
	@Override
    public void initialize(URL url, ResourceBundle rb) {
		Connection c = null;
		Statement stmt = null;
		ResultSet rs;
		String dburl = "jdbc:mysql://localhost:3306/todo";
		String dbuser = "root";
		String dbpass = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			c = DriverManager.getConnection(dburl, dbuser, dbpass);
			stmt = c.createStatement();
			rs = stmt.executeQuery("SELECT * FROM tasks");
			while(rs.next()){
				String date = rs.getString("date");
				String task = rs.getString("task");
				lista.add(new Tasks(date, task));
			}
			rs.close();
			stmt.close();
			c.close();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		tabledate.setCellValueFactory(new PropertyValueFactory<>("date"));					//fix
		tabletask.setCellValueFactory(new PropertyValueFactory<>("task"));
		table.setItems(lista);							
		table.getColumns().addAll(tabledate, tabletask);
    }    
    
    public void getUser(String user){
    	this.user = user;
    }
    
}
