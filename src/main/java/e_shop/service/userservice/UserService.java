package e_shop.service.userservice;

import e_shop.main.JavaFX;
import javafx.css.PseudoClass;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.List;


public class UserService {

    public static boolean allMandatoryFieldsCorrect = true;

    private List<Label> listOfWarnings = JavaFX.getListOfWarnings();

    public void addUser(Label emailIsMandatoryLabel, Label emailFormatInvalidLabel, Label emailExistsLabel,
                        Label phoneNumberIsMandatoryLabel, Label phoneNumberFormatInvalidLabel,
                        Label phoneNumberExistsLabel, Label passwordIsMandatoryLabel,
                        Label passwordConfirmIsMandatoryLabel, Label passwordsDoNotMatchLabel,
                        Label passwordComplexityLabel,
                        TextField firstNameTextField, TextField lastNameTextField,
                        TextField emailTextField, TextField phoneNumberTextField, TextField addressTextField,
                        ComboBox<String> countryComboBox, TextField passwordTextField,
                        TextField confirmPasswordTextField, GridPane gridPane) {

        UserValidation userValidation = new UserValidation();

        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String address = addressTextField.getText();
        String country = countryComboBox.getValue();
        String password = passwordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();

        allMandatoryFieldsCorrect = true;

        removeAllWarnings(gridPane);

        //emailTextField.getStyleClass().remove("error");

        if (email.isEmpty()) {
            gridPane.add(emailIsMandatoryLabel, 3, 3);
            emailTextField.getStyleClass().add("error");
            allMandatoryFieldsCorrect = false;
        } else {
            userValidation.validateEmail(email, gridPane, emailFormatInvalidLabel, emailExistsLabel, emailTextField);
        }

        //phoneNumberTextField.getStyleClass().remove("error");

        if (phoneNumber.isEmpty() || phoneNumber.equals("+370")) {
            gridPane.add(phoneNumberIsMandatoryLabel, 3, 4);
            phoneNumberTextField.getStyleClass().add("error");
            allMandatoryFieldsCorrect = false;
        } else {
            userValidation.validatePhoneNumber(phoneNumber, gridPane, phoneNumberFormatInvalidLabel,
                    phoneNumberExistsLabel, phoneNumberTextField);
        }

        passwordTextField.getStyleClass().remove("error");
        if (password.isEmpty()) {
            gridPane.add(passwordIsMandatoryLabel, 3,7);
            passwordTextField.getStyleClass().add("error");
            allMandatoryFieldsCorrect = false;
        }


        confirmPasswordTextField.getStyleClass().remove("error");
        if (confirmPassword.isEmpty()) {
            gridPane.add(passwordConfirmIsMandatoryLabel, 3,8);
            confirmPasswordTextField.getStyleClass().add("error");
            allMandatoryFieldsCorrect = false;
        }


        if (!confirmPassword.isEmpty() && !password.equals(confirmPassword)) {
            gridPane.add(passwordsDoNotMatchLabel, 3, 8);
            confirmPasswordTextField.getStyleClass().add("error");
            allMandatoryFieldsCorrect = false;
        }

        PasswordService passwordService = new PasswordService();
        if (!password.isEmpty() && !passwordService.meetsRequirements(password)) {
            gridPane.add(passwordComplexityLabel, 3, 7);
            passwordTextField.getStyleClass().add("error");
            allMandatoryFieldsCorrect = false;
        }

        Label userWasAddedLabel = new Label("User was added!");
        userWasAddedLabel.setTextFill(Color.GREEN);
        userWasAddedLabel.setTranslateX(100);
        if (allMandatoryFieldsCorrect) {
            gridPane.add(userWasAddedLabel, 2, 10);
        }

    }

    private void removeAllWarnings(GridPane gridPane) {
        for (Label warning : listOfWarnings) {
            gridPane.getChildren().remove(warning);
        }
    }


}
