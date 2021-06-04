package pageObjects;

import commons.AbstractPage;
import org.openqa.selenium.WebDriver;
import pageUIs.LoginPageUI;

public class LoginPageObject extends AbstractPage {
    WebDriver driver;

    public LoginPageObject(WebDriver driver) {
        this.driver = driver;
    }


    public void inputToPassword(String password) {
        waitToElementVisible(driver, LoginPageUI.PASSWORD_TEXTBOX);
        sendKeysToElement(driver, LoginPageUI.PASSWORD_TEXTBOX, password);
    }

    public void inputToUsername(String username) {
        waitToElementVisible(driver, LoginPageUI.USERNAME_TEXTBOX);
        sendKeysToElement(driver, LoginPageUI.USERNAME_TEXTBOX, username);
    }

    public void clickToLoginButton() {
        waitToElementClickable(driver, LoginPageUI.LOGIN_BUTTON);
        clickToElement(driver, LoginPageUI.LOGIN_BUTTON);
    }

    public String getErrorLine() {
        waitToElementVisible(driver, LoginPageUI.ERROR_LINE);
        return getElementText(driver, LoginPageUI.ERROR_LINE);
    }
}
