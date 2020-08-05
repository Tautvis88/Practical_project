package e_shop.entity;

public class User {
    private int id;
    private String name;
    private String surname;
    private String email;
    private String mobNo;
    private String address;
    private String password;
    private String passwordHash;

    public User() {
    }

    public User(int id, String name, String surname, String email, String mobNo, String address,
                String password, String passwordHash) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.mobNo = mobNo;
        this.address = address;
        this.password = password;
        this.passwordHash = passwordHash;
    }

    public User(String id, String name, String surname, String email, String mobNo, String address,
                String password, String passwordHash) {
        this.id = Integer.parseInt(id);     // if'as dėl Stringo, kad vienas konstruktorius
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.mobNo = mobNo;
        this.address = address;
        this.password = password;
        this.passwordHash = passwordHash;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobNo() {
        return mobNo;
    }

    public String getAddress() {
        return address;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public String toString() {  // dinaminis stringo ilgio keitimas arba automatinis
        return String.format("%-5s", id) +
                String.format("%-18s", name) +
                String.format("%-20s", surname) +
                String.format("%-55s", email) +
                String.format("%-15s", mobNo) +
                String.format("%-60s", address) +
                String.format("%-20s", password) +
                String.format("%-105s", passwordHash);
    }
}