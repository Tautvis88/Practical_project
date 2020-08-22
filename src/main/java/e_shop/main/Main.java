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


        JavaFX.runJavaFx();


        // Menu menu = new Menu();
        // menu.startTheProgram();
        // menu.showMenu();

    }
}

/*
UserService.class -> addUser() metode žiauriai daug parametrų - gal galima juos kaip nors iškelti ar iš viso tą
metodą atgal įkelti į JavaFX klasę?

Gal koks nors vienas metodas, kuris ištrintų tuos raudonus labelius prie mandatory fieldų ir panaikintų red border,
nes dabar žiauriai kartojasi visur tas remove("error).
 */