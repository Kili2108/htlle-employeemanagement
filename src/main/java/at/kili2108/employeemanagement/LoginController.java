package at.kili2108.employeemanagement;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

import java.io.IOException;
import java.sql.SQLException;

public class LoginController {
    public TextField userNameTextfield;
    public PasswordField pwTextField;
    public Button loginButton;

    public void login(MouseEvent actionEvent) {

        try {
            ConnectionManager.getInstance().login(userNameTextfield.getText(), pwTextField.getText());
        } catch (SQLException e) {
            e.printStackTrace();
            return;
        } catch (UnauthorizedException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Benutzername oder Passwort falsch");
            alert.showAndWait();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("main-view.fxml"));
            Scene scene = new Scene(loader.load());
            Stage primaryStage = new Stage();
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
