package e_shop.main;

import e_shop.service.userservice.UserService;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class JavaFX extends Application {

    private UserService userService = new UserService();

    public void runJavaFx() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        constructMainView(primaryStage);
    }

    public void constructMainView(Stage primaryStage) {
        Button signUpButton = new Button("SIGN UP");
        signUpButton.setPrefSize(100, 50);
        signUpButton.setFont(Font.font(16));
        Button logInButton = new Button("LOG IN");
        logInButton.setPrefSize(100, 50);
        logInButton.setFont(Font.font(16));

        GridPane mainGridPane = new GridPane();
        mainGridPane.setHgap(50);
        mainGridPane.setVgap(50);
        mainGridPane.setAlignment(Pos.TOP_CENTER);
        mainGridPane.add(signUpButton, 0, 1);
        mainGridPane.add(logInButton, 0, 2);
        Scene mainScene = new Scene(mainGridPane);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("MENU");
        primaryStage.setHeight(300);
        primaryStage.setWidth(300);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();

        // setOnMouseClicked() method defines what happens when the button is clicked.
        signUpButton.setOnMouseClicked(event -> {
            constructSignUpFormView(primaryStage);
        });
        logInButton.setOnMouseClicked(event -> {
            constructLogInFormView(primaryStage);
        });
    }

    public void constructLogInFormView(Stage primaryStage) {
        Label emailLabel = new Label("Email: ");
        Label passwordLabel = new Label("Password: ");

        TextField emailTextField = new TextField();
        emailTextField.setPrefWidth(300);
        TextField passwordTextField = new TextField();

        Button logInButton = new Button("Log In");
        Button backToMainMenuButton = new Button("Back to MENU");
        GridPane gridPane = new GridPane();

        gridPane.setHgap(20);
        gridPane.setVgap(20);
        gridPane.setAlignment(Pos.TOP_CENTER);

        gridPane.add(emailLabel, 1, 1);
        gridPane.add(emailTextField, 2, 1);
        gridPane.add(passwordLabel, 1, 2);
        gridPane.add(passwordTextField, 2, 2);

        gridPane.add(logInButton, 2, 3);
        gridPane.add(backToMainMenuButton, 3, 3);

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("LOG IN FORM");
        primaryStage.setHeight(300);
        primaryStage.setWidth(550);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();

        backToMainMenuButton.setOnMouseClicked(event -> {
            constructMainView(primaryStage);
        });

        logInButton.setOnMouseClicked(event -> {

        });

    }

    public void constructSignUpFormView(Stage primaryStage) {
        Label firstNameLabel = new Label("First name: ");
        Label lastNameLabel = new Label("Last name: ");
        Label emailLabel = new Label("* Email: ");
        Label phoneNumberLabel = new Label("* Phone number: ");
        Label addressLabel = new Label("Address: ");
        Label countryLabel = new Label("Country: ");
        Label passwordLabel = new Label("* Password: ");
        Label confirmPasswordLabel = new Label("* Confirm password: ");
        Label mandatoryFieldsLabel = new Label("* mandatory fields");

        Label thisFieldIsMandatoryLabel = new Label("This field is mandatory.");
        Label thisFieldIsMandatoryLabel2 = new Label("This field is mandatory.");
        Label invalidEmailLabel = new Label("Invalid email format!");
        Label emailExistsLabel = new Label("Email exists!");
        Label invalidPhoneNumberLabel = new Label("Invalid phone number format!");
        Label phoneNumberExistsLabel = new Label("Phone number exists!");

        TextField firstNameTextField = new TextField();
        firstNameTextField.setPrefWidth(300);
        TextField lastNameTextField = new TextField();
        TextField emailTextField = new TextField();
        TextField phoneNumberTextField = new TextField("+370");
        TextField addressTextField = new TextField();

        ComboBox<String> countryComboBox = new ComboBox<>();
        countryComboBox.getItems().addAll("Lithuania", "Latvia", "Estonia", "Poland", "Russia");
        countryComboBox.setMinWidth(300);

        TextField passwordTextField = new TextField();
        TextField confirmPasswordTextField = new TextField();

        Button addUserButton = new Button("Add user");
        Button backToMainMenuButton = new Button("Back to MENU");
        GridPane gridPane = new GridPane();

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.TOP_LEFT);

        gridPane.add(firstNameLabel, 1, 1);
        gridPane.add(firstNameTextField, 2, 1);
        gridPane.add(lastNameLabel, 1, 2);
        gridPane.add(lastNameTextField, 2, 2);
        gridPane.add(emailLabel, 1, 3);
        gridPane.add(emailTextField, 2, 3);
        gridPane.add(phoneNumberLabel, 1, 4);
        gridPane.add(phoneNumberTextField, 2, 4);
        gridPane.add(addressLabel, 1, 5);
        gridPane.add(addressTextField, 2, 5);
        gridPane.add(countryLabel, 1, 6);
        gridPane.add(countryComboBox, 2, 6);
        gridPane.add(passwordLabel, 1, 7);
        gridPane.add(passwordTextField, 2, 7);
        gridPane.add(confirmPasswordLabel, 1, 8);
        gridPane.add(confirmPasswordTextField, 2, 8);
        gridPane.add(mandatoryFieldsLabel, 2, 9);
        gridPane.add(addUserButton, 2, 10);
        gridPane.add(backToMainMenuButton, 3,10);

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("SIGN UP FORM");
        primaryStage.setHeight(500);
        primaryStage.setWidth(700);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();

        // setOnMouseClicked() method defines what happens when the button is clicked.
        addUserButton.setOnMouseClicked(event -> {
            userService.addUser(thisFieldIsMandatoryLabel, thisFieldIsMandatoryLabel2, invalidEmailLabel, emailExistsLabel, invalidPhoneNumberLabel, phoneNumberExistsLabel, firstNameTextField, lastNameTextField, emailTextField, phoneNumberTextField, addressTextField, countryComboBox, passwordTextField, confirmPasswordTextField, gridPane);
        });

        backToMainMenuButton.setOnMouseClicked(event -> {
            constructMainView(primaryStage);
        });

    }
}
