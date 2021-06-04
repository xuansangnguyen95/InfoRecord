package pageObjects;

import commons.AbstractPage;
import org.openqa.selenium.WebDriver;
import pageUIs.HomePageUI;
import pageUIs.LoginPageUI;

public class HomePageObject extends AbstractPage {
    WebDriver driver;

    public HomePageObject(WebDriver driver) {
        this.driver = driver;
    }

    public String getNameOfUser() {
        waitToElementVisible(driver, HomePageUI.NAME_OF_USER);
        return getElementText(driver, HomePageUI.NAME_OF_USER);
    }
}
