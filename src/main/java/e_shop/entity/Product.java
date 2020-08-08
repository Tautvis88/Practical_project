package e_shop.entity;

import e_shop.utils.Color;

import javax.persistence.*;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private int id;

    private String code;
    private String name;
    private int quantity;
    private double price;

    public Product(int id, String code, String name, int quantity, double price) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
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
        return String.format("%4s", id) +
                "   |  " + String.format("%-11s", code) +
                "|  " + String.format("%-48s", name) +
                "|     " + String.format("%-7s", newQuantity) +
                "|    " + String.format("%6s", price);
    }

}

