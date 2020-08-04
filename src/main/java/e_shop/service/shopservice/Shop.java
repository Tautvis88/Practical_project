package e_shop.service.shopservice;

import e_shop.entity.Product;
import e_shop.service.FileReadWrite;
import e_shop.service.Menu;
import e_shop.utils.Color;

import java.io.IOException;
import java.util.*;

public class Shop {
    private final Scanner scanner = new Scanner(System.in);

    public static FileReadWrite fileReadWrite = new FileReadWrite();
    public static List<Product> shoppingCart = new LinkedList<>();
    public static Map<Product, Integer> productsAndQuantities;
    public List<Product> productList = fileReadWrite.getProductsFromFile();
    public Menu menu = new Menu();

    static {
        try {
            productsAndQuantities = fileReadWrite.getItemsFromFileMap();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Shop() throws IOException {
    }

    public void showShopItems() throws IOException, InterruptedException {
        System.out.println("                                     P R E K I Ų  S Ą R A Š A S");
        for (int i = 0; i < 3; i++) {
            System.out.println(fileReadWrite.getStringLinesFromTextFile().get(i));
        }
        int count = 1;
        for (Product product : productList) {
            System.out.println(product.toString());
            if (count % 3 == 0) {
                System.out.println(fileReadWrite.getStringLinesFromTextFile().get(6));
            }
            count++;
        }
        menu.showMenu();
    }

    public void showProductList() throws IOException, InterruptedException {
        System.out.println("                                     P R E K I Ų  S Ą R A Š A S");
        for (int i = 0; i < 3; i++) {
            System.out.println(fileReadWrite.getStringLinesFromTextFile().get(i));
        }

        int count = 1;
        for (Map.Entry<Product, Integer> entry : productsAndQuantities.entrySet()) {
            String quantity = String.format("%-7s", entry.getValue());

            if (entry.getValue() == 0) {
                quantity = Color.RED + quantity + Color.RESET;
            }
            System.out.println(entry.getKey().toStringPart1() + "|     " + quantity +
                    entry.getKey().toStringPart2());

            if (count % 3 == 0) {
                System.out.println(fileReadWrite.getStringLinesFromTextFile().get(6));
            }
            count++;
        }

        menu.showMenu();
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

    public void addItemToCart() throws IOException, InterruptedException {
        int itemNo = specifyTheItem();
        int quantity = specifyTheQuantity(itemNo);
        if (isItemAlreadyInCart(itemNo)) {
            for (Product product : shoppingCart) {
                if (product.getNo() == itemNo) {
                    System.out.println(Color.GREEN_BRIGHT +
                            "Tokia prekė jau yra prekių krepšelyje, todėl jos kiekis bus padidintas iki " +
                            (product.getQuantity() + quantity) + " vnt." + Color.RESET);
                    product.setQuantity(product.getQuantity() + quantity);
                    break;
                }
            }
        } else {
            for (Product product : productList) {
                if (product.getNo() == itemNo) {
                    Product boughtProduct = new Product(itemNo, product.getCode(), product.getName(), quantity, product.getPrice());
                    shoppingCart.add(boughtProduct);
                    decreaseItemQuantityInShop(itemNo, quantity);
                    System.out.println(Color.GREEN_BRIGHT + "Prekė sėkmingai pridėta į krepšelį." + Color.RESET);
                    break;
                }
            }
        }
        menu.showMenu();
    }

    public void removeItemFromCart() {


    }

    private void decreaseItemQuantityInShop(int itemNo, int quantity) {
        for (Map.Entry<Product, Integer> entry : productsAndQuantities.entrySet()) {
            if (entry.getKey().getNo() == itemNo) {
                int decreasedQuantity = entry.getValue() - quantity;
                entry.setValue(decreasedQuantity);
                break;
            }
        }
    }

    private boolean isItemAlreadyInCart(int itemNo) {
        for (Product product: shoppingCart) {
            if (product.getNo() == itemNo) {
                return true;
            }
        }
        return false;
    }

    public void showShoppingCart() throws IOException, InterruptedException {
        if (shoppingCart.isEmpty()) {
            System.out.println(Color.RED + "PREKIŲ KREPŠELIS YRA TUŠČIAS." + Color.RESET);
        } else {
            printCartHeader();
            shoppingCart.sort(Comparator.comparingInt(Product::getNo));
            for (Product product : shoppingCart) {
                System.out.println(product.toString());
            }
            printCartAmount();
        }

        menu.showMenu();
    }

    private int specifyTheItem() {
        while (true) {
            try {
                System.out.print("Norimos prekės NR.: ");
                int itemNo = scanner.nextInt();
                if (itemNo < 0) {
                    System.out.println(Color.RED + "Prekės numeris turi būti sveikasis teigiamas skaičius." +
                            Color.RESET);
                } else if (itemNo > productList.size()) {
                    System.out.println(Color.RED + "Tokios prekės sąraše nėra. GRĮŽTI Į MENIU spaust 0" +
                            Color.RESET);
                } else if (isProductOutOfStock(itemNo)) {
                    System.out.println(Color.RED + "Šios prekės laikinai neturime. GRĮŽTI Į MENIU spaust 0" +
                            Color.RESET);
                    scanner.nextLine();
                } else if (itemNo == 0) {
                    menu.showMenu();
                } else {
                    return itemNo;
                }
            } catch (InputMismatchException e) {
                System.out.println(Color.RED + "Prekės numeris turi būti sveikasis teigiamas skaičius. " +
                        "GRĮŽTI Į MENIU spausti 0" + Color.RESET);
                scanner.nextLine();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isProductOutOfStock(int itemNo) {
        boolean productIsOutOfStock = false;
        for (Map.Entry<Product, Integer> productAndQuantity : productsAndQuantities.entrySet()) {
            if (productAndQuantity.getKey().getNo() == itemNo) {
                productIsOutOfStock = productAndQuantity.getKey().getQuantity() == 0;
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

                if (quantity < 0) {
                    System.out.println(Color.RED + "Prekės kiekis turi būti sveikasis teigiamas skaičius." +
                            Color.RESET);
                } else if (quantityIsNotSufficient(itemNo, quantity)) {
                    System.out.println(Color.RED + "Turimas kiekis yra " + getItemQuantity(itemNo) +
                            " vnt. " + "GRĮŽTI Į MENIU spausti 0" + Color.RESET);
                    scanner.nextLine();
                } else if (itemNo == 0) {
                    menu.showMenu();
                } else {
                    return quantity;
                }
            } catch (InputMismatchException e) {
                System.out.println(Color.RED + "Prekės kiekis turi būti sveikasis teigiamas skaičius. " +
                        "GRĮŽTI Į MENIU spausti 0" + Color.RESET);
                scanner.nextLine();
            } catch (InterruptedException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean quantityIsNotSufficient(int itemNo, int quantity) {
        for (Product product : productList) {
            if (product.getNo() == itemNo) {
                return product.getQuantity() < quantity;
            }
        }
        return true;
    }

    private int getItemQuantity(int itemNo) {
        int itemQuantity = 0;
        for (Product product : productList) {
            if (product.getNo() == itemNo) {
                itemQuantity = product.getQuantity();
            }
        }
        return itemQuantity;
    }
}
