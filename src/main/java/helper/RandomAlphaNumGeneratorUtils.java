package helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class RandomAlphaNumGeneratorUtils {

    private static final String FILE_PATH = "invoice_number.txt";

    public static String getNextInvoiceNumber() {
        try {
            if (Files.exists(Paths.get(FILE_PATH))) {
                String current = Files.readString(Paths.get(FILE_PATH)).trim();
                int nextNumber = Integer.parseInt(current) + 1;
                return String.format("%04d", nextNumber); // format 0001, 0002, etc.
            } else {
                return "0001";
            }
        } catch (IOException | NumberFormatException e) {
            throw new RuntimeException("Failed Read invoice number", e);
        }
    }

    public static void saveInvoiceNumber(String number) {
        try {
            Files.writeString(Paths.get(FILE_PATH), number);
        } catch (IOException e) {
            throw new RuntimeException("Failed Save invoice number", e);
        }
    }

    public static String generateRandomInvoice(int length) {
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "0123456789" +
                "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphaNumericString.length());
            sb.append(alphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    public static String generateRandomAlphaNum(int length) {
        String alphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
                "0123456789" +
                "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(alphaNumericString.length());
            sb.append(alphaNumericString.charAt(index));
        }
        return sb.toString();
    }

    public static String generateRandomNum(int length) {
        String numericString = "0123456789";
        StringBuilder sb = new StringBuilder(length);
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(numericString.length());
            sb.append(numericString.charAt(index));
        }
        return sb.toString();
    }
}
