package pom;

import com.microsoft.playwright.assertions.LocatorAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pom.models.Home1Page;
import pom.models.HomePage;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static pom.data.Data.*;

public class POMTest extends BaseTest {

    private static final String PRODUCT_DETAILS_ID = "entry_216826";
    private static final String ITEMS_IN_CART = "div[data-id = '217825'] span.cart-item-total";
    private static final String CART_SIDE_PANEL_ID = "cart-total-drawer";
    private static final String CHECKOUT_CART_ID = "checkout-cart";
    private static final String PRODUCT_DETAILS_TD = "#checkout-cart table td.text-left:has(a)";
    private static final String ACCOUNT_OPTIONS = "input[name='account']~label";

    //    private static void clickTopCategoriesMenu(String text) {
//        getLink(text).click();
//    }
//
//
//    private static Locator getItemsInCart() {
//        return page.locator(ITEMS_IN_CART);
//    }
//
//
//    private static Locator getProductDetailsTd() {
//        return page.locator(PRODUCT_DETAILS_TD);
//    }
//
//    private static Locator getImageInProductDetails() {
//        return page.locator("#checkout-cart")
//                .getByRole(AriaRole.CELL, new Locator.GetByRoleOptions()
//                        .setName("HTC Touch HD").setExact(true)
//                )
//                .getByRole(AriaRole.LINK);
//    }
//
//    static HomePage homePage = new HomePage(getPage());
//
    @Test
    public void addFirstProductInComponentsAndCheckout() {
        /*
         * Step-by-step test approach
         * 1. Navigate to eCommerce Playground Website.
         *      1. Verify the user is navigated to the eCommerce website.
         *      2. Verify page title is Your Store.
         *      3. Verify Shop by Category menu is visible.
         */

        HomePage homePage = new HomePage(getPage(), getPlaywright());
        Home1Page homePage1 = new Home1Page(getPage(), getPlaywright());

        //1
        assertThat(homePage.page()).hasURL(BASE_URL);
        assertThat(homePage.page()).hasTitle(YOUR_STORE);
        assertThat(homePage.button(SHOP_BY_CATEGORY)).isVisible();
        assertThat(homePage.button(SHOP_BY_CATEGORY)).isEnabled();

        //2
        homePage.assertUrl(BASE_URL);
        homePage.assertTitle(YOUR_STORE);
        homePage.assertThatButtonIsVisible(SHOP_BY_CATEGORY);
        homePage.assertThatButtonIsEnabled(SHOP_BY_CATEGORY);
        homePage.assertThatButton_IsVisible_IsEnabled(SHOP_BY_CATEGORY);

        //3
        assertThat(homePage1.page()).hasURL(homePage1.getUrl());
        assertThat(homePage1.page()).hasTitle(homePage1.getTitle());
        assertThat(homePage1.getShopByCategoryButton()).isVisible();
        assertThat(homePage1.getShopByCategoryButton()).isEnabled();

        //4
        Assertions.assertEquals(BASE_URL, homePage.getHomePageUrl());
        Assertions.assertEquals(YOUR_STORE, homePage.getHomePageTitle());

        Assertions.assertEquals(homePage1.getUrl(), getPage().url());
        Assertions.assertEquals(homePage1.getTitle(), homePage1.page().title());




        //=================================================================
        /*
         * 2. Click on Shop by Category.
         *      1. Verify Top categories is shown to the user.
         */
        homePage.clickButton(SHOP_BY_CATEGORY);
        //homePage1.clickShopByCategoryButton();

        //1
        assertThat(homePage.heading(TOP_CATEGORIES)).isVisible();
        assertThat(homePage.dataId(TOP_CATEGORIES_MENUS_ID)).isVisible();
        assertThat(homePage.dataId(TOP_CATEGORIES_MENUS_ID)).hasText(
                TOP_CATEGORIES_MENUS_TEXT,
                new LocatorAssertions.HasTextOptions().setUseInnerText(true)
        );
        assertThat(homePage.link(COMPONENTS)).isVisible();
        assertThat(homePage.link(COMPONENTS)).isEnabled();

        //2
        homePage.assertThatHeadingIsVisible(TOP_CATEGORIES);
        homePage.assertThatDataIdIsVisible(TOP_CATEGORIES_MENUS_ID);
        homePage.assertDataIdHasText(TOP_CATEGORIES_MENUS_ID, TOP_CATEGORIES_MENUS_TEXT);
        homePage.assertThatLinkIsVisible(COMPONENTS);
        homePage.assertThatLinkIsEnabled(COMPONENTS);

        //3
        assertThat(homePage1.getTopCategoriesHeading()).isVisible();
        assertThat(homePage1.getTopCategoriesMenus()).isVisible();
        assertThat(homePage1.getTopCategoriesMenus()).hasText(
                TOP_CATEGORIES_MENUS_TEXT,
                new LocatorAssertions.HasTextOptions().setUseInnerText(true));
        assertThat(homePage1.getComponentsMenu()).isVisible();
        assertThat(homePage1.getComponentsMenu()).isEnabled();

    }

//
//
//
//        //3	Click on the Components category.
//        //      1.Verify the user is redirected to the Components page
//        //      2.Verify the page title is ‘Components’
//        //      3.Verify ‘HTC Touch HD’ is shown in the list.
//
//        clickTopCategoriesMenu(COMPONENTS);
//
//        assertThat(page).hasURL(COMPONENTS_PAGE_URL);
//        assertThat(page).hasTitle(COMPONENTS);
//        assertThat(getHeading(COMPONENTS)).isVisible();
//
//        assertThat(getId(COMPONENTS_LIST_ID)).containsText(HTC_TOUCH_HD);
//        assertThat(getExactLink(HTC_TOUCH_HD)).isVisible();
//        assertThat(getExactLink(HTC_TOUCH_HD)).hasCount(1);
//
//        //4	Click on the first product, ‘HTC Touch HD’
//        //      1.Verify the user is navigated to the ‘HTC Touch HD’ product details page.
//        //      2.Verify page title is ‘HTC Touch HD’
//        //      3.Verify Availability is shown as ‘In Stock’
//        //      4.Verify the ‘ADD To CART’ button is enabled.
//
//        clickExactLink(HTC_TOUCH_HD);
//
//        assertThat(page).hasURL(HTC_TOUCH_HD_PAGE_URL);
//        assertThat(page).hasTitle(HTC_TOUCH_HD);
//        assertThat(getHeading(HTC_TOUCH_HD)).isVisible();
//        assertThat(getId(PRODUCT_DETAILS_ID)).containsText(IN_STOCK);
//        assertThat(getButton(ADD_TO_CART)).isEnabled();
//
//        //5	Click on the ‘ADD To CART’ button.
//        //      1.Verify product is successfully added to the cart.
//
//        clickButton(ADD_TO_CART);
//
//        assertThat(getAlert()).isVisible();
//        assertThat(getAlert()).hasCount(1);
//        assertThat(getAlert()).containsText(ONE_ITEM);
//        assertThat(getAlert()).containsText(HTC_TOUCH_HD_ADD_TO_CART_SUCCESS_MSG);
//
//        clickButton(CLOSE);
//
//        //6	Click on the ‘Cart’ icon.
//        //      1.Verify the Cart side panel is open
//        //      2.Verify the Checkout button is shown to the user
//
//        assertThat(getItemsInCart()).hasText(ONE);
//
//        clickButton(ONE);
//
//        assertThat(getId(CART_SIDE_PANEL_ID)).isVisible();
//        assertThat(getButton(CHECKOUT)).isVisible();
//        assertThat(getButton(CHECKOUT)).isEnabled();
//
//        //7.	Click on the ‘Checkout’ button.
//        //      1.Verify the user is redirected to the Checkout page.
//        //      2.Verify product details are shown in the right side pane.
//        //      3.Since you didn’t log into the application on the right-hand side,
//        //      you will also see the Registration/Login/Guest flow.
//
//        clickButton(CHECKOUT);
//
//        assertThat(page).hasURL(CHECKOUT_PAGE_URL);
//        assertThat(page).hasTitle(CHECKOUT);
//
//        assertThat(getId(CART_SIDE_PANEL_ID)).isVisible();
//        assertThat(getId(CHECKOUT_CART_ID)).isVisible();
//        assertThat(getId(CHECKOUT_CART_ID)).not().isEmpty();
//        assertThat(getImageInProductDetails()).isVisible();
//        assertThat(page.locator(PRODUCT_DETAILS_TD)).isVisible();
//        assertThat(page.locator(PRODUCT_DETAILS_TD)).hasText(HTC_TOUCH_HD_DETAILS);
//        assertThat(page.locator(ACCOUNT_OPTIONS).nth(0)).hasText(LOGIN);
//        assertThat(page.locator(ACCOUNT_OPTIONS).nth(1)).hasText(REGISTER_ACCOUNT);
//        assertThat(page.locator(ACCOUNT_OPTIONS).nth(2)).hasText(GUEST_CHECKOUT);
//
//    } catch(
//    Exception e)
//
//    {
//        e.printStackTrace();
//    } finally
//
//    {
//        System.out.println("Pass");
//
//
//    }

}
