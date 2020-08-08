package e_shop.service.userservice;

import e_shop.entity.User;
import e_shop.service.FileReadWrite;
import e_shop.service.Menu;
import e_shop.utils.Color;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Scanner;

public class UserValidation {
    private final Scanner scanner = new Scanner(System.in);

    public void validateUser(String enteredEmail, String enteredPassword)
            throws IOException, InvalidKeySpecException, NoSuchAlgorithmException, InterruptedException {

        boolean userNotFound = true;
        FileReadWrite dataFile = new FileReadWrite();
        List<User> userList = dataFile.getUsersFromFile();
        Password password = new Password();
        UserLogin userSignIn = new UserLogin();

        while (userNotFound) {
            for (User user : userList) {
                if (user.getEmail().equalsIgnoreCase(enteredEmail) &&
                        password.validatePassword(enteredPassword, user.getPasswordHash()) &&
                        enteredEmail.equalsIgnoreCase("admin@eshop.lt") &&
                        enteredPassword.equals("Adminas1")) {
                    System.out.println(Color.RED + "JŪS PRISIJUNGĖTE KAIP PUSLAPIO ADMINISTRATORIUS!" + Color.RESET);
                    userNotFound = false;
                    Menu menu = new Menu();
                    menu.showAdminMenu();
                    break;
                } else if (user.getEmail().equalsIgnoreCase(enteredEmail) &&
                        password.validatePassword(enteredPassword, user.getPasswordHash())) {
                    System.out.println(Color.YELLOW + "Sveikiname sėkmingai prisijungus, " +
                            userSignIn.correctUsernameEnding(user.getFirstName()) + "!" + Color.RESET);
                    userNotFound = false;
                    Menu menu = new Menu();
                    menu.showMenu();
                    break;
                }
            }
            if (userNotFound) {
                System.out.println(Color.RED_BRIGHT + "Neteisingas el. pašto adresas ir/arba slaptažodis." + Color.RESET);
                System.out.print("\nEl. pašto adresas: ");
                enteredEmail = scanner.next();
                System.out.print("      Slaptažodis: ");
                enteredPassword = scanner.next();
            }

        }
    }

    public String checkEmail(String newEmail) throws IOException {
        while (true) {
            if (doesEmailExist(newEmail)) {
                System.out.println(Color.RED_BRIGHT + "Vartotojas su tokiu el. pašto adresu jau egzistuoja." + Color.RESET);
                System.out.print("El. paštas: ");
                newEmail = scanner.next();
            } else if (doesEmailCorrect(newEmail)) {
                break;
            } else {
                System.out.println(Color.RED_BRIGHT + "Neteisingas el. pašto adreso formatas." + Color.RESET);
                System.out.print("El. paštas: ");
                newEmail = scanner.next();
            }
        }
        return newEmail;
    }



    public String checkPhoneNumber(String newMobNo) throws IOException {
        while (true) {
            if (doesPhoneNumberExist(newMobNo)) {
                System.out.println(Color.RED_BRIGHT + "Vartotojas su tokiu tel. Nr. jau egzistuoja." + Color.RESET);
                System.out.print("Tel. Nr. +370 6");
                newMobNo = "+3706" + scanner.next();
            } else if (doesPhoneNumberCorrect(newMobNo)) {
                break;
            } else {
                System.out.println(Color.RED_BRIGHT + "Neteisingas telefono numerio formatas." + Color.RESET);
                System.out.print("Tel. Nr. +370 6");
                newMobNo = "+3706" + scanner.next();
            }
        }
        return newMobNo;
    }

    public boolean doesEmailExist(String newEmail) throws IOException {
        boolean emailExists = false;
        FileReadWrite dataFile = new FileReadWrite();
        for (User user : dataFile.getUsersFromFile()) {
            if (user.getEmail().equals(newEmail)) {
                emailExists = true;
                break;
            }
        }
        return emailExists;
    }

    public boolean doesEmailCorrect(String newEmail) {
        boolean emailCorrect = false;
        if (newEmail.matches("^(.+)@(.+)\\.(.+)$")) {
            emailCorrect = true;
        }
        return emailCorrect;
    }

    public boolean doesPhoneNumberExist(String newMobNo) throws IOException {
        boolean mobNoExists = false;
        FileReadWrite filesReadWrite = new FileReadWrite();
        for (User user : filesReadWrite.getUsersFromFile()) {
            if (user.getTel().equals(newMobNo)) {
                mobNoExists = true;
                break;
            }
        }
        return mobNoExists;
    }

    public boolean doesPhoneNumberCorrect(String newMobNo) {
        boolean correctLength = false;
        boolean allDigits = false;
        if (newMobNo.length() == 12) {
            correctLength = true;
        }
        for (int i = 1; i < 12; i++) {
            if (Character.isDigit(newMobNo.charAt(i))) {
                allDigits = true;
            } else {
                allDigits = false;
                break;
            }
        }
        return correctLength && allDigits;
    }
}
