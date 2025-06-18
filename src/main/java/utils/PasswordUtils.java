package utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordUtils {

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-512"); //
            byte[] hashedBytes = md.digest(password.getBytes()); //

            StringBuilder sb = new StringBuilder(); //
            for (byte b : hashedBytes) { //
                sb.append(String.format("%02x", b)); //
            }

            return sb.toString(); //
        } catch (NoSuchAlgorithmException e) { //
            throw new RuntimeException("Errore durante l'hash della password", e); //
        }
    }

    /**
     * Checks if a plain text password matches a hashed password (using SHA-512).
     * NOTE: This method is for SHA-512 without salt and iterations.
     * For production, consider more secure hashing algorithms like BCrypt or PBKDF2.
     * @param plainPassword The plain text password entered by the user.
     * @param hashedPassword The hashed password stored in the database.
     * @return true if the passwords match, false otherwise.
     */
    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        // Semplicemente, hasha la password in chiaro fornita e la confronta con l'hash memorizzato.
        // Questo funziona SOLO perché l'hashing originale non usa un salt variabile.
        String hashedAttempt = hashPassword(plainPassword);
        return hashedAttempt.equals(hashedPassword);
    }
}

