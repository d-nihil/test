package market;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.NoSuchElementException;

import java.time.Duration;
import java.util.*;

public abstract class Page {
    private String url;
    private WebDriver driver;
    private WebDriverWait wait;
    private Actions action;
    private JavascriptExecutor js;

    public Page(WebDriver driver, WebDriverWait wait, String url) {
        this.driver = driver;
        this.wait = wait;
        this.url = url;
        action = new Actions(driver);
        js = (JavascriptExecutor) driver;
    }

    public Page(WebDriver driver, String url) {
        this(driver, new WebDriverWait(driver, Duration.ofMillis(10000)), url);
    }

    public Page(WebDriver driver, WebDriverWait wait) {
        this(driver, wait, "");
    }

    public Page() {
        this(new ChromeDriver(), "");
    }

    // Этот метод позволяет загрузить страницу по URL
    public void loadPage(String url) {
        driver.get(url);
    }

    // Этот метод позволяет нажать на кнопку
    public void clickButton(WebElement element, String xpath) {
        waitForPresenceOfElement(By.xpath(xpath));
        scrollIntoView(element);
        try {
            hoverOver(element, xpath);
            action.pause(2000).click(element).perform();
        } catch (StaleElementReferenceException | NoSuchElementException ex) {
            PageFactory.initElements(driver, this);
            hoverOver(element, xpath);
            action.pause(2000).click(element).perform();
        }
    }

    // Этот метод позволяет передвинуть курсор к какому-то элементу
    public void hoverOver(WebElement element, String xpath) {
        waitForPresenceOfElement(By.xpath(xpath));
        scrollIntoView(element);
        try {
            action.pause(2000).moveToElement(element).perform();
        } catch (StaleElementReferenceException | NoSuchElementException ex) {
            PageFactory.initElements(driver, this);
            action.pause(2000).moveToElement(element).perform();
        }
    }

    // Этот метод позволяет записать значение в текстовое поле
    public void writeTextFieldInput(WebElement element, String xpath, String inputString) {
        waitForPresenceOfElement(By.xpath(xpath));
        scrollIntoView(element);
        try {
            action.moveToElement(element).pause(2000).sendKeys(element, inputString).pause(1000).perform();
        } catch (StaleElementReferenceException | NoSuchElementException ex) {
            PageFactory.initElements(driver, this);
            action.moveToElement(element).pause(2000).sendKeys(element, inputString).pause(1000).perform();
        }
    }

    // Этот метод позволяет чекнуть несколько чекдобсов в заданном диапазоне
    public void clickCheckBoxes(WebElement parentElement, String parentElementXPath, String additionalXPath, int number) {
        waitForPresenceOfElement(By.xpath(parentElementXPath));
        scrollIntoView(parentElement);
        List<WebElement> childElements = parentElement.findElements(By.xpath(additionalXPath));
        int start = 0;
        int numberOfCheckedElements = 0;

        while (numberOfCheckedElements < number) {
            try {
                action.pause(500).click(childElements.get(start)).perform();
            } catch (StaleElementReferenceException | NoSuchElementException ex) {
                PageFactory.initElements(driver, this);
                childElements = parentElement.findElements(By.xpath(additionalXPath));
                action.pause(500).click(childElements.get(start)).perform();
            }

            if (childElements.get(start).isSelected())
                numberOfCheckedElements++;

            start++;
        }
    }

    // Этот метод возвращает количество элементов на странице в родительском элементе
    public int countElements(WebElement parentElement, String parentElementXPath, String additionalXPath) {
        waitForPresenceOfElement(By.xpath(parentElementXPath));
        js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
        int result;
        List<WebElement> elements;
        try {
            elements = parentElement.findElements(By.xpath(additionalXPath));
            result = elements.size();
        } catch (StaleElementReferenceException | NoSuchElementException ex) {
            PageFactory.initElements(driver, this);
            elements = parentElement.findElements(By.xpath(additionalXPath));
            result = elements.size();
        }
        js.executeScript("window.scrollTo(0, 0);");

        return result;
    }

    // Этот метод проверяет, содержит ли родительский элемент какой-либо другой элемент
    public boolean contains(WebElement parentElement, String parentElementXPath, String additionalXPath) {
        waitForPresenceOfElement(By.xpath(parentElementXPath));
        PageFactory.initElements(driver, this);
        boolean result = true;
        try {
            parentElement.findElement(By.xpath(additionalXPath));
        } catch (NoSuchElementException ex) {
            result = false;
        }
        return result;
    }

    // Этот метод возвращает n-ный элемент из списка
    public WebElement getNthElement(WebElement parentElement, String parentElementXPath, String additionalXPath, int n) {
        waitForPresenceOfElement(By.xpath(parentElementXPath));
        List<WebElement> elements;
        try {
            elements = parentElement.findElements(By.xpath(additionalXPath));
            return elements.get(n);
        } catch (StaleElementReferenceException | NoSuchElementException ex) {
            PageFactory.initElements(driver, this);
            elements = parentElement.findElements(By.xpath(additionalXPath));
            return elements.get(n);
        }
    }

    // Этот метод возвращает текст из подэлемента родительского элемента
    public String getTextFromSubelement(WebElement parentElement, String additionalXPath) {
        waitUntilPageIsLoaded();
        try {
            return parentElement.findElement(By.xpath(additionalXPath)).getText();
        } catch (StaleElementReferenceException | NoSuchElementException ex) {
            PageFactory.initElements(driver, this);
            return parentElement.findElement(By.xpath(additionalXPath)).getText();
        }
    }

    // Этот метод позволяет постранично пройтись по всем результатам выдачи в поисках элемента по его имени.
    // Возвращает искомый элемент или null, если такой элемент не был найден.
    public WebElement traverseSearchResults(WebElement parentElement, String parentElementXPath, String additionalXPath,
                                            String elementName, WebElement nextButton, String elementNameAdditionalXPath) {
        boolean continueSearch = true;

        while (continueSearch) {
            waitForPresenceOfElement(By.xpath(parentElementXPath));
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            PageFactory.initElements(driver, this);
            List<WebElement> elements = parentElement.findElements(By.xpath(additionalXPath));

            for (int i = 0; i < elements.size(); i++) {
                WebElement currentElement;
                String currentElementName;
                try {
                    currentElement = elements.get(i);
                    currentElementName = getTextFromSubelement(currentElement, elementNameAdditionalXPath);
                } catch (NoSuchElementException ex) {
                    PageFactory.initElements(driver, this);
                    currentElement = elements.get(i);
                    currentElementName = getTextFromSubelement(currentElement, elementNameAdditionalXPath);
                }
                if (currentElementName.equals(elementName))
                    return currentElement;
            }

            try {
                PageFactory.initElements(driver, this);
                scrollIntoView(nextButton);
                action.click(nextButton).perform();
            } catch (NoSuchElementException ex) {
                continueSearch = false;
            }
        }

        return null;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    public WebDriverWait getWait() {
        return wait;
    }

    public void setWait(WebDriverWait wait) {
        this.wait = wait;
    }

    public Actions getAction() {
        return action;
    }

    public void setAction(Actions action) {
        this.action = action;
    }

    // Этот метод проверяет, присутствует ли элемент на странице
    public void waitForPresenceOfElement(By by) {
        waitUntilPageIsLoaded();
        wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    // Этот метод проверяет каждую секунду в течение 50 секунд, загрузилась ли страница
    public void waitUntilPageIsLoaded() {
        for (int i = 0; i < 50; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }

            if (js.executeScript("return document.readyState").toString().equals("complete"))
                break;
        }
    }

    // Этот метод скроллит страницу для того, чтобы элемент был видимым в окне просмотра. Выравнивание по нижнему краю видимой зоны.
    public void scrollIntoView(WebElement element) {
        waitUntilPageIsLoaded();
        js.executeScript("arguments[0].scrollIntoView(false);", element);
    }

    // Этот метод позволяет переключаться между вкладками. Нумерация вкладок начинается с 0.
    public void switchTabs(int tabNumber) {
        List<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(tabNumber));
    }
}
