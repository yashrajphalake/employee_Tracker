package com.tracker.util;

import org.mindrot.jbcrypt.BCrypt;

public class HashUtil {
    public static String hash(String plain) {
        return BCrypt.hashpw(plain, BCrypt.gensalt(12));
    }
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java HashUtil <password>");
            return;
        }
        System.out.println(hash(args[0]));
    }
}
