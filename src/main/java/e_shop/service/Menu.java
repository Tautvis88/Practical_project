package e_shop.service;

import e_shop.service.shopservice.ShopProducts;
import e_shop.service.shopservice.ShoppingCart;
import e_shop.service.userservice.UserLogin;
import e_shop.service.userservice.UserSignUp;
import e_shop.utils.Color;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {
    private final Scanner scanner = new Scanner(System.in);

    public void startTheProgram() throws InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException, IOException {
        System.out.println("============================================================================");
        System.out.println("               SVEIKI ATVYKĘ Į ELEKTRONINĘ PARDUOTUVĘ!                      ");
        System.out.println("============================================================================");
        System.out.print("Ar norite tęsti toliau? (t/n): ");
        String continueOrNot = scanner.next();

        if (continueOrNot.toLowerCase().equals("t")) {
           askToLoginOrSignUp();
        } else {
            System.exit(0);
        }
    }
    public void askToLoginOrSignUp() throws NoSuchAlgorithmException, InvalidKeySpecException, InterruptedException, IOException {
        while (true) {
            try {
                System.out.print("\n0 - Log In, 1 - Sign Up: ");
                int logInOrSignUp = scanner.nextInt();
                if (logInOrSignUp == 0) {
                    UserLogin userLogin = new UserLogin();
                    userLogin.runLogInForm();
                    break;
                } else if (logInOrSignUp == 1) {
                    UserSignUp userSignUp = new UserSignUp();
                    // userSignUp.runSignUpForm();
                    userSignUp.showSignUpForm();
                    break;
                }
            } catch (InputMismatchException exception) {
                System.out.print(Color.RED + "Įveskite skaičių. " + Color.RESET);
                scanner.nextLine();
            }
        }


    }

    public void showMenu() throws IOException, InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException {
        boolean repeat = true;
        while (repeat) {
            System.out.println("-----------------------------------");
            System.out.println("1 - RODYTI PREKIŲ SĄRAŠĄ");
            System.out.println("2 - ĮDĖTI PREKĘ Į KREPŠELĮ");
            System.out.println("3 - IŠIMTI PREKĘ IŠ KREPŠELIO");
            System.out.println("4 - RODYTI KREPŠELĮ");
            System.out.println("5 - BAIGTI APSIPIRKIMĄ");
            System.out.println("6 - VARTOTOJO MENIU");
            System.out.print("Pasirinkimas: ");
            int option = scanner.nextInt();
            System.out.println("-----------------------------------");

            ShoppingCart shoppingCart = new ShoppingCart();
            ShopProducts shopProducts = new ShopProducts();

            switch (option) {
                case 1:
                    shopProducts.showShopProducts();
                    repeat = false;  // o jei nutrinsiu, tai gal vistiek veiks?
                    break;
                case 2:
                    shoppingCart.addProductToCart();
                    repeat = false;
                    break;
                case 3:
                    shoppingCart.removeProductFromShoppingCart();
                    repeat = false;
                    break;
                case 4:
                    shoppingCart.showShoppingCart();
                    repeat = false;
                    break;
                case 5:
                    shoppingCart.endShopping();
                    repeat = false;
                    break;
                case 6:
                    showUserMenu();
                    repeat = false;
                    break;
                default:
                    System.out.println(Color.RED + "Tokio pasirinkimo nėra." + Color.RESET);
                    repeat = true;
                    break;
            }
        }
    }

    public void showAdminMenu() throws IOException, InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException {
        boolean repeat = true;
        while (repeat) {
            System.out.println("-----------------------------------");
            System.out.println("1 - RODYTI REGISTRUOTUS VARTOTOJUS");
            System.out.println("2 - RODYTI PREKIŲ SĄRAŠĄ");
            System.out.println("3 - IŠTRINTI VARTOTOJĄ IŠ DUOMENŲ BAZĖS");
            System.out.println("4 - IŠTRINTI PREKĘ IŠ DUOMENŲ BAZĖS");
            System.out.println("5 - PRIDĖTI PREKĘ IŠ FAILO");
            System.out.println("6 - PRIDĖTI PREKĘ IŠ KONSOLĖS");
            System.out.println("7 - EKSPORTUOTI PREKES Į FAILĄ");
            System.out.print("Pasirinkimas: ");
            int option = scanner.nextInt();
            System.out.println("-----------------------------------");

            ShoppingCart shoppingCart = new ShoppingCart();
            ShopProducts shopProducts = new ShopProducts();

            switch (option) {
                case 1:
                    shopProducts.showShopProducts();
                    repeat = false;
                    break;
                case 2:
                    shoppingCart.addProductToCart();
                    repeat = false;
                    break;
                case 3:
                    shoppingCart.removeProductFromShoppingCart();
                    repeat = false;
                    break;
                case 4:
                    shoppingCart.showShoppingCart();
                    repeat = false;
                    break;
                case 5:
                    shoppingCart.endShopping();
                    repeat = false;
                    break;
                case 6:
                    showUserMenu();
                    repeat = false;
                    break;
                default:
                    System.out.println(Color.RED + "Tokio pasirinkimo nėra." + Color.RESET);
                    repeat = true;
                    break;
            }
        }
    }

    private void showUserMenu() throws IOException, InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException {
        System.out.println("1 - Pakeisti slaptažodį");
        System.out.println("2 - Pakeisti adresą");
        System.out.println("3 - Ištrinti paskyrą");
        System.out.print("Pasirinkimas: ");
        int choice = scanner.nextInt();
        System.out.println("-----------------------------------");

        switch (choice) {
            case 1:
                changePassword();
                break;
            case 2:
                changeAddress();
                break;
            case 3:
                removeUser();
                break;
            default:
                System.out.println("Tokio pasirinkimo nėra.");
                System.out.println("Grįžtame į pagrindinį meniu.");
                System.out.println();
                showMenu();
                break;
        }
    }

    private void removeUser() throws IOException, InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException {
        showMenu();
    }

    private void changeAddress() throws IOException, InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException {
        showMenu();
    }

    private void changePassword() throws IOException, InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException {
        showMenu();
    }
}
