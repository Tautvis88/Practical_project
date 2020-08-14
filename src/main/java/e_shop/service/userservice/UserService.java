package e_shop.service.userservice;

import e_shop.utils.TextFieldUtils;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class UserService {

    public void addUser(Label thisFieldIsMandatoryLabel, Label thisFieldIsMandatoryLabel2, Label invalidEmailLabel, Label emailExistsLabel, Label invalidPhoneNumberLabel, Label phoneNumberExistsLabel, TextField firstNameTextField, TextField lastNameTextField, TextField emailTextField, TextField phoneNumberTextField, TextField addressTextField, ComboBox<String> countryComboBox, TextField passwordTextField, TextField confirmPasswordTextField, GridPane gridPane) {
        UserValidation userValidation = new UserValidation();

        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String email = emailTextField.getText();
        String phoneNumber = phoneNumberTextField.getText();
        String address = addressTextField.getText();
        String country = countryComboBox.getValue();
        String password = passwordTextField.getText();
        String confirmPassword = confirmPasswordTextField.getText();

        thisFieldIsMandatoryLabel.setTextFill(Color.RED);
        thisFieldIsMandatoryLabel2.setTextFill(Color.RED);

        gridPane.getChildren().remove(thisFieldIsMandatoryLabel);
        if (TextFieldUtils.messageFieldIsMandatoryVisible) {

        }
        if (email.isEmpty()) {
            gridPane.add(thisFieldIsMandatoryLabel, 3, 3);
            TextFieldUtils.messageFieldIsMandatoryVisible = true;
        } else {
            userValidation.validateEmail(email, gridPane, invalidEmailLabel, emailExistsLabel);
        }


        if (TextFieldUtils.messageFieldIsMandatoryVisible2) {
            gridPane.getChildren().remove(thisFieldIsMandatoryLabel2);

        }
        if (phoneNumber.isEmpty() || phoneNumber.equals("+370")) {
            gridPane.add(thisFieldIsMandatoryLabel2, 3, 4);
            TextFieldUtils.messageFieldIsMandatoryVisible2 = true;
        } else {
            userValidation.validatePhoneNumber(phoneNumber, gridPane, invalidPhoneNumberLabel, phoneNumberExistsLabel);
        }

        System.out.println("Entered name was: " + firstName);
        System.out.println("Entered email was: " + email);
        System.out.println("Selected country was: " + country);

        Label userWasAddedLabel = new Label("User was added!");
        userWasAddedLabel.setTextFill(Color.GREEN);
        gridPane.add(userWasAddedLabel, 2, 11);
    }
}
