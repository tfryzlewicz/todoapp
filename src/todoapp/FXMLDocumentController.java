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
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author Tomasz
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private TextField usertxt;
    @FXML
    private PasswordField pswrdtxt;
    
    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException {
        if(isOk()){
        	Stage stage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        	FXMLLoader loader = new FXMLLoader();
            Pane root = loader.load(getClass().getResource("MainWindow.fxml").openStream());
            MainWindowController mainWindowController = (MainWindowController)loader.getController();
            mainWindowController.getUser(usertxt.getText());
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("ToDoApp");
            stage.show();    
        }
        else{
            label.setVisible(true);
            label.setText("Invaild Username or Password");
        }
    }
    
    private boolean isOk(){
    	boolean letIn = false;
		System.out.println( "SELECT * FROM login WHERE user= " + "'" + usertxt.getText() + "'" 
	            + " AND password= " + "'" + pswrdtxt.getText() + "'" );
		Connection c = null;
		Statement stmt = null;
		  try {
			  	Class.forName("com.mysql.jdbc.Driver");
	            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/todo", "root", "");
	            c.setAutoCommit(false);
	            
	            System.out.println("Opened database successfully");
	            stmt = c.createStatement();
	            
	            ResultSet rs = stmt.executeQuery( "SELECT * FROM login WHERE user= " + "'" + usertxt.getText() + "'" 
	            + " AND PASSWORD= " + "'" + pswrdtxt.getText() + "'");
	            
	            while ( rs.next() ) {
	                 if (rs.getString("user") != null && rs.getString("PASSWORD") != null) { 
	                     String  username = rs.getString("user");
	                     System.out.println( "user = " + username );
	                     String password = rs.getString("PASSWORD");
	                     System.out.println("PASSWORD = " + password);
	                     letIn = true;
	                 }  
	            }
	            rs.close();
	            stmt.close();
	            c.close();
	            } catch ( Exception e ) {
	                System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	                System.exit(0);
	            }
	            System.out.println("Operation done successfully");
	            return letIn;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
