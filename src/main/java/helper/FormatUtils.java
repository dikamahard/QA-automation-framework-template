package helper;

public class FormatUtils {

    public static String toDigits(String raw) {
        //if (raw == null) return 0;
        String digits = raw.replaceAll("\\D+", ""); // remove everything not 0-9
        return digits;
    }

    public static int rupiahToInteger(String rupiah) {
        //if (raw == null) return 0;
        String s = rupiah.trim();
        boolean neg = rupiah.startsWith("-");

        String digits = s.replaceAll("\\D", "");

        return Integer.parseInt(neg ? "-" + digits : digits);
    }

    public static String rupiahToDigits(String rupiah) {
        //if (raw == null) return 0;
        String s = rupiah.trim();
        boolean neg = rupiah.startsWith("-");

        String digits = s.replaceAll("\\D", "");

        return neg ? "-" + digits : digits;
    }

    public static String getRupiah(String raw) {
        //if (raw == null) return "";
        String[] lines = raw.split("\\R");

        int rpIndex = -1;
        for (int i = 0; i < lines.length; i++) {
            if (lines[i].contains("Rp")) {
                rpIndex = i;
                break; // first occurrence
            }
        }
        String rupiah = lines[rpIndex].trim();

        return rupiah.startsWith("-") ? ("- " + rupiah) : rupiah;
    }
}
