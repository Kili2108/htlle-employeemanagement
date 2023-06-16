package at.kili2108.employeemanagement;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainController {
    @FXML
    public TableView<Employee> employeeTableView;

    @FXML
    public FXCollections data;

    @FXML
    private TableColumn<Employee, String> nameColumn;

    @FXML
    private TableColumn<Employee, String> surnameColumn;

    @FXML
    private TableColumn<Employee, Date> birthdateColumn;

    @FXML
    private TableColumn<Employee, String> emailColumn;

    @FXML
    private TableColumn<Employee, Workplace> workplaceColumn;

    private boolean userLoggedIn = false;

    public void initialize() {

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        birthdateColumn.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        workplaceColumn.setCellValueFactory(new PropertyValueFactory<>("workplace"));

        employeeTableView.setItems(ConnectionManager.getInstance().getEmployees());

    }

    public void logout(ActionEvent actionEvent) {
        Stage stage = (Stage) employeeTableView.getScene().getWindow();
        stage.hide();
    }

    public void exit(ActionEvent actionEvent) {
        System.exit(0);
    }

    public void addEmployee(ActionEvent actionEvent) {
        Stage stage = (Stage) employeeTableView.getScene().getWindow();
        ConnectionManager.getInstance().addEmployee(stage);

        employeeTableView.setItems(ConnectionManager.getInstance().getEmployees());
    }

    public void editEmployee(ActionEvent actionEvent) {
        Stage stage = (Stage) employeeTableView.getScene().getWindow();
        ConnectionManager.getInstance().updateEmployee(stage, employeeTableView.getSelectionModel().getSelectedItem());

        employeeTableView.setItems(ConnectionManager.getInstance().getEmployees());
    }

    public void deleteEmployee(ActionEvent actionEvent) {
        ConnectionManager.getInstance().deleteEmployee(employeeTableView.getSelectionModel().getSelectedItem());

        employeeTableView.setItems(ConnectionManager.getInstance().getEmployees());
    }

    public void resetAll(ActionEvent actionEvent) {
        try {
            ConnectionManager.getInstance().resetAll();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Ein Fehler ist aufgetreten!");
            alert.showAndWait();
            return;
        }

        employeeTableView.setItems(ConnectionManager.getInstance().getEmployees());
    }

    public void createDummyData(ActionEvent actionEvent) {
        try {
            ConnectionManager.getInstance().createDummyData();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Ein Fehler ist aufgetreten!");
            alert.showAndWait();
            return;
        }

        employeeTableView.setItems(ConnectionManager.getInstance().getEmployees());
    }

    public void getAdminAccounts(ActionEvent actionEvent) {

        List<AdminAccount> accounts = new ArrayList<>();

        try {
            JSONArray adminAccounts = new JSONArray(ConnectionManager.getInstance().getAdminAccounts());

            for (int i = 0; i < adminAccounts.length(); i++) {
                JSONObject account = adminAccounts.getJSONObject(i);
                accounts.add(new AdminAccount(account.getInt("id"), account.getString("username"), account.getString("password")));
            }

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Ein Fehler ist aufgetreten!");
            alert.showAndWait();
        }

        for (AdminAccount account : accounts) {
            System.out.println(account);
        }
    }
}
