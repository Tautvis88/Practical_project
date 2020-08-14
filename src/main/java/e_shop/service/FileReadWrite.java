package e_shop.service;

import e_shop.entity.Product;
import e_shop.entity.User;
import e_shop.repository.ProductRepositoryImpl;
import e_shop.repository.UserRepository;
import e_shop.repository.UserRepositoryImpl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;

public class FileReadWrite {
    private final Path usersFilePath = Paths.get("src/main/resources/files/Users.txt");
    private final Path productsFilePath = Paths.get("src/main/resources/files/Products.txt");
    private List<String> stringLinesFromTextFile;

    public void writeUserToTextFile(Path usersFilePath, User user) throws IOException {
        Files.write(usersFilePath, (System.lineSeparator() + user).getBytes(), StandardOpenOption.APPEND);
    }

    public List<User> getUsersFromFile() throws IOException {

        List<String> fileLines = Files.readAllLines(usersFilePath);

        List<User> usersList = new LinkedList<>();
        UserRepository userRepository = new UserRepositoryImpl();

        for (String fileLine : fileLines) {
            if (fileLine.trim().matches("\\d.*")) {
                String[] info = fileLine.split("  +");
                User user = new User(info[0], info[1], info[2], info[3], info[4], info[5], info[6], info[7]);
                usersList.add(user);
                // userRepository.saveUserToDatabase(user);
            }
        }
        return usersList;   // paduoti šitą listą į DB, o ne po atskirą userį
    }

    public Path getUsersFilePath() {
        return usersFilePath;
    }

    public List<Product> getProductsFromFile() throws IOException {
        // Nuskaito failo eilutes
        stringLinesFromTextFile = Files.readAllLines(productsFilePath);

        // Kuria prekes iš eilučių
        List<Product> products = new LinkedList<>();
        ProductRepositoryImpl productRepository = new ProductRepositoryImpl();
        for (String stringLine : stringLinesFromTextFile) {
            if (stringLine.trim().matches("\\d.*")) {
                String[] parameters = stringLine.replaceAll("  +", "").split("\\|");

                int id = Integer.parseInt(parameters[0]);
                String code = parameters[1];
                String name = parameters[2];
                int quantity = Integer.parseInt(parameters[3]);
                double price = Double.parseDouble(parameters[4]);

                Product product = new Product(id, code, name, quantity, price);
                products.add(product);
                // productRepository.saveProductToDatabase(product);
            }
        }
        return products;
    }

    public List<String> getStringLinesFromTextFile() {
        return stringLinesFromTextFile;
    }

}
