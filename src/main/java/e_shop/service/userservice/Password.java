package e_shop.service.userservice;

import e_shop.utils.Color;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Random;
import java.util.Scanner;

public class Password {
    private final String upperCaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private final String lowerCaseChars = upperCaseChars.toLowerCase();
    private final String digits = "0123456789";
    private final String combinedChars = upperCaseChars + lowerCaseChars + digits;
    private String password = "";
    private final Scanner scanner = new Scanner(System.in);


    public String requirementsValidation(String userPassword) throws InterruptedException {
        password = userPassword;
        while (!meetsRequirements(password)) {
            printRequirements();
            offerCreateAutomatically();
        }
        return password;
    }

    private boolean meetsRequirements(String password) {
        boolean meetsRequirements = false;
        if (password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")) {
            meetsRequirements = true;
        }
        return meetsRequirements;
    }

    private void printRequirements() throws InterruptedException {
        System.out.println(Color.RED_BRIGHT + "Slaptažodis neatitinka reikalavimų." + Color.RESET);
        Thread.sleep(1000);
        System.out.println("Reikia:");
        Thread.sleep(1000);
        System.out.println("1. Ne mažiau kaip 8 simbolių.");
        Thread.sleep(1500);
        System.out.println("2. Bent vienos didžiosios raidės.");
        Thread.sleep(1500);
        System.out.println("3. Bent vienos mažosios raidės.");
        Thread.sleep(1500);
        System.out.println("4. Bent vieno skaičiaus.");
        Thread.sleep(1500);
    }

    private void offerCreateAutomatically() {
        System.out.print("Sukurti naują slaptažodį automatiškai? (t/n): ");
        String answer = scanner.next();
        if (answer.trim().equalsIgnoreCase("t")) {
            password = createPassword();
            System.out.println("Jūsų naujas slaptažodis yra \"" + Color.GREEN_BRIGHT + password + Color.RESET + "\".");
        } else {
            System.out.print("Įveskite naują slaptažodį: ");
            password = scanner.next();
        }
    }

    public String createPassword() {
        Random random = new Random();

        int length = 8;
        char[] passwordCharsArray = new char[length];

        passwordCharsArray[0] = upperCaseChars.charAt(random.nextInt(upperCaseChars.length()));
        passwordCharsArray[1] = lowerCaseChars.charAt(random.nextInt(lowerCaseChars.length()));
        passwordCharsArray[7] = digits.charAt(random.nextInt(digits.length()));

        for (int i = 2; i < length - 1; i++) {
            passwordCharsArray[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }

        StringBuilder generatedPassword = new StringBuilder();
        for (char c : passwordCharsArray) {
            generatedPassword.append(c);
        }

        return password = generatedPassword.toString();
    }

    public String generatePasswordHash(String password) throws NoSuchAlgorithmException, InvalidKeySpecException {

        int iterations = 1000;
        char[] passwordChars = password.toCharArray();
        byte[] salt = getSalt();

        KeySpec keySpec = new PBEKeySpec(passwordChars, salt, iterations, 32 * 8);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = secretKeyFactory.generateSecret(keySpec).getEncoded();
        return iterations + ":" + toHex(salt) + ":" + toHex(hash);
    }

    private byte[] getSalt() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    private String toHex(byte[] array) {
        BigInteger bigInteger = new BigInteger(1, array);
        String hex = bigInteger.toString(16);

        int paddingLength = (array.length * 2) - hex.length();
        if (paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        } else {
            return hex;
        }
    }

    public boolean validatePassword(String enteredPassword, String storedPassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        String[] hashParts = storedPassword.split(":");
        int iterations = Integer.parseInt(hashParts[0]);
        byte[] salt = fromHex(hashParts[1]);
        byte[] hash = fromHex(hashParts[2]);

        KeySpec keySpec = new PBEKeySpec(enteredPassword.toCharArray(), salt, iterations, hash.length * 8);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] testHash = secretKeyFactory.generateSecret(keySpec).getEncoded();

        int diff = hash.length ^ testHash.length;   // array ilgius paverčia į binary numbers, jei tie ilgiai
        // neutampa, tai tai diff > 0, jei sutampa - diff = 0;

        for (int i = 0; i < hash.length && i < testHash.length; i++) {
            diff |= hash[i] ^ testHash[i]; // jei nors vienas narys nesutampa, tai dešinėje pusėje rezultatas bus > 0;
            // |= paima ir priskiria diffui skaičių, kuris yra didesnis. Jei mažesnis, tai nepriskiria.
        }

        return diff == 0;
    }

    private byte[] fromHex(String hex) {
        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
        return bytes;
    }
}
