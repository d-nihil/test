package market;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

public class SmartphonesPage extends Page {
    public static final String ALL_FILTERS_BUTTON_XPATH = "//a[@data-auto='allFiltersButton']/button";
    public static final String SMARTPHONES_BOX_XPATH = "//main[@aria-label='Результаты поиска']/div/div/div/div/div";
    public static final String SMARTPHONES_ADDITIONAL_XPATH = "./div";
    public static final String SMARTPHONE_NAME_ADDITIONAL_XPATH = ".//h3[@data-zone-name='title']/a/span";
    public static final String SORT_BY_PRICE_XPATH = "//button[@data-autotest-id='dprice']";
    public static final String NEXT_BUTTON_XPATH = "//div[@data-auto='pagination-next']/span[contains(text(), 'Вперёд')]";
    public static final String PREMIUM_OFFERS_ADDITIONAL_XPATH = ".//div[@data-zone-name='premium-offers-gallery']";

    @FindBy(xpath = ALL_FILTERS_BUTTON_XPATH)
    private WebElement allFiltersButton;

    @FindBy(xpath = SMARTPHONES_BOX_XPATH)
    private WebElement smartphonesBox;

    @FindBy(xpath = SORT_BY_PRICE_XPATH)
    private WebElement sortByPrice;

    @FindBy(xpath = NEXT_BUTTON_XPATH)
    private WebElement nextButton;

    public SmartphonesPage(WebDriver driver, WebDriverWait wait, String url) {
        super(driver, wait, url);
        PageFactory.initElements(driver, this);
    }

    public SmartphonesPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public WebElement getAllFiltersButton() {
        return allFiltersButton;
    }

    public WebElement getSmartphonesBox() {
        return smartphonesBox;
    }

    public WebElement getSortByPrice() {
        return sortByPrice;
    }

    public String getSmartphoneName(WebElement element) {
        return element.findElement(By.xpath(SMARTPHONE_NAME_ADDITIONAL_XPATH)).getText();
    }

    public WebElement getNextButton() {
        return nextButton;
    }
}
