package e_shop.entity;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    private String email;
    private String tel;
    private String address;
    private String password;

    @Column(name = "password_hash")
    private String passwordHash;

    public User() {
    }

    public User(String userId, String firstName, String lastName, String email, String tel, String address,
                String password, String passwordHash) {
        this.userId = Integer.parseInt(userId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.tel = tel;
        this.address = address;
        this.password = password;
        this.passwordHash = passwordHash;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getTel() {
        return tel;
    }

    public String getAddress() {
        return address;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    @Override
    public String toString() {  // dinaminis stringo ilgio keitimas arba automatinis
        return String.format("%-5s", userId) +
                String.format("%-18s", firstName) +
                String.format("%-20s", lastName) +
                String.format("%-55s", email) +
                String.format("%-15s", tel) +
                String.format("%-60s", address) +
                String.format("%-20s", password) +
                String.format("%-105s", passwordHash);
    }
}