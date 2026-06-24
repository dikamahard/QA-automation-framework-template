package pages.web.galileo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import pages.base.BaseWebGalileoPage;

public class PromoPage extends BaseWebGalileoPage {
    public PromoPage(WebDriver driver) {
        super(driver);
    }

    public static String url = "http://192.168.7.110:9797/loyalty-program/loyalty";
    public By title = titlePage("Promo List");

    public By btnAddPromo = button("Add Promo");

    public By itemListPromo(String promoName) {
        return By.xpath("//tr[contains(.,'"+promoName+"')]");
    }
    public void clickBtnAddPromo() {
        System.out.println("clickBtnAddPromo");
        action.click(btnAddPromo);
    }

    public void clickEditPromo() {
        System.out.println("clickEditPromo");
        clickBurgerAndSelectValue(burgerMenu, "Edit");
    }

    public void clickDeletePromo() {
        System.out.println("clickDeletePromo");
        clickBurgerAndSelectValue(burgerMenu, "Delete");
        action.click(button("Delete"));
    }

}
