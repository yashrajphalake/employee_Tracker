package com.util;

/**
 * PasswordUtil.java
 * Utility class for handling password hashing and verification.
 * * NOTE: For a production application, you MUST use a robust, modern hashing 
 * library like BCrypt, SCrypt, or Argon2. This implementation is a placeholder
 * to demonstrate the correct structure.
 */
public class PasswordUtil {

    /**
     * Hashes a plain text password.
     * TEMPORARY SIMULATION: Just prepends a prefix. 
     * @param plainPassword The user's password in plain text.
     * @return The resulting hash string.
     */
    public static String hashPassword(String plainPassword) {
        // In production: Use BCrypt.hashpw(plainPassword, BCrypt.gensalt());
        return "{DEMO_HASH}" + plainPassword;
    }

    /**
     * Verifies a plain text password against a stored hash.
     * TEMPORARY SIMULATION: Checks if the password matches the hash after removing the prefix.
     * @param plainPassword The password submitted by the user.
     * @param storedHash The password hash retrieved from the database.
     * @return True if the passwords match, false otherwise.
     */
    public static boolean verifyPassword(String plainPassword, String storedHash) {
        if (storedHash == null || storedHash.isEmpty()) {
            return false;
        }
        
        // In production: Use BCrypt.checkpw(plainPassword, storedHash);
        
        if (storedHash.startsWith("{DEMO_HASH}")) {
            String originalPassword = storedHash.substring("{DEMO_HASH}".length());
            return plainPassword.equals(originalPassword);
        }
        
        // Fallback for non-prefixed hashes
        return plainPassword.equals(storedHash); 
    }
}