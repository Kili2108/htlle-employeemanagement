package at.kili2108.employeemanagement;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.TypedQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;

public class ConnectionManager {

    private static ConnectionManager INSTANCE = null;

    private Connection con;

    private ObservableList<Employee> employees;

    private ConnectionManager() {
    }

    public void login(String username, String pw) throws SQLException, UnauthorizedException {
        con = DriverManager.getConnection("jdbc:mariadb://88.214.56.134:3306/employee_management", "dbmm", ")7yJpPqh}Eo&!!&Wfa?Si/UTXRNTirF");
        try (CallableStatement cs = con.prepareCall("{?=call my_login_admin(?, ?)}");) {
            cs.registerOutParameter(1, Types.BOOLEAN);
            cs.setString(2, username);
            cs.setString(3, pw);
            cs.execute();
            if (!cs.getBoolean(1)) {
                throw new UnauthorizedException("Unauthorized");
            }

            getEmployeesFromDB();
        }
    }

    private void getEmployeesFromDB() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("employee");
        EntityManager em = emf.createEntityManager();

        TypedQuery<Employee> query = em.createQuery("SELECT e FROM Employee e", Employee.class);

        employees = FXCollections.observableArrayList(query.getResultList());
    }

    public void addEmployee(Stage ownerStage) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(ownerStage);
        dialog.setTitle("Add Employee");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(5);

        Label nameLabel = new Label("Name:");
        TextField nameTextField = new TextField();

        Label surnameLabel = new Label("Surname:");
        TextField surnameTextField = new TextField();

        Label birthdateLabel = new Label("Birthdate:");
        DatePicker birthdatePicker = new DatePicker();

        Label emailLabel = new Label("Email:");
        TextField emailTextField = new TextField();

        Label workplaceLabel = new Label("Workplace:");
        TextField workplaceTextField = new TextField();

        gridPane.addRow(0, nameLabel, nameTextField);
        gridPane.addRow(1, surnameLabel, surnameTextField);
        gridPane.addRow(2, birthdateLabel, birthdatePicker);
        gridPane.addRow(3, emailLabel, emailTextField);
        gridPane.addRow(4, workplaceLabel, workplaceTextField);

        dialog.getDialogPane().setContent(gridPane);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == okButton) {
                String name = nameTextField.getText();
                String surname = surnameTextField.getText();
                LocalDate birthdate = birthdatePicker.getValue();
                String email = emailTextField.getText();
                String workplace = workplaceTextField.getText();

                try (PreparedStatement statement = con.prepareStatement(
                        "INSERT INTO Employee (Name, Surname, Birthdate, Email, Workplace) VALUES (?, ?, ?, ?, ?)")) {
                    con.setAutoCommit(false);
                    statement.setString(1, name);
                    statement.setString(2, surname);
                    statement.setDate(3, Date.valueOf(birthdate));
                    statement.setString(4, email);
                    statement.setInt(5, Integer.parseInt(workplace));

                    statement.executeUpdate();
                    con.commit();

                    System.out.println("Employee added successfully.");
                } catch (SQLException e) {
                    System.out.println("Error inserting employee: " + e.getMessage());

                    try {
                        con.rollback();
                        System.out.println("Transaction rolled back.");
                    } catch (SQLException ex) {
                        System.out.println("Error rolling back transaction: " + ex.getMessage());
                    }
                }
            }
            return null;
        });

        dialog.showAndWait();

        getEmployeesFromDB();
    }

    public void updateEmployee(Stage ownerStage, Employee selectedEmployee) {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.initOwner(ownerStage);
        dialog.setTitle("Edit Employee");

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(5);

        Label nameLabel = new Label("Name:");
        TextField nameTextField = new TextField();
        nameTextField.setText(selectedEmployee.getName());

        Label surnameLabel = new Label("Surname:");
        TextField surnameTextField = new TextField();
        surnameTextField.setText(selectedEmployee.getSurname());

        Label birthdateLabel = new Label("Birthdate:");
        DatePicker birthdatePicker = new DatePicker();
        birthdatePicker.setValue(selectedEmployee.getBirthdate());

        Label emailLabel = new Label("Email:");
        TextField emailTextField = new TextField();
        emailTextField.setText(selectedEmployee.getEmail());

        Label workplaceLabel = new Label("Workplace:");
        TextField workplaceTextField = new TextField();
        workplaceTextField.setText(String.valueOf(selectedEmployee.getWorkplace().getId()));

        gridPane.addRow(0, nameLabel, nameTextField);
        gridPane.addRow(1, surnameLabel, surnameTextField);
        gridPane.addRow(2, birthdateLabel, birthdatePicker);
        gridPane.addRow(3, emailLabel, emailTextField);
        gridPane.addRow(4, workplaceLabel, workplaceTextField);

        dialog.getDialogPane().setContent(gridPane);

        ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        dialog.getDialogPane().getButtonTypes().addAll(okButton, cancelButton);

        dialog.setResultConverter(buttonType -> {
            if (buttonType == okButton) {
                String name = nameTextField.getText();
                String surname = surnameTextField.getText();
                LocalDate birthdate = birthdatePicker.getValue();
                String email = emailTextField.getText();
                String workplace = workplaceTextField.getText();

                try (PreparedStatement statement = con.prepareStatement(
                        "UPDATE employee_management.Employee t SET t.Name= ?, t.Surname = ?, t.Birthdate = ?, t.Email = ?, t.Workplace = ? WHERE t.id = ?")) {
                    con.setAutoCommit(false);
                    statement.setString(1, name);
                    statement.setString(2, surname);
                    statement.setDate(3, Date.valueOf(birthdate));
                    statement.setString(4, email);
                    statement.setInt(5, Integer.parseInt(workplace));

                    statement.setInt(6, selectedEmployee.getId());

                    statement.executeUpdate();
                    con.commit();

                    System.out.println("Employee added successfully.");
                } catch (SQLException e) {
                    System.out.println("Error inserting employee: " + e.getMessage());

                    try {
                        con.rollback();
                        System.out.println("Transaction rolled back.");
                    } catch (SQLException ex) {
                        System.out.println("Error rolling back transaction: " + ex.getMessage());
                    }
                }
            }
            return null;
        });

        dialog.showAndWait();

        getEmployeesFromDB();
    }

    public void deleteEmployee(Employee selectedEmployee) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Employee löschen");
        alert.setHeaderText("Employee löschen");
        alert.setContentText(String.format("Möchtest du wirklich den Employee %s %s löschen?", selectedEmployee.getName(), selectedEmployee.getSurname()));

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            try (PreparedStatement statement = con.prepareStatement(
                    "DELETE FROM employee_management.Employee WHERE id = ?;")) {
                con.setAutoCommit(false);
                statement.setInt(1, selectedEmployee.getId());

                statement.executeUpdate();
                con.commit();

                System.out.println("Employee added successfully.");
            } catch (SQLException e) {
                System.out.println("Error inserting employee: " + e.getMessage());

                try {
                    con.rollback();
                    System.out.println("Transaction rolled back.");
                } catch (SQLException ex) {
                    System.out.println("Error rolling back transaction: " + ex.getMessage());
                }
            }
        }

        getEmployeesFromDB();
    }

    public String getAdminAccounts() throws SQLException{
        try (CallableStatement cs = con.prepareCall("{?=call GetAdminAccountsJSON()}")) {
            cs.registerOutParameter(1, Types.VARCHAR);
            cs.execute();

            return cs.getString(1);
        }
    }

    public void createDummyData() throws SQLException {
        try (CallableStatement statement = con.prepareCall("{call Create_Dummy_Data()}")) {
            statement.execute();
        }

        getEmployeesFromDB();
    }

    public void resetAll() throws SQLException {
        try (CallableStatement statement = con.prepareCall("{call Reset_All()}")) {
            statement.execute();
        }

        getEmployeesFromDB();
    }


    public ObservableList<Employee> getEmployees() {
        return employees;
    }

    public static ConnectionManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConnectionManager();
        }
        return INSTANCE;
    }


}
