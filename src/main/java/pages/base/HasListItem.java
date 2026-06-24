package pages.base;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public interface HasListItem {
    WebDriver getDriver();
    default By itemList(String itemId) {
        return By.xpath("//tr[./td[.//*[.='"+itemId+"']]]");
    }

    default By itemListData(String itemId, int indexData) {
        return By.xpath("(//tr[./td[.//*[.='"+itemId+"']]]//td/div)["+indexData+"]");
    }

    default String getItemListData(String itemId, int indexData) {
        return getDriver().findElement(itemListData(itemId, indexData)).getText();
    }
}
