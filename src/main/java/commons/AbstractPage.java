package commons;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class AbstractPage {
	
	private WebElement element;
	private JavascriptExecutor jsExecutor;
	private WebDriverWait expliciWait;
	private List<WebElement> elements;

	public void openPageURL(WebDriver driver, String url) {
		driver.get(url);
	}

	public String getTitle(WebDriver driver, String title) {
		return driver.getTitle();
	}

	public String getCurrentURL(WebDriver driver) {
		return driver.getCurrentUrl();
	}

	public String getPageSource(WebDriver driver) {
		return driver.getPageSource();
	}

	public void backToPage(WebDriver driver) {
		driver.navigate().back();
	}

	public void forwardToPage(WebDriver driver) {
		driver.navigate().forward();
	}

	public void refreshPage(WebDriver driver) {
		driver.navigate().refresh();
	}

	public void acceptAlert(WebDriver driver) {
		driver.switchTo().alert().accept();
	}

	public void cancelAlert(WebDriver driver) {
		driver.switchTo().alert().dismiss();
	}

	public void getTextAlert(WebDriver driver) {
		driver.switchTo().alert().getText();
	}

	public void setTextAlert(WebDriver driver, String value) {
		driver.switchTo().alert().sendKeys(value);
	}

	public void waitAlertPresence(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		explicitWait.until(ExpectedConditions.alertIsPresent());
	}

	public void switchToWindowByID(WebDriver driver, String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindow : allWindows) {
			if (!runWindow.equals(parentID)) {
				driver.switchTo().window(runWindow);
				break;
			}
		}
	}

	public void switchToWindowByTitle(WebDriver driver, String title) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			driver.switchTo().window(runWindows);
			String currentWin = driver.getTitle();
			if (currentWin.equals(title)) {
				break;
			}
		}
	}

	public void closeAllWindowsWithoutParent(WebDriver driver, String parentID) {
		Set<String> allWindows = driver.getWindowHandles();
		for (String runWindows : allWindows) {
			if (!runWindows.equals(parentID)) {
				driver.switchTo().window(runWindows);
				driver.close();
			}
		}
		driver.switchTo().window(parentID);
	}

	public WebElement getElement(WebDriver driver, String locator) {
		return driver.findElement(getByXpath(locator));
	}

	public List<WebElement> getElements(WebDriver driver, String locator) {
		return driver.findElements(getByXpath(locator));
	}

	public By getByXpath(String locator) {
		return By.xpath(locator);
	}

	public String getDynamicLocator(String locator, String... values) {
		locator = String.format(locator, (Object[]) values);
		return locator;
	}
	
	public void clickToElement(WebDriver driver, String locator) {
		if(driver.toString().toLowerCase().contains("edge")) {
			sleepInMilisecond(500);
		}
		WebElement element = getElement(driver, locator);
		element.click();
	}
	
	public void clickToElement(WebDriver driver, String locator, String... values) {
		if(driver.toString().toLowerCase().contains("edge")) {
			sleepInMilisecond(500);
		}
		WebElement element = getElement(driver, getDynamicLocator(locator, values));
		element.click();
	}
	
	public void sendKeysToElement(WebDriver driver, String locator, String value) {
		WebElement element = getElement(driver, locator);
		element.clear();
		sleepInMilisecond(500);
		element.sendKeys(value);
	}

	public void sendKeysToElement(WebDriver driver, String locator, String value, String... values) {
		WebElement element = getElement(driver, getDynamicLocator(locator, values));
		element.clear();
		sleepInMilisecond(500);
		element.sendKeys(value);
	}
	
	public void selectItemInDropdown(WebDriver driver, String locator, String itemValue) {
		WebElement element = getElement(driver, locator);
		Select select = new Select(element);
		select.selectByVisibleText(itemValue);
	}

	public void selectItemInDropdown(WebDriver driver, String locator, String itemValue, String... values) {
		WebElement element = getElement(driver, getDynamicLocator(locator, values));
		Select select = new Select(element);
		select.selectByVisibleText(itemValue);
	}
	
	public String getFirstSelectedText(WebDriver driver, String locator) {
		WebElement element = getElement(driver, locator);
		Select select = new Select(element);
		return select.getFirstSelectedOption().getText();
	}

	public String getFirstSelectedText(WebDriver driver, String locator, String... values) {
		WebElement element = getElement(driver, getDynamicLocator(locator, values));
		Select select = new Select(element);
		return select.getFirstSelectedOption().getText();
	}

	public boolean isDropdownMultiple(WebDriver driver, String locator) {
		WebElement element = getElement(driver, locator);
		Select select = new Select(element);
		return select.isMultiple();
	}

	public void selectItemInCustomDropdown(WebDriver driver, String parentLocator, String childItemLocator,
			String expectedItem) {
		getElement(driver, parentLocator).click();
		sleepInMilisecond(1);

		WebDriverWait explicitWait = new WebDriverWait(driver, 20);
		explicitWait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(getByXpath(childItemLocator)));

		List<WebElement> allItems = getElements(driver, childItemLocator);

		for (WebElement item : allItems) {
			if (item.getText().equals(expectedItem)) {
				JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
				jsExecutor.executeScript("arguments[0].scrollIntoView(true);", item);
				sleepInMilisecond(1);

				item.click();
				sleepInMilisecond(1);
				break;
			}
		}
	}

	public void sleepInMilisecond(long timeout) {
		try {
			Thread.sleep(timeout);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public String getElementAttribute(WebDriver driver, String locator, String attributeName) {
		WebElement element = getElement(driver, locator);
		return element.getAttribute(attributeName);
	}

	public String getElementAttribute(WebDriver driver, String locator, String attributeName, String... values) {
		WebElement element = getElement(driver, getDynamicLocator(locator, values));
		return element.getAttribute(attributeName);
	}
	
	public String getElementText(WebDriver driver, String locator) {
		WebElement element = getElement(driver, locator);
		return element.getText();
	}

	public String getElementText(WebDriver driver, String locator, String... values) {
		WebElement element = getElement(driver, getDynamicLocator(locator, values));
		return element.getText();
	}
	
	public int countElementSize(WebDriver driver, String locator) {
		return getElements(driver, locator).size();
	}

	public int countElementSize(WebDriver driver, String locator, String... values) {
		return getElements(driver, getDynamicLocator(locator, values)).size();
	}
	
	public void checkToCheckbox(WebDriver driver, String locator) {
		WebElement element = getElement(driver, locator);
		if(!element.isSelected()) {
			element.click();
		}
	}
	
	public void checkToCheckbox(WebDriver driver, String locator, String... values) {
		WebElement element = getElement(driver, getDynamicLocator(locator, values));
		if(!element.isSelected()) {
			element.click();
		}
	}
	
	public void uncheckToCheckbox(WebDriver driver, String locator) {
		WebElement element = getElement(driver, locator);
		if(element.isSelected()) {
			element.click();
		}
	}
	
	public void uncheckToCheckbox(WebDriver driver, String locator, String... values) {
		WebElement element = getElement(driver, getDynamicLocator(locator, values));
		if(element.isSelected()) {
			element.click();
		}
	}
	
	public void checkToUncheckbox(WebDriver driver, String locator, String... values) {
		WebElement element = getElement(driver, getDynamicLocator(locator, values));
		if(!element.isSelected()) {
			element.click();
		}
	}
	
	public boolean isElementDisplayed(WebDriver driver, String locator) {
		return getElement(driver, locator).isDisplayed();
	}
	
	public boolean isElementDisplayed(WebDriver driver, String locator, String... values) {
		return getElement(driver, getDynamicLocator(locator, values)).isDisplayed();
	}
	
	public boolean isElementUndisplayed(WebDriver driver, String locator) {
		overrideImplicitWait(driver, GlobalConstants.SHORT_TIMEOUT);
		elements = getElements(driver, locator);
		overrideImplicitWait(driver, GlobalConstants.LONG_TIMEOUT);
		
		if(elements.size() == 0) {
			return true;
		} else if(elements.size() > 0 && !elements.get(0).isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isElementUndisplayed(WebDriver driver, String locator, String... values) {
		overrideImplicitWait(driver, GlobalConstants.SHORT_TIMEOUT);
		elements = getElements(driver, getDynamicLocator(locator, values));
		overrideImplicitWait(driver, GlobalConstants.LONG_TIMEOUT);
		
		if(elements.size() == 0) {
			return true;
		} else if(elements.size() > 0 && !elements.get(0).isDisplayed()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void overrideImplicitWait(WebDriver driver, long timeInSecond) {
		driver.manage().timeouts().implicitlyWait(timeInSecond, TimeUnit.SECONDS);
	}
	
	public boolean isElementSelected(WebDriver driver, String locator) {
		return getElement(driver, locator).isSelected();
	}
	
	public boolean isElementSelected(WebDriver driver, String locator, String... values) {
		return getElement(driver, getDynamicLocator(locator, values)).isSelected();
	}
	
	public boolean isElementEnabled(WebDriver driver, String locator) {
		return getElement(driver, locator).isEnabled();
	}
	
	public void switchToFrame(WebDriver driver, String locator) {
		driver.switchTo().frame(getElement(driver, locator));
	}
	
	public void switchToDefault(WebDriver driver) {
		driver.switchTo().defaultContent();
	}
	
	public void doubleClickToElement(WebDriver driver, String locator) {
		Actions action = new Actions(driver);
		action.doubleClick(getElement(driver, locator)).perform();
	}
	
	public void rightClickToElement(WebDriver driver, String locator) {
		Actions action = new Actions(driver);
		action.contextClick(getElement(driver, locator)).perform();
	}
	
	public void hoverMouseToElement(WebDriver driver, String locator) {
		Actions action = new Actions(driver);
		action.moveToElement(getElement(driver, locator)).perform();
	}
	
	public void clickAndHoldToElement(WebDriver driver, String locator) {
		Actions action = new Actions(driver);
		action.clickAndHold(getElement(driver, locator)).perform();
	}
	
	public void dragAndDropToElement(WebDriver driver, String sourceLocator, String targerLocator) {
		Actions action = new Actions(driver);
		action.dragAndDrop(getElement(driver, sourceLocator), getElement(driver, targerLocator)).perform();
	}
	
	public void sendKeyboardToElement(WebDriver driver, String locator, Keys key) {
		Actions action = new Actions(driver);
		action.sendKeys(getElement(driver, locator), key).perform();
	}
	
	public Object executeForBrowser(WebDriver driver, String javaScript) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return jsExecutor.executeScript(javaScript);
	}

	public String getInnerText(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return document.documentElement.innerText;");
	}
	
	public boolean areExpectedTextInInnerText(WebDriver driver, String textExpected) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
		return textActual.equals(textExpected);
	}

	public void scrollToBottomPage(WebDriver driver) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.scrollBy(0,document.body.scrollHeight)");
	}

	public void navigateToUrlByJS(WebDriver driver, String url) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("window.location = '" + url + "'");
	}

	public void highlightElement(WebDriver driver, String locator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getElement(driver, locator);
		String originalStyle = element.getAttribute("style");
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", "border: 2px solid red; border-style: dashed;");
		sleepInMilisecond(1);
		jsExecutor.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, "style", originalStyle);
	}

	public void clickToElementByJS(WebDriver driver, String locator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getElement(driver, locator);
		jsExecutor.executeScript("arguments[0].click();", element);
	}

	public void scrollToElement(WebDriver driver, String locator) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getElement(driver, locator);
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
		sleepInMilisecond(1000);
	}

	public void sendkeyToElementByJS(WebDriver driver, String locator, String value) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getElement(driver, locator);
		jsExecutor.executeScript("arguments[0].setAttribute('value', '" + value + "')", element);
	}

	public void removeAttributeInDOM(WebDriver driver, String locator, String attributeRemove) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		WebElement element = getElement(driver, locator);
		jsExecutor.executeScript("arguments[0].removeAttribute('" + attributeRemove + "');", element);
	}

	public boolean areJQueryAndJSLoadedSuccess(WebDriver driver) {
		WebDriverWait explicitWait = new WebDriverWait(driver, 30);
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

		ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				try {
					return ((Long) jsExecutor.executeScript("return jQuery.active") == 0);
				} catch (Exception e) {
					return true;
				}
			}
		};

		ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
			@Override
			public Boolean apply(WebDriver driver) {
				return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
			}
		};

		return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
	}

	public boolean verifyTextInnerText(WebDriver driver, String textExpected) {
		String textActual = (String) jsExecutor.executeScript("return document.documentElement.innerText.match('" + textExpected + "')[0]");
		return textActual.equals(textExpected);
	}
	
	public boolean isImageLoaded(WebDriver driver, String locator) {
		boolean status = (boolean) jsExecutor.executeScript("return arguments[0].complete && typeof arguments[0].naturalWidth!=\"undefined\" && arguments[0].naturalWidth > 0", getElement(driver, locator));
		if(status) {
			return true;
		}else {
			return false;
		}
	}
	
	public void waitToElementVisible(WebDriver driver, String locator) {
		WebDriverWait explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(locator)));
	}
	
	public void waitToElementVisible(WebDriver driver, String locator, String... values) {
		WebDriverWait explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
		explicitWait.until(ExpectedConditions.visibilityOfElementLocated(getByXpath(getDynamicLocator(locator, values))));
	}
	
	public void waitToElementInvisible(WebDriver driver, String locator) {
		WebDriverWait explicitWait = new WebDriverWait(driver, GlobalConstants.SHORT_TIMEOUT);
		overrideImplicitWait(driver, GlobalConstants.SHORT_TIMEOUT);
		
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(locator)));
		overrideImplicitWait(driver, GlobalConstants.LONG_TIMEOUT);
	}
	
	public void waitToElementInvisible(WebDriver driver, String locator, String... values) {
		WebDriverWait explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
		explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(getByXpath(getDynamicLocator(locator, values))));
	}
	
	public void waitToElementClickable(WebDriver driver, String locator) {
		WebDriverWait explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(locator)));
	}
	
	public void waitToElementClickable(WebDriver driver, String locator, String... values) {
		WebDriverWait explicitWait = new WebDriverWait(driver, GlobalConstants.LONG_TIMEOUT);
		explicitWait.until(ExpectedConditions.elementToBeClickable(getByXpath(getDynamicLocator(locator, values))));
	}
	
	public boolean isDataStringSortedAscending(WebDriver driver, String locator) {
		
		ArrayList<String> arrayList = new ArrayList<>();
		
		List<WebElement> elementList = driver.findElements(By.xpath(locator));
		
		for(WebElement element : elementList) {
			arrayList.add(element.getText());
		}
		
		ArrayList<String> sortedList = new ArrayList<>();
		for(String child : arrayList) {
			sortedList.add(child);
		}
		
		Collections.sort(sortedList);
		
		return sortedList.equals(arrayList);
	}
	
	public boolean isDataStringSortedDescending(WebDriver driver, String locator) {
		
		ArrayList<String> arrayList = new ArrayList<>();
		
		List<WebElement> elementList = driver.findElements(By.xpath(locator));
		
		for(WebElement element : elementList) {
			arrayList.add(element.getText());
		}
		
		ArrayList<String> sortedList = new ArrayList<>();
		for(String child : arrayList) {
			sortedList.add(child);
		}
		
		Collections.sort(sortedList);
		
		Collections.reverse(sortedList);
		
		return sortedList.equals(arrayList);
	}
	
	public boolean isDataFloatSortedDescending(WebDriver driver, String locator) {
		
		ArrayList<Float> arrayList = new ArrayList<Float>();
		
		List<WebElement> elementList = driver.findElements(By.xpath(locator));
		
		for(WebElement element : elementList) {
			arrayList.add(Float.parseFloat(element.getText().replace("$", "").replace(",", "").trim()));
		}
		
		ArrayList<Float> sortedList = new ArrayList<Float>();
		for(Float child : arrayList) {
			sortedList.add(child);
		}
		
		Collections.sort(sortedList);
		
		Collections.reverse(sortedList);
		
		return sortedList.equals(arrayList);
	}
	
	public boolean isDataFloatSortedAscending(WebDriver driver, String locator) {
		
		ArrayList<Float> arrayList = new ArrayList<Float>();
		
		List<WebElement> elementList = driver.findElements(By.xpath(locator));
		
		for(WebElement element : elementList) {
			arrayList.add(Float.parseFloat(element.getText().replace("$", "").replace(",", "").trim()));
		}
		
		ArrayList<Float> sortedList = new ArrayList<Float>();
		for(Float child : arrayList) {
			sortedList.add(child);
		}
		
		Collections.sort(sortedList);
		
		return sortedList.equals(arrayList);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}