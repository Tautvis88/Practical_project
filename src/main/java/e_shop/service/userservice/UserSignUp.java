package e_shop.service.userservice;

import e_shop.entity.User;
import e_shop.repository.UserRepository;
import e_shop.repository.UserRepositoryImpl;
import e_shop.service.FileReadWrite;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Scanner;

public class UserSignUp {
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
        PasswordService password = new PasswordService();
        String strongPassword = password.requirementsValidation(userPassword);
        String strongPasswordHash = password.generatePasswordHash(strongPassword);

        int id = calcUserNo(filesReadWrite.getUsersFilePath());

        User newUser = new User(String.valueOf(id), name, surname, validEmail, validMobNo, address,
                                strongPassword, strongPasswordHash);

        // Naujo user'io įrašymas į failą
        filesReadWrite.writeUserToTextFile(filesReadWrite.getUsersFilePath(), newUser);

        // Naujo user'io įrašymas į duomenų bazę
        UserRepository userRepository = new UserRepositoryImpl();
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

}
