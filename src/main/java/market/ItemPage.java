package market;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.NoSuchElementException;

public class ItemPage extends Page {
    public static final String RATING_XPATH = "(//div[@data-baobab-name='rating']/span[1])[2]";

    @FindBy(xpath = RATING_XPATH)
    private WebElement rating;

    public ItemPage(WebDriver driver, WebDriverWait wait, String url) {
        super(driver, wait, url);
        PageFactory.initElements(driver, this);
    }

    public ItemPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public WebElement getRating() {
        return rating;
    }
}
