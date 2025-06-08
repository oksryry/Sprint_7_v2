package utils;

import java.util.Random;

public class UtilsRandomString {

    public static Random random = new Random();


    public static final String CHARACTERS = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public static String randomString() {
        int length = random.nextInt(15);
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    public static int numberOfDays() {
        int number = random.nextInt(30);
        return number;
    }
}
