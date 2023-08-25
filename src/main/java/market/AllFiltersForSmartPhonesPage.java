package market;


import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

public class AllFiltersForSmartPhonesPage extends Page {
    public static final String MAX_PRICE_TEXT_FIELD_XPATH = "//div[@id='glprice']/div/div[@data-prefix='до']/input[@data-auto='range-filter-input-max']";
    public static final String DROP_DOWN_SCREEN_SIZE_XPATH = "//button[@data-auto='accordion-btn']/h4[contains(text(), 'Диагональ экрана (точно)')]";
    public static final String MIN_SCREEN_SIZE_TEXT_FIELD_XPATH = "(//div[@data-prefix='от']/input[@data-auto='range-filter-input-min'])[2]";
    public static final String MANUFACTURERS_BOX_XPATH = "//div[@id='7893318']/div/div[2]";
    public static final String MANUFACTURERS_LIST_ADDITIONAL_XPATH = "./div/label/input[@type='checkbox']";
    public static final String SHOW_BUTTON_XPATH = "//a[contains(text(), 'Показать')]";

    @FindBy(xpath = MAX_PRICE_TEXT_FIELD_XPATH)
    private WebElement maxPriceTextField;

    @FindBy(xpath = DROP_DOWN_SCREEN_SIZE_XPATH)
    private WebElement dropDownScreenSize;

    @FindBy(xpath = MIN_SCREEN_SIZE_TEXT_FIELD_XPATH)
    private WebElement minScreenSizeTextField;

    @FindBy(xpath = MANUFACTURERS_BOX_XPATH)
    private WebElement manufacturersBox;

    @FindBy(xpath = SHOW_BUTTON_XPATH)
    private WebElement showButton;

    public AllFiltersForSmartPhonesPage(WebDriver driver, WebDriverWait wait, String url) {
        super(driver, wait, url);
        PageFactory.initElements(driver, this);
    }

    public AllFiltersForSmartPhonesPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
        PageFactory.initElements(driver, this);
    }

    public WebElement getMaxPriceTextField() {
        return maxPriceTextField;
    }

    public WebElement getDropDownScreenSize() {
        return dropDownScreenSize;
    }

    public WebElement getMinScreenSizeTextField() {
        return minScreenSizeTextField;
    }

    public WebElement getManufacturersBox() {
        return manufacturersBox;
    }

    public WebElement getShowButton() {
        return showButton;
    }
}
