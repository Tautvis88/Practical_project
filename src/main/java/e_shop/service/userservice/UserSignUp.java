package e_shop.service.userservice;

import e_shop.entity.User;
import e_shop.repository.UserRepositoryImpl;
import e_shop.service.FileReadWrite;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Scanner;

public class UserSignUp extends Application {
    private final Scanner scanner = new Scanner(System.in);

    public void runSignUpForm() throws IOException, InterruptedException, InvalidKeySpecException,
            NoSuchAlgorithmException {
        System.out.println("============================================================================");
        System.out.println("                         REGISTRACIJOS FORMA                                ");
        System.out.println("============================================================================");
        System.out.println("Įveskite paskyros sukūrimui reikalingus duomenis. ");

        UserValidation userValidation = new UserValidation();
        FileReadWrite filesReadWrite = new FileReadWrite();
        UserLogin userSignIn = new UserLogin();

        System.out.print("\nVardas: ");
        String name = scanner.next();

        System.out.print("Pavardė: ");
        String surname = scanner.next();

        System.out.print("El. paštas: ");
        String email = scanner.next();
        String validEmail = userValidation.checkEmail(email);

        System.out.print("Mob. Nr. +370 6");
        String phoneNumber = "+3706" + scanner.next();
        String validMobNo = userValidation.checkPhoneNumber(phoneNumber);

        System.out.print("Adresas: ");
        scanner.skip("((?<!(?>\\R))\\s)*");
        String address = scanner.nextLine();

        System.out.print("Slaptažodis: ");
        String userPassword = scanner.next();
        Password password = new Password();
        String strongPassword = password.requirementsValidation(userPassword);
        String strongPasswordHash = password.generatePasswordHash(strongPassword);

        int id = calcUserNo(filesReadWrite.getUsersFilePath());

        User newUser = new User(String.valueOf(id), name, surname, validEmail, validMobNo, address,
                                strongPassword, strongPasswordHash);

        // Naujo user'io įrašymas į failą
        filesReadWrite.writeUserToTextFile(filesReadWrite.getUsersFilePath(), newUser);

        // Naujo user'io įrašymas į duomenų bazę
        UserRepositoryImpl userRepository = new UserRepositoryImpl();
        userRepository.saveUserToDatabase(newUser);

        System.out.print("\nREGISTRACIJA SĖKMINGA. Ar norite prisijungti? (t/n): ");
        String requestSignIn = scanner.next();
        if (requestSignIn.equalsIgnoreCase("t")) {
            userSignIn.runLogInForm();
        } else {
            System.exit(0);
        }
    }

    private int calcUserNo(Path usersFilePath) throws IOException {
        List<String> registeredUsers = Files.readAllLines(usersFilePath);
        return registeredUsers.size() - 2 + 1;    // minus 2 eilut. header'io, plius 1 naujam vartotojo nr. sukurti
    }

    public void showSignUpForm() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Label firstNameLabel = new Label("First name: ");
        Label lastNameLabel = new Label("Last name: ");
        Label emailLabel = new Label("Email: ");
        Label phoneNumberLabel = new Label("Phone number: +370");
        Label addressLabel = new Label("Address: ");
        Label countryLabel = new Label("Country: ");
        Label passwordLabel = new Label("Password: ");
        Label confirmPasswordLabel = new Label("Confirm password: ");

        Label invalidEmailLabel = new Label("Invalid email format!");
        Label invalidPhoneNumberLabel = new Label("Invalid phone number format!");
        Label emailExistsLabel = new Label("Email exists!");
        Label phoneNumberExistsLabel = new Label("Phone number exists!");

        TextField firstNameTextField = new TextField();
        TextField lastNameTextField = new TextField();
        TextField emailTextField = new TextField();
        emailTextField.setPrefWidth(300);
        TextField phoneNumberTextField = new TextField("+370");
        TextField addressTextField = new TextField();

        ComboBox<String> countryComboBox = new ComboBox<>();
        countryComboBox.getItems().addAll("Lithuania", "Latvia", "Estonia", "Poland", "Russia");

        TextField passwordTextField = new TextField();
        TextField confirmPasswordTextField = new TextField();

        Button validateEmail = new Button("Validate");
        Button validatePhoneNumber = new Button("Validate");
        Button validatePassword = new Button("Validate");
        Button addUserButton = new Button("Add user");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.TOP_CENTER);

        gridPane.add(firstNameLabel, 0, 1);
        gridPane.add(firstNameTextField, 1, 1);
        gridPane.add(lastNameLabel, 0, 2);
        gridPane.add(lastNameTextField, 1, 2);
        gridPane.add(emailLabel, 0, 3);
        gridPane.add(emailTextField, 1, 3);
        gridPane.add(validateEmail,2,3);
        gridPane.add(phoneNumberLabel, 0, 4);
        gridPane.add(phoneNumberTextField, 1, 4);
        gridPane.add(validatePhoneNumber, 2,4);
        gridPane.add(addressLabel, 0, 5);
        gridPane.add(addressTextField, 1, 5);
        gridPane.add(countryLabel, 0, 6);
        gridPane.add(countryComboBox, 1, 6);
        gridPane.add(passwordLabel, 0, 7);
        gridPane.add(passwordTextField, 1, 7);
        gridPane.add(confirmPasswordLabel, 0, 8);
        gridPane.add(confirmPasswordTextField, 1, 8);
        gridPane.add(validatePassword, 2, 8);
        gridPane.add(addUserButton, 1, 9);

        // setOnMouseClicked() method defines what happens when the button is clicked.
        UserValidation userValidation = new UserValidation();

        validateEmail.setOnMouseClicked(event -> {
            String email = emailTextField.getText();
            gridPane.getChildren().remove(invalidEmailLabel);   // kaip optimizuoti, kad remove if exists
            gridPane.getChildren().remove(emailExistsLabel);
            if (!userValidation.doesEmailCorrect(email)) {
                invalidEmailLabel.setTextFill(Color.RED);
                gridPane.add(invalidEmailLabel, 3, 3);
            } else {
                try {
                    if (userValidation.doesEmailExist(email)) {
                        emailExistsLabel.setTextFill(Color.RED);
                        gridPane.add(emailExistsLabel, 3, 3);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        validatePhoneNumber.setOnMouseClicked(event -> {
            String phoneNumber = phoneNumberTextField.getText();
            gridPane.getChildren().remove(invalidPhoneNumberLabel);
            gridPane.getChildren().remove(phoneNumberExistsLabel);
            if (!userValidation.doesPhoneNumberCorrect(phoneNumber)) {
                invalidPhoneNumberLabel.setTextFill(Color.RED);
                gridPane.add(invalidPhoneNumberLabel, 3, 4);
            } else {
                try {
                    if (userValidation.doesPhoneNumberExist(phoneNumber)) {
                        phoneNumberExistsLabel.setTextFill(Color.RED);
                        gridPane.add(phoneNumberExistsLabel, 3, 4);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        addUserButton.setOnMouseClicked(event -> {
            // try {
            //     if () {
            //
            //     }
            //
            //     System.out.println("Add user button WAS CLICKED!");
            // } catch (IOException e) {
            //     e.printStackTrace();
            // }

            String name = lastNameTextField.getText();
            System.out.println("Entered name was: " + name);

            String email = emailTextField.getText();
            System.out.println("Entered email was: " + email);

            String country = countryComboBox.getValue();
            System.out.println("Selected country was: " + country);

            Label niceLabel = new Label("User was added!");
            gridPane.add(niceLabel, 0, 9);
        });

        Scene scene = new Scene(gridPane);
        primaryStage.setScene(scene);

        primaryStage.setTitle("SIGN UP FORM");
        primaryStage.setHeight(500);
        primaryStage.setWidth(800);

        primaryStage.setAlwaysOnTop(true);
        primaryStage.show();
    }
}
