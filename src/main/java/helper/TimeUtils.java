package helper;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;

public class TimeUtils {

    public static String getTodayDate(String format) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Jakarta"));

        return switch (format) {
            case "dMMMMYYYY" -> today.format(DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.ENGLISH));
            case "ddMMYYYY" -> today.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")); // e.g. "12-11-2025"
            case "day" -> today.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH);
            default -> throw new IllegalArgumentException("Unsupported date format: " + format);
        };
    }
}
