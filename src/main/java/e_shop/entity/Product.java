package e_shop.entity;

import e_shop.utils.Color;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int productId;

    private String productCode;
    private String productName;
    private int quantity;
    private double price;

    public Product(int productId, String productCode, String productName, int quantity, double price) {
        this.productId = productId;
        this.productCode = productCode;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductCode() {
        return productCode;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setQuantity(int newQuantity) {
        this.quantity = newQuantity;
    }

    @Override
    public String toString() {
        String newQuantity;
        if (quantity == 0) {
            newQuantity = Color.RED + String.format("%-7s", quantity) + Color.RESET;
        } else {
            newQuantity = String.valueOf(quantity);
        }
        // dinaminis tarpų keitimas per kintamuosius
        return String.format("%4s", productId) +
                "   |  " + String.format("%-11s", productCode) +
                "|  " + String.format("%-48s", productName) +
                "|     " + String.format("%-7s", newQuantity) +
                "|    " + String.format("%6s", price);
    }

}

