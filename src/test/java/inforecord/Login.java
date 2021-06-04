package inforecord;

import commons.AbstractTest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pageObjects.HomePageObject;
import pageObjects.LoginPageObject;
import pageObjects.PageGeneratorManager;

public class Login extends AbstractTest {
    WebDriver driver;
    String source_folder = System.getProperty("user.dir");

    String username, password, errorLine, nameOfUser;

    LoginPageObject loginPage;
    HomePageObject homePage;

    @Parameters("browser")
    @BeforeClass
    public void beforeClass(String browserName) {
        driver = getBrowserDriver(browserName, "https://inforecord.ru/back-office/login");
        loginPage = PageGeneratorManager.getLoginPage(driver);

        username = "sang.nguyen02";
        password = "thatismylove";
        errorLine = "Incorrect username or password.";
        nameOfUser = "Nguyễn Xuân Sang";
    }


    @Test
    public void TC01_LoginWithEmptyUsername(){

        log.info("Login With Empty Username - Step 01: input to password with value: " + password);
        loginPage.inputToPassword(password);

        log.info("Login With Empty Username - Step 02: click to button 'Log in'");
        loginPage.clickToLoginButton();

        log.info("Login With Empty Username - Step 03: verify error line");
        verifyEquals(loginPage.getErrorLine(), "Incorrect username or password.");
    }

    @Test
    public void TC02_LoginWithEmptyPassword(){

        log.info("Login With Empty Password - Step 01: input to username with value: " + username);
        loginPage.inputToUsername(username);

        log.info("Login With Empty Password - Step 02: click to button 'Log in'");
        loginPage.clickToLoginButton();

        log.info("Login With Empty Password - Step 03: verify error line");
        verifyEquals(loginPage.getErrorLine(), "Incorrect username or password.");
    }

    @Test
    public void TC03_LoginWithCorrectInformation(){

        log.info("Login With Correct Information - Step 01: input to username with value: " + username);
        loginPage.inputToUsername(username);

        log.info("Login With Correct Information - Step 02: input to username with value: " + password);
        loginPage.inputToPassword(password);

        log.info("Login With Correct Information - Step 03: click to button 'Log in'");
        loginPage.clickToLoginButton();
        homePage = PageGeneratorManager.getHomePage(driver);

        log.info("Login With Correct Information - Step 04: verify name of user");
        verifyEquals(homePage.getNameOfUser(), nameOfUser);
    }

    @AfterClass
    public void AfterClass(){
        closeBrowserAndDriver(driver);
    }
}
