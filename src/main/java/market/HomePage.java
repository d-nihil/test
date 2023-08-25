package market;

import org.openqa.selenium.*;
import org.openqa.selenium.support.*;
import org.openqa.selenium.support.ui.*;

public class HomePage extends Page {
    public static final String CATALOGUE_BUTTON_XPATH = "//span[@class='_1z5kk' and contains(text(), 'Каталог')]";
    public static final String ELECTRONICS_FIELD_XPATH = "//ul[@role='tablist']/li/a/span[contains(text(), 'Электроника')]";
    public static final String SMARTPHONES_FIELD_XPATH = "//ul[@data-autotest-id='subItems']/li/div/a[contains(text(), 'Смартфоны')]";

    @FindBy(xpath = CATALOGUE_BUTTON_XPATH)
    private WebElement catalogueButton;

    @FindBy(xpath = ELECTRONICS_FIELD_XPATH)
    private WebElement electronicsButton;

    @FindBy(xpath = SMARTPHONES_FIELD_XPATH)
    private WebElement smartphonesButton;

    public HomePage(WebDriver driver, WebDriverWait wait, String url) {
        super(driver, wait, url);
        PageFactory.initElements(driver, this);
    }

    public WebElement getCatalogueButton() {
        return catalogueButton;
    }

    public WebElement getElectronicsField() {
        return electronicsButton;
    }

    public WebElement getSmartphonesField() {
        return smartphonesButton;
    }
}
