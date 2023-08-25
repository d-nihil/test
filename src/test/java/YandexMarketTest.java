import market.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/*
1. Открыть браузер и развернуть на весь экран.
2. Зайти на https://market.yandex.ru/
3. Открыть каталог
4. Навести курсок на поле "Электроника"
5. В списке справа выбрать "Смартфоны"
6. Выбрать "Все фильтры"
7. Задать максимальную цену 20000 рублей
8. Задать диагональ экрана от 3 дюймов
9. Выбрать не менее 5 любых производителей, среди популярных.
10. Нажать кнопку "Показать"
11. Проверить, что после пролистывания страницы в самый низ на ней 10 элементов.
12. Запомнить первый элемент в списке.
13. Изменить сортировку на другую (по цене)
14. Найти запомненный объект и нажать по его имени
15. Вывести цифровое значение его оценки
16. Закрыть браузер
*/

public class YandexMarketTest {
    public static void main(String[] args) throws InterruptedException {
        // Здесь вторым аргументом нужно передать расположение chromedriver.exe
        System.setProperty("webdriver.chrome.driver", "D:\\soft\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(10000));
        HomePage homePage = new HomePage(driver, wait, "https://market.yandex.ru/");

        // Открыть браузер и развернуть на весь экран.
        driver.manage().window().maximize();

        // Загрузить страницу https://market.yandex.ru/, нажать кнопку "Каталог", навести курсор на строку "Электроника" и кликнуть строку "Смартфоны"
        homePage.loadPage(homePage.getUrl());
        homePage.clickButton(homePage.getCatalogueButton(), HomePage.CATALOGUE_BUTTON_XPATH);
        homePage.hoverOver(homePage.getElectronicsField(), HomePage.ELECTRONICS_FIELD_XPATH);
        homePage.clickButton(homePage.getSmartphonesField(), HomePage.SMARTPHONES_FIELD_XPATH);

        // Нажать "Все фильтры", ввести максимальную цену 20000, минимальный размер экрана 3 дюйма, выбрать первых 5 производителей, нажать "Показать"
        SmartphonesPage smartphonesPage = new SmartphonesPage(driver, wait);
        smartphonesPage.clickButton(smartphonesPage.getAllFiltersButton(), SmartphonesPage.ALL_FILTERS_BUTTON_XPATH);
        AllFiltersForSmartPhonesPage allFiltersForSmartPhonesPage = new AllFiltersForSmartPhonesPage(driver, wait);
        allFiltersForSmartPhonesPage.writeTextFieldInput(allFiltersForSmartPhonesPage.getMaxPriceTextField(),
                AllFiltersForSmartPhonesPage.MAX_PRICE_TEXT_FIELD_XPATH, "20000");
        allFiltersForSmartPhonesPage.clickButton(allFiltersForSmartPhonesPage.getDropDownScreenSize(),
                AllFiltersForSmartPhonesPage.DROP_DOWN_SCREEN_SIZE_XPATH);
        allFiltersForSmartPhonesPage.writeTextFieldInput(allFiltersForSmartPhonesPage.getMinScreenSizeTextField(),
                AllFiltersForSmartPhonesPage.MIN_SCREEN_SIZE_TEXT_FIELD_XPATH, "3");
        allFiltersForSmartPhonesPage.clickCheckBoxes(allFiltersForSmartPhonesPage.getManufacturersBox(),
                AllFiltersForSmartPhonesPage.MANUFACTURERS_BOX_XPATH, AllFiltersForSmartPhonesPage.MANUFACTURERS_LIST_ADDITIONAL_XPATH, 5);
        allFiltersForSmartPhonesPage.clickButton(allFiltersForSmartPhonesPage.getShowButton(),
                AllFiltersForSmartPhonesPage.SHOW_BUTTON_XPATH);

        // Проверить, что на странице 10 элементов.
        // Первым подэлементом может быть блок "Популярные предложения". Если он присутствует, делаем декремент количества смартфонов.
        int numberOfSmartphones = smartphonesPage.countElements(smartphonesPage.getSmartphonesBox(),
                SmartphonesPage.SMARTPHONES_BOX_XPATH, SmartphonesPage.SMARTPHONES_ADDITIONAL_XPATH);
        if (smartphonesPage.contains(smartphonesPage.getSmartphonesBox(), SmartphonesPage.SMARTPHONES_BOX_XPATH,
                SmartphonesPage.PREMIUM_OFFERS_ADDITIONAL_XPATH)) {
            numberOfSmartphones--;
        }
        System.out.println("На странице 10 элементов: " + (numberOfSmartphones == 10));
        System.out.println("На странице " + (numberOfSmartphones) + " элементов");

        // Запомнить первый элемент в списке.
        // Первым подэлементом может быть блок "Популярные предложения". Если он присутствует, делаем инкремент начальной позиции.
        int start = 0;
        if (smartphonesPage.contains(smartphonesPage.getSmartphonesBox(), SmartphonesPage.SMARTPHONES_BOX_XPATH,
                SmartphonesPage.PREMIUM_OFFERS_ADDITIONAL_XPATH)) {
            start++;
        }
        WebElement firstElement = smartphonesPage.getNthElement(smartphonesPage.getSmartphonesBox(),
                SmartphonesPage.SMARTPHONES_BOX_XPATH, SmartphonesPage.SMARTPHONES_ADDITIONAL_XPATH, start);
        String firstElementName = smartphonesPage.getSmartphoneName(firstElement);
        System.out.println(firstElementName);

        // Изменить сортировку на другую (по цене)
        smartphonesPage.clickButton(smartphonesPage.getSortByPrice(), SmartphonesPage.SORT_BY_PRICE_XPATH);

        //Найти запомненный объект и нажать по его имени
        WebElement savedElement = smartphonesPage.traverseSearchResults(smartphonesPage.getSmartphonesBox(), SmartphonesPage.SMARTPHONES_BOX_XPATH, SmartphonesPage.SMARTPHONES_ADDITIONAL_XPATH,
                firstElementName, smartphonesPage.getNextButton(), SmartphonesPage.SMARTPHONE_NAME_ADDITIONAL_XPATH);

        // Вывести цифровое значение оценки элемента
        ItemPage itemPage = new ItemPage(driver, wait);
        if (savedElement != null) {
            smartphonesPage.clickButton(savedElement.findElement(By.xpath(SmartphonesPage.SMARTPHONE_NAME_ADDITIONAL_XPATH)),
                    SmartphonesPage.SMARTPHONE_NAME_ADDITIONAL_XPATH);
            System.out.println("Оценка смартфона " + ": " + itemPage.getRating().getText());
        } else {
            System.out.println("Искомый смартфон не обнаружен.");
        }

        driver.quit();
    }
}
