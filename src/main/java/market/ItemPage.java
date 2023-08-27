package market;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ItemPage extends Page {
    public static final String RATING_XPATH = "//div[@data-zone-name='productCardTitle']//div[@data-baobab-name='productActions']//div[@data-baobab-name='rating']";
    public static final String RATING_VALUE_ADDITIONAL_XPATH = "./span[1]";

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
