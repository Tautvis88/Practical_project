package e_shop.entity;


public class Product {
    private int no;
    private String code;
    private String name;
    private int quantity;
    private double price;


    public Product(int no, String code, String name, double price) {
        this.no = no;
        this.code = code;
        this.name = name;
        this.price = price;
    }

    public Product(String no, String code, String name, String quantity, String price) {
        this.no = Integer.parseInt(no);
        this.code = code;
        this.name = name;
        this.quantity = Integer.parseInt(quantity);
        this.price = Double.parseDouble(price);
    }

    public Product(int itemNo, String code, String name, int quantity, double price) {
        this.no = itemNo;
        this.code = code;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getNo() {
        return no;
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
    public String toString() {      // dinaminis tarpų keitimas per kintamuosius
        return String.format("%4s", no) +
                "   |  " + String.format("%-11s", code) +
                "|  " + String.format("%-48s", name) +
                "|     " + String.format("%-7s", quantity) +
                "|    " + String.format("%6s", price);
    }
    public String toStringPart1() {
        return String.format("%4s", no) +
                "   |  " + String.format("%-11s", code) +
                "|  " + String.format("%-48s", name);
    }
    public String toStringPart2() {
        return "|    " + String.format("%6s", price);
    }

}

