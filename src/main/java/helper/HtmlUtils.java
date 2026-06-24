package helper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public class HtmlUtils {


    public static String extractHref(String escaped) {
        System.out.println("extractHref");

        if (escaped!= null) {
            String html = Parser.unescapeEntities(escaped, false);
            Document doc = Jsoup.parse(html);
            Elements urlElement = doc.selectXpath("//*[@href]");
            System.out.println(">>> " + urlElement.attr("href"));
            return urlElement.attr("href");
        }

        return null;
    }

    public static String extractOTP(String html) {
        Document doc = Jsoup.parse(html);
        Elements otpElement  = doc.selectXpath("//p[.='Please use the following OTP Code to continue the login process:']/following-sibling::p[1]");
        System.out.println(">>> " + otpElement.text());
        return otpElement.text();
    }

    public static String unescapedHtml(String escaped) {
        System.out.println("unescapedHtml");

        return Parser.unescapeEntities(escaped, false);
    }
}
