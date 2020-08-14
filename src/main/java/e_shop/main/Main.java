package e_shop.main;

import e_shop.service.Menu;
import e_shop.service.userservice.UserSignUp;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Main {
    public static void main(String[] args) throws InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        // Context context = new Context(new FileReader());
        // context.executeStrategy();

        JavaFX javaFX = new JavaFX();
        javaFX.runJavaFx();

        Menu menu = new Menu();
        // menu.startTheProgram();
        // menu.showMenu();

    }
}
