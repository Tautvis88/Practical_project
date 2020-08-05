package e_shop.service.userservice;

import e_shop.utils.Color;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Scanner;

public class UserLogin {
    private final Scanner scanner = new Scanner(System.in);
    public static String enteredEmail;
    public static String enteredPassword;

    public void runLogInForm() throws IOException, InvalidKeySpecException,
            NoSuchAlgorithmException, InterruptedException {
        System.out.println();
        System.out.println("============================================================================");
        System.out.println("                           PRISIJUNGIMO FORMA                               ");
        System.out.println("============================================================================");
        System.out.print("El. pašto adresas: ");
        enteredEmail = scanner.next();

        UserValidation userValidation = new UserValidation();
        while (!userValidation.doesEmailCorrect(enteredEmail)) {
            System.out.println(Color.RED_BRIGHT + "Neteisingas el. pašto adreso formatas." + Color.RESET);
            System.out.print("El. pašto adresas: ");
            enteredEmail = scanner.next();
        }
        System.out.print("      Slaptažodis: ");
        enteredPassword = scanner.next();
        userValidation.validateUser(enteredEmail, enteredPassword);

    }

    protected String correctUsernameEnding(String username) {
        String usernameEdited;
        if (username.toLowerCase().endsWith("as")) {
            usernameEdited = username.substring(0, username.length() - 1).concat("i").toUpperCase();
        } else if (username.toLowerCase().endsWith("is")) {
            usernameEdited = username.substring(0, username.length() - 1).toUpperCase();
        } else if (username.toLowerCase().endsWith("ius")) {
            usernameEdited = username.substring(0, username.length() - 2).concat("au").toUpperCase();
        } else if (username.toLowerCase().endsWith("ė")) {
            usernameEdited = username.substring(0, username.length() - 1).concat("e").toUpperCase();
        } else {
            usernameEdited = username.toUpperCase();
        }
        return usernameEdited;
    }
}
