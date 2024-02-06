package pom.models;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

import static pom.data.Data.SHOP_BY_CATEGORY;

public class Home1Page extends Asserts {
    private final String HOME_PAGE_URL = "https://ecommerce-playground.lambdatest.io/";
    private final String HOME_PAGE_TITLE = "Your Store";

    private final Locator shopByCategoryButton = button("Shop by Category");
    private final Locator topCategoriesHeading = heading("Top categories");
    private final Locator topCategoriesMenus = dataId("217841");
    private final Locator componentsMenuLink = link("Components");


    public Home1Page(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public Page page() {
        return getPage();
    }

    @Override
    public String getUrl() {
        return HOME_PAGE_URL;
    }

    @Override
    public String getTitle() {
        return HOME_PAGE_TITLE;
    }

    public Locator getShopByCategoryButton() {
        return shopByCategoryButton;
    }

    public Home1Page clickShopByCategoryButton() {
        clickButton(SHOP_BY_CATEGORY);
        return this;
    }

    public Home1Page clickShopByCategoryButton1() {
        shopByCategoryButton.click();
        return this;
    }

    public Locator getTopCategoriesHeading() {
        return topCategoriesHeading;
    }

    public Locator getTopCategoriesMenus() {
        return topCategoriesMenus;
    }

    public Locator getComponentsMenu() {
        return componentsMenuLink;
    }

    public void clickComponentsMenu() {
        componentsMenuLink.click();
    }

}
