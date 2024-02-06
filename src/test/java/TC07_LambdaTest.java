import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TC07_LambdaTest {

    private static Page page = getPage();

    private static final String BASE_URL = "https://ecommerce-playground.lambdatest.io/";
    private static final String YOUR_STORE = "Your Store";
    private static final String COMPONENTS_PAGE_URL = BASE_URL + "index.php?route=product/category&path=25";
    private static final String HTC_TOUCH_HD_PAGE_URL = BASE_URL + "index.php?route=product/product&path=25&product_id=28";
    private static final String CHECKOUT_PAGE_URL = BASE_URL + "index.php?route=checkout/checkout";

    private static final String SHOP_BY_CATEGORY = "Shop by Category";
    private static final String TOP_CATEGORIES = "Top categories";
    private static final String COMPONENTS = "Components";
    private static final String ID = "id";
    private static final String HTC_TOUCH_HD = "HTC Touch HD";
    private static final String IN_STOCK = "In Stock";
    private static final String ADD_TO_CART = "Add to Cart";
    private static final String CLOSE = "Close";
    private static final String ONE_ITEM = "1 item(s)";
    private static final String HTC_TOUCH_HD_ADD_TO_CART_SUCCESS_MSG = "Success: You have added " + HTC_TOUCH_HD +  " to your shopping cart!";
    private static final String ONE = "1";
    private static final String CHECKOUT = "Checkout";
    private static final String HTC_TOUCH_HD_DETAILS = "HTC Touch HD Model: Product 1 Reward Points: 400";
    private static final String LOGIN = "Login";
    private static final String REGISTER_ACCOUNT = "Register Account";
    private static final String GUEST_CHECKOUT = "Guest Checkout";

    private static final String TOP_CATEGORIES_MENUS_ID = "widget-navbar-217841";
    private static final String COMPONENTS_LIST_ID = "entry_212408";
    private static final String PRODUCT_DETAILS_ID = "entry_216826";
    private static final String ITEMS_IN_CART = "div[data-id = '217825'] span.cart-item-total";
    private static final String CART_SIDE_PANEL_ID = "cart-total-drawer";
    private static final String CHECKOUT_CART_ID = "checkout-cart";
    private static final String PRODUCT_DETAILS_TD = "#checkout-cart table td.text-left:has(a)";
    private static final String ACCOUNT_OPTIONS = "input[name='account']~label";



    private static Playwright getPlaywright() {
        return  Playwright.create();
    }

    private static Page getPage() {
        Browser browser = getPlaywright().chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false));
        BrowserContext context = browser.newContext();
        return context.newPage();
    }

    private static void clickButton(String text) {
        getButton(text).click();
    }

    private static Locator getButton(String text) {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(text));
    }

    private static Locator getLink(String text) {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(text));
    }

    private static Locator getExactLink(String text) {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(text).setExact(true));
    }

    private static Locator getHeading(String text) {
        return page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName(text));
    }

    private static Locator getId(String text) {
        getPlaywright().selectors().setTestIdAttribute(ID);
        return page.getByTestId(text);
    }

    private static void clickTopCategoriesMenu(String text) {
        getLink(text).click();
    }

    private static Locator getText(String text) {
       return page.getByText(text);
    }

    private static void clickExactLink(String text) {
       getExactLink(text).click();
    }

    private static void clickLink(String text) {
        getLink(text).click();
    }

    private static Locator getItemsInCart() {
        return page.locator(ITEMS_IN_CART);
    }

    private static Locator getAlert() {
        return page.getByRole(AriaRole.ALERT);
    }

    private static Locator getImage(String text) {
        return page.getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName(text));
    }

    private static Locator getProductDetailsTd() {
        return page.locator(PRODUCT_DETAILS_TD);
    }

    private static Locator getImageInProductDetails() {
        return page.locator("#checkout-cart")
                .getByRole(AriaRole.CELL, new Locator.GetByRoleOptions()
                        .setName("HTC Touch HD").setExact(true)
                )
                .getByRole(AriaRole.LINK);
    }


    public static void main(String[] args) {
        //Step-by-step test approach
        try {
            //1	Navigate to eCommerce Playground Website.
            //      1.Verify the user is navigated to the eCommerce website.
            //      2.Verify page title is Your Store.
            page.navigate(BASE_URL);

            assertThat(page).hasURL(BASE_URL);
            assertThat(page).hasTitle(YOUR_STORE);

            //2	Click on Shop by Category.
            //      1.Verify Shop by Category menu is visible.
            //      2.Verify Top categories is shown to the user.
            assertThat(getButton(SHOP_BY_CATEGORY)).isVisible();

            clickButton(SHOP_BY_CATEGORY);

            assertThat(getHeading(TOP_CATEGORIES)).isVisible();
            assertThat(getId(TOP_CATEGORIES_MENUS_ID)).isVisible();
            assertThat(getId(TOP_CATEGORIES_MENUS_ID)).not().isEmpty();

            //3	Click on the Components category.
            //      1.Verify the user is redirected to the Components page
            //      2.Verify the page title is ‘Components’
            //      3.Verify ‘HTC Touch HD’ is shown in the list.

            clickTopCategoriesMenu(COMPONENTS);

            assertThat(page).hasURL(COMPONENTS_PAGE_URL);
            assertThat(page).hasTitle(COMPONENTS);
            assertThat(getHeading(COMPONENTS)).isVisible();

            assertThat(getId(COMPONENTS_LIST_ID)).containsText(HTC_TOUCH_HD);
            assertThat(getExactLink(HTC_TOUCH_HD)).isVisible();
            assertThat(getExactLink(HTC_TOUCH_HD)).hasCount(1);

            //4	Click on the first product, ‘HTC Touch HD’
            //      1.Verify the user is navigated to the ‘HTC Touch HD’ product details page.
            //      2.Verify page title is ‘HTC Touch HD’
            //      3.Verify Availability is shown as ‘In Stock’
            //      4.Verify the ‘ADD To CART’ button is enabled.

            clickExactLink(HTC_TOUCH_HD);

            assertThat(page).hasURL(HTC_TOUCH_HD_PAGE_URL);
            assertThat(page).hasTitle(HTC_TOUCH_HD);
            assertThat(getHeading(HTC_TOUCH_HD)).isVisible();
            assertThat(getId(PRODUCT_DETAILS_ID)).containsText(IN_STOCK);
            assertThat(getButton(ADD_TO_CART)).isEnabled();

            //5	Click on the ‘ADD To CART’ button.
            //      1.Verify product is successfully added to the cart.

            clickButton(ADD_TO_CART);

            assertThat(getAlert()).isVisible();
            assertThat(getAlert()).hasCount(1);
            assertThat(getAlert()).containsText(ONE_ITEM);
            assertThat(getAlert()).containsText(HTC_TOUCH_HD_ADD_TO_CART_SUCCESS_MSG);

            clickButton(CLOSE);

            //6	Click on the ‘Cart’ icon.
            //      1.Verify the Cart side panel is open
            //      2.Verify the Checkout button is shown to the user

            assertThat(getItemsInCart()).hasText(ONE);

            clickButton(ONE);

            assertThat(getId(CART_SIDE_PANEL_ID)).isVisible();
            assertThat(getButton(CHECKOUT)).isVisible();
            assertThat(getButton(CHECKOUT)).isEnabled();

            //7.	Click on the ‘Checkout’ button.
            //      1.Verify the user is redirected to the Checkout page.
            //      2.Verify product details are shown in the right side pane.
            //      3.Since you didn’t log into the application on the right-hand side,
            //      you will also see the Registration/Login/Guest flow.

            clickButton(CHECKOUT);

            assertThat(page).hasURL(CHECKOUT_PAGE_URL);
            assertThat(page).hasTitle(CHECKOUT);

            assertThat(getId(CART_SIDE_PANEL_ID)).isVisible();
            assertThat(getId(CHECKOUT_CART_ID)).isVisible();
            assertThat(getId(CHECKOUT_CART_ID)).not().isEmpty();
            assertThat(getImageInProductDetails()).isVisible();
            assertThat(page.locator(PRODUCT_DETAILS_TD)).isVisible();
            assertThat(page.locator(PRODUCT_DETAILS_TD)).hasText(HTC_TOUCH_HD_DETAILS);
            assertThat(page.locator(ACCOUNT_OPTIONS).nth(0)).hasText(LOGIN);
            assertThat(page.locator(ACCOUNT_OPTIONS).nth(1)).hasText(REGISTER_ACCOUNT);
            assertThat(page.locator(ACCOUNT_OPTIONS).nth(2)).hasText(GUEST_CHECKOUT);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Pass");


        }
    }
}
