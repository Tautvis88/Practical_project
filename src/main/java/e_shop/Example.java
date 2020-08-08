package e_shop;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Example extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Label nameLabel = new Label("First name: ");
        Label emailLabel = new Label("Email: ");
        Label countryLabel = new Label("Country: ");


        TextField nameTextField = new TextField();
        nameTextField.setMaxWidth(200);
        TextField emailTextField = new TextField();
        emailTextField.setMaxWidth(200);

        ComboBox<String> countryComboBox = new ComboBox<>();
        countryComboBox.getItems().addAll("Lithuania", "Russia", "Poland");

        Button addUserButton = new Button("Add user");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.TOP_CENTER);

        gridPane.add(nameLabel, 0, 1);
        gridPane.add(nameTextField, 1, 1);
        gridPane.add(emailLabel, 0, 2);
        gridPane.add(emailTextField, 1, 2);
        gridPane.add(countryLabel, 0, 3);
        gridPane.add(countryComboBox, 1, 3);
        gridPane.add(addUserButton, 1, 4);

        // setOnMouseClicked() method defines what happens when the button is clicked.
        addUserButton.setOnMouseClicked(event -> {
            System.out.println("Add user button WAS CLICKED!");

            String name = nameTextField.getText();
            System.out.println("Entered name was: " + name);

            String email = emailTextField.getText();
            System.out.println("Entered email was: " + email);

            String country = countryComboBox.getValue();
            System.out.println("Selected country was: " + country);

            Label niceLabel = new Label("Nice!");
            gridPane.add(niceLabel, 0, 4);
        });

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);

        primaryStage.setTitle("SIGN UP FORM");
        primaryStage.setHeight(500);
        primaryStage.setWidth(500);

        primaryStage.show();

    }
}
