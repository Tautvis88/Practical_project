package e_shop.service.shopservice;

import e_shop.entity.Product;
import e_shop.service.FileReadWrite;
import e_shop.service.Menu;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

public class ShopProducts {

    private Menu menu = new Menu();

    private static FileReadWrite fileReadWrite = new FileReadWrite();
    private static List<Product> productList;
    static {
        try {
            productList = fileReadWrite.getProductsFromFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showShopProducts() throws IOException, InterruptedException, InvalidKeySpecException, NoSuchAlgorithmException {
        printListOfShopProductsHeader();
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

    private void printListOfShopProductsHeader() {
        String name = "P R E K I Ų  S Ą R A Š A S";
        int numberOfSpaces = 40;
        String spaces = String.format("%" + numberOfSpaces + "s", "");
        System.out.println(spaces + name);
        for (int i = 0; i < 3; i++) {
            System.out.println(fileReadWrite.getStringLinesFromTextFile().get(i));
        }
    }


}
