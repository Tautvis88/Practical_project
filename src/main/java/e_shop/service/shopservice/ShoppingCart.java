package e_shop.service.shopservice;

import e_shop.entity.Product;
import e_shop.entity.User;
import e_shop.service.FileReadWrite;
import e_shop.service.Menu;
import e_shop.service.userservice.PasswordService;
import e_shop.service.userservice.UserLogin;
import e_shop.utils.Color;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

public class ShoppingCart {
    private final Scanner scanner = new Scanner(System.in);

    public static FileReadWrite fileReadWrite = new FileReadWrite();
    public static List<Product> shoppingCart = new LinkedList<>();
    public static List<Product> productList;
    private Menu menu = new Menu();

    static {
        try {
            productList = fileReadWrite.getProductsFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ShoppingCart() {
    }

    private void printCartHeader() {
        for (int i = 0; i < 3; i++) {
            System.out.println(fileReadWrite.getStringLinesFromTextFile().get(i));
        }
    }
    private void printCartAmount() {
        int totalQuantity = 0;
        double totalAmount = 0.0;
        for (Product product : shoppingCart) {
            totalQuantity += product.getQuantity();
            totalAmount += product.getQuantity() * product.getPrice();
        }
        System.out.println("--------------------------------------------------------------------------------------------------------");
        System.out.printf("%72s", "IŠ VISO:");
        System.out.printf("%7s", totalQuantity);
        System.out.printf("%17s", totalAmount);
        System.out.println(" eur");
    }

    public void addProductToCart() throws IOException, InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException {
        int productId = specifyTheProduct();
        int quantity = specifyTheQuantity(productId);
        if (isProductAlreadyInCart(productId)) {
            for (Product product : shoppingCart) {
                if (product.getProductId() == productId) {
                    System.out.println(Color.GREEN_BRIGHT +
                            "Tokia prekė jau yra prekių krepšelyje, todėl jos kiekis bus padidintas iki " +
                            (product.getQuantity() + quantity) + " vnt." + Color.RESET);
                    product.setQuantity(product.getQuantity() + quantity);
                    break;
                }
            }
        } else {
            for (Product product : productList) {
                if (product.getProductId() == productId) {
                    Product boughtProduct = new Product(productId, product.getProductCode(), product.getProductName(), quantity, product.getPrice());
                    shoppingCart.add(boughtProduct);
                    decreaseProductQuantityInShop(productId, quantity);
                    System.out.println(Color.GREEN_BRIGHT + "Prekė sėkmingai pridėta į krepšelį." + Color.RESET);
                    break;
                }
            }
        }
        menu.showMenu();
    }

    public void removeProductFromShoppingCart() throws IOException, InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException {
        if (shoppingCart.isEmpty()) {
            System.out.println(Color.RED + "PREKIŲ KREPŠELIS YRA TUŠČIAS." + Color.RESET);
        } else {
            System.out.print("Enter product ID: ");
            int productId = scanner.nextInt();
            boolean productNotFound = true;
            for (Product product : shoppingCart) {
                if (product.getProductId() == productId && product.getQuantity() > 1) {
                    productNotFound = false;
                    System.out.println("You have " + product.getQuantity() + " products with ID " + productId + ".");
                    System.out.print("How many products do you want to remove? ");
                    int productsQuantityToRemove = scanner.nextInt();
                    if (productsQuantityToRemove >= product.getQuantity()) {
                        shoppingCart.remove(product);
                        increaseProductQuantityInShop(productId, product.getQuantity());
                        System.out.println(Color.GREEN_BRIGHT + "All products with ID " + productId +
                                " successfully removed." + Color.RESET);
                    } else {
                        int newDecreasedQuantity = product.getQuantity() - productsQuantityToRemove;
                        product.setQuantity(newDecreasedQuantity);
                        increaseProductQuantityInShop(productId, productsQuantityToRemove);
                        System.out.println(Color.GREEN_BRIGHT + "The quantity of products with ID " + productId +
                                " has decreased to " + newDecreasedQuantity + "." + Color.RESET);
                    }
                    break;
                } else if (product.getProductId() == productId && product.getQuantity() == 1) {
                    productNotFound = false;
                    shoppingCart.remove(product);
                    increaseProductQuantityInShop(productId, 1);
                    System.out.println(Color.GREEN_BRIGHT + "Product with ID " + productId + " successfully removed." +
                            Color.RESET);
                    break;
                }
            }
            if (productNotFound) {
                System.out.println(Color.RED + "PRODUCT NOT FOUND." + Color.RESET);
            }

        }
        menu.showMenu();
    }

    private void decreaseProductQuantityInShop(int productId, int quantityPurchased) {
        for (Product product : productList) {
            if (product.getProductId() == productId) {
                int newQuantity = product.getQuantity() - quantityPurchased;
                product.setQuantity(newQuantity);
                break;
            }
        }
    }

    private void increaseProductQuantityInShop(int productId, int quantityReturned) {
        for (Product product : productList) {
            if (product.getProductId() == productId) {
                int newQuantity = product.getQuantity() + quantityReturned;
                product.setQuantity(newQuantity);
                break;
            }
        }
    }

    private boolean isProductAlreadyInCart(int itemNo) {
        for (Product product: shoppingCart) {
            if (product.getProductId() == itemNo) {
                return true;
            }
        }
        return false;
    }

    public void showShoppingCart() throws IOException, InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException {
        if (shoppingCart.isEmpty()) {
            System.out.println(Color.RED + "PREKIŲ KREPŠELIS YRA TUŠČIAS." + Color.RESET);
        } else {
            printShoppingCart();
        }

        menu.showMenu();
    }

    public void printShoppingCart() {
        printCartHeader();
        shoppingCart.sort(Comparator.comparingInt(Product::getProductId));
        for (Product product : shoppingCart) {
            System.out.println(product.toString());
        }
        printCartAmount();
    }

    private int specifyTheProduct() {
        while (true) {
            try {
                System.out.print("Norimos prekės NR.: ");
                int productId = scanner.nextInt();
                if (productId < 0) {
                    System.out.println(Color.RED + "Prekės numeris turi būti sveikasis teigiamas skaičius." +
                            Color.RESET);
                } else if (productId > productList.size()) {
                    System.out.println(Color.RED + "Tokios prekės sąraše nėra. Norėdami grįžti, spauskite 0" +
                            Color.RESET);
                } else if (isProductOutOfStock(productId)) {
                    System.out.println(Color.RED + "Šios prekės laikinai neturime. Norėdami grįžti, spauskite 0" +
                            Color.RESET);
                    scanner.nextLine();
                } else if (productId == 0) {
                    menu.showMenu();
                } else {
                    return productId;
                }
            } catch (InputMismatchException e) {
                System.out.println(Color.RED + "Prekės numeris turi būti sveikasis teigiamas skaičius. " +
                        "GRĮŽTI Į MENIU spausti 0" + Color.RESET);
                scanner.nextLine();
            } catch (InterruptedException | IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isProductOutOfStock(int productId) {
        boolean productIsOutOfStock = false;
        for (Product product : productList) {
            if (product.getProductId() == productId) {
                productIsOutOfStock = product.getQuantity() == 0;
                break;
            }
        }
        return productIsOutOfStock;
    }

    private int specifyTheQuantity(int itemNo) {
        while (true) {
            try {
                System.out.print("Norimos prekės KIEKIS: ");
                int quantity = scanner.nextInt();

                if (quantity <= 0) {
                    System.out.println(Color.RED + "Prekės kiekis turi būti sveikasis teigiamas skaičius." +
                            Color.RESET);
                } else if (quantityIsNotSufficient(itemNo, quantity)) {
                    System.out.println(Color.RED + "Turimas kiekis yra " + getItemQuantity(itemNo) +
                            " vnt. " + "Norėdami grįžti, spauskite 0" + Color.RESET);
                    scanner.nextLine();
                } else if (itemNo == 0) {
                    menu.showMenu();
                } else {
                    return quantity;
                }
            } catch (InputMismatchException e) {
                System.out.println(Color.RED + "Prekės kiekis turi būti sveikasis teigiamas skaičius. " +
                        "Norėdami grįžti, spauskite 0" + Color.RESET);
                scanner.nextLine();
            } catch (InterruptedException | IOException | InvalidKeySpecException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean quantityIsNotSufficient(int itemNo, int quantity) {
        for (Product product : productList) {
            if (product.getProductId() == itemNo) {
                return product.getQuantity() < quantity;
            }
        }
        return true;
    }

    private int getItemQuantity(int itemNo) {
        int itemQuantity = 0;
        for (Product product : productList) {
            if (product.getProductId() == itemNo) {
                itemQuantity = product.getQuantity();
            }
        }
        return itemQuantity;
    }

    public void endShopping() throws InterruptedException, IOException, InvalidKeySpecException, NoSuchAlgorithmException {
        PasswordService password = new PasswordService();
        if (shoppingCart.isEmpty()) {
            System.out.println(Color.RED + "PREKIŲ KREPŠELIS YRA TUŠČIAS." + Color.RESET);
            System.out.println("-----------------------------------");
            System.out.println(Color.GREEN + "Dėkojame, kad apsilankėte pas mus." + Color.RESET);
        } else {
            printShoppingCart();
            System.out.print("Pradėti pirkimo procesą? (t/n): ");
            String exitOrContinue = scanner.next();
            if (exitOrContinue.equalsIgnoreCase("n")) {
                System.out.println();
                System.out.print(Color.RED + "Jūsų PREKIŲ KREPŠELIS PANAIKINTAS. " + Color.RESET);
                System.out.println(Color.GREEN + "Dėkojame, kad apsilankėte pas mus." + Color.RESET);
            } else {
                System.out.println("\nPristatymo būdas: " +
                        "\n0 - PRISTATYMAS Į NAMUS" +
                        "\n1 - ATSIĖMIMAS PAŠTOMATE" +
                        "\n2 - ATSIĖMIMAS UŽSAKYMŲ ATSIĖMIMO PUNKTE");
                System.out.print("Pasirinkimas: ");
                int deliverySelection = scanner.nextInt();

                if (deliverySelection == 0) {
                    for (User user : fileReadWrite.getUsersFromFile()) {
                        if (user.getEmail().equals(UserLogin.enteredEmail) &&
                            password.validatePassword(UserLogin.enteredPassword, user.getPasswordHash())) {
                            System.out.print("\nJūsų telefono numeris -->> ");
                            System.out.print(user.getTel() + " <<--");
                            System.out.print("\nJūsų registracijos adresas -->> ");
                            System.out.println(user.getAddress() + " <<--");
                            break;
                        }
                    }
                    System.out.print("\nPristatyti šiuo adresu? (t/n): ");
                    String addressAnswer = scanner.next();
                    if (addressAnswer.equalsIgnoreCase("n")) {
                        System.out.print("Įveskite naują pristatymo adresą: ");
                        scanner.skip("((?<!(?>\\R))\\s)*");
                        String newShippingAddress = scanner.nextLine();
                        System.out.println("Ačiū. Naujas pristatymo adresas yra " + newShippingAddress);
                    }
                } else if (deliverySelection == 1) {
                    System.out.print("\nNurodykit paštomatą, kuriame norėsite atsiimti prekes: ");
                    scanner.skip("((?<!(?>\\R))\\s)*");
                    String parcelMachine = scanner.nextLine();
                    System.out.println("Ačiū. Pasirinktas paštomas, į kurį pristatysime prekes, yra " + parcelMachine);

                    System.out.print("Apie prekių pristatymą informuosime jus trumpąja žinute tel. nr. ");
                    for (User user : fileReadWrite.getUsersFromFile()) {
                        if (user.getEmail().equals(UserLogin.enteredEmail) &&
                            user.getPasswordHash().equals(password.generatePasswordHash(UserLogin.enteredPassword))) {
                            System.out.println(user.getTel());
                        }
                    }
                } else {
                    System.out.println("\nAčiū. Pasirinkote užsakymą atsiimti atsiėmimo punkte. " +
                            "Prekių atsiėmimo punkto ADRESAS: Vilniaus g. 1, Vilnius.");
                }

                System.out.println("\nApmokėjimo būdas: " +
                        "\n1 - ELEKTRONINĖ BANKININKYSTĖ" +
                        "\n2 - GRYNAISIAIS ATSIĖMIMO METU");
                System.out.print("Pasirinkimas: ");
                int paymentSelection = scanner.nextInt();

                if (paymentSelection == 1) {
                    System.out.println("\nPasirinkite banką: \n1 - Swedbank, 2 - SEB, 3 - Luminor");
                    System.out.print("Pasirinkimas: ");
                    int bankNo = scanner.nextInt();
                    System.out.println("\nJungiamasi prie banko: ");
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(500);
                        System.out.print(" . ");
                    }
                    boolean bankFound = false;
                    while (!bankFound) {
                        switch (bankNo) {
                            case 1:
                                System.out.println("PRISIJUNGTA prie SWEDBANK banko puslapio.");
                                bankFound = true;
                                break;
                            case 2:
                                System.out.println("PRISIJUNGTA prie SEB banko puslapio.");
                                bankFound = true;
                                break;
                            case 3:
                                System.out.println("PRISIJUNGTA prie Luminor banko puslapio.");
                                bankFound = true;
                                break;
                            default:
                                System.out.println("Tokio banko nėra, bandykite dar kartą.");
                                bankFound = false;
                        }
                    }
                    System.out.print("\nAtlikite mokėjimą banko puslapyje ir paspauskite ENTER: ");
                    scanner.skip("((?<!(?>\\R))\\s)*");
                    scanner.nextLine();
                    for (int i = 0; i < 10; i++) {
                        Thread.sleep(500);
                        System.out.print(" . ");
                    }
                    System.out.println("APMOKĖJIMAS GAUTAS.");

                } else {
                    System.out.println("Pasirinkote už prekes apmokėti grynaisiais pinigais atsiėmimo metu.");
                }
                System.out.println("\nSąskaitą faktūrą išsiuntėme el. paštu " + UserLogin.enteredEmail);

                System.out.println("DĖKOJAME, KAD PIRKOTE.");
            }
            System.out.println();
        }
    }
}
