package e_shop.main;

import e_shop.service.userservice.UserService;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class JavaFX extends Application {

    private UserService userService = new UserService();

    public static void runJavaFx() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        constructMainView(primaryStage);
    }

    public void constructMainView(Stage primaryStage) throws FileNotFoundException {

        // Creating an image
        Image image = new Image(new FileInputStream("src/main/resources/files/eshop.jpg"));

        // Setting the image view
        ImageView imageView = new ImageView(image);

        // Setting the position of the image
        //imageView.setX(200);
        //imageView.setY(100);

        // Setting the fit height and width of the image view
        //imageView.setFitHeight(500);
        imageView.setFitWidth(300);

        // Setting the preserve ratio of the image view
        imageView.setPreserveRatio(true);

        Button signUpButton = new Button("SIGN UP");
        signUpButton.setPrefSize(100, 50);
        signUpButton.setFont(Font.font(16));
        signUpButton.setTranslateX(100);

        Button logInButton = new Button("LOG IN");
        logInButton.setPrefSize(100, 50);
        logInButton.setFont(Font.font(16));
        logInButton.setTranslateX(100);

        GridPane mainGridPane = new GridPane();
        mainGridPane.setHgap(50);
        mainGridPane.setVgap(35);
        mainGridPane.setAlignment(Pos.TOP_CENTER);
        mainGridPane.add(imageView, 0, 1);
        mainGridPane.add(signUpButton, 0, 2);
        mainGridPane.add(logInButton, 0, 3);

        Scene mainScene = new Scene(mainGridPane);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("MENU");
        primaryStage.setHeight(400);
        primaryStage.setWidth(500);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();

        // setOnMouseClicked() method defines what happens when the button is clicked.
        signUpButton.setOnMouseClicked(event -> constructSignUpFormView(primaryStage));
        logInButton.setOnMouseClicked(event -> constructLogInFormView(primaryStage));
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
            try {
                constructMainView(primaryStage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

        logInButton.setOnMouseClicked(event -> {

        });

    }

    public void constructSignUpFormView(Stage primaryStage) {
        // Labels of Sign Up form fields
        Label firstNameLabel = new Label("First name: ");
        Label lastNameLabel = new Label("Last name: ");
        Label emailLabel = new Label("* Email: ");
        Label phoneNumberLabel = new Label("* Phone number: ");
        Label addressLabel = new Label("Address: ");
        Label countryLabel = new Label("Country: ");
        Label passwordLabel = new Label("* Password: ");
        Label confirmPasswordLabel = new Label("* Confirm password: ");
        Label mandatoryFieldsLabel = new Label("* mandatory fields");

        // Email field labels
        Label emailIsMandatoryLabel = new Label("Email is mandatory.");
              emailIsMandatoryLabel.setTextFill(Color.RED);
        Label emailFormatInvalidLabel = new Label("Email format is invalid.");
              emailFormatInvalidLabel.setTextFill(Color.RED);
        Label emailExistsLabel = new Label("Email exists.");
              emailExistsLabel.setTextFill(Color.RED);

        // Phone Number field labels
        Label phoneNumberIsMandatoryLabel = new Label("Phone number is mandatory.");
              phoneNumberIsMandatoryLabel.setTextFill(Color.RED);
        Label phoneNumberFormatInvalidLabel = new Label("Phone number format is invalid.");
              phoneNumberFormatInvalidLabel.setTextFill(Color.RED);
        Label phoneNumberExistsLabel = new Label("Phone number exists.");
              phoneNumberExistsLabel.setTextFill(Color.RED);

        // Password field labels
        Label passwordIsMandatoryLabel = new Label("Password is mandatory.");
              passwordIsMandatoryLabel.setTextFill(Color.RED);
        Label passwordConfirmIsMandatoryLabel = new Label("Password confirm is mandatory.");
              passwordConfirmIsMandatoryLabel.setTextFill(Color.RED);
        Label passwordsDoNotMatchLabel = new Label("Password do not match.");
              passwordsDoNotMatchLabel.setTextFill(Color.RED);
        Label passwordComplexityLabel = new Label("Password does not meet the complexity requirements.");
              passwordComplexityLabel.setTextFill(Color.RED);

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
        scene.getStylesheets().add(getClass().getResource("/files/textFieldRedBorder.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("SIGN UP FORM");
        primaryStage.setHeight(500);
        primaryStage.setWidth(750);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();

        // setOnMouseClicked() method defines what happens when the button is clicked.
        addUserButton.setOnMouseClicked(event -> {
            userService.addUser(emailIsMandatoryLabel, emailFormatInvalidLabel, emailExistsLabel,
                                phoneNumberIsMandatoryLabel, phoneNumberFormatInvalidLabel, phoneNumberExistsLabel,
                                passwordIsMandatoryLabel, passwordConfirmIsMandatoryLabel, passwordsDoNotMatchLabel,
                                passwordComplexityLabel,
                                firstNameTextField, lastNameTextField, emailTextField, phoneNumberTextField,
                                addressTextField, countryComboBox, passwordTextField, confirmPasswordTextField,
                                gridPane);
        });

        backToMainMenuButton.setOnMouseClicked(event -> {
            try {
                constructMainView(primaryStage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        });

    }
}
