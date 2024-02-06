import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TC04 {
    //TC-04
    //Go to https://naveenautomationlabs.com/opencart/
    //Click on Components -> Monitors menu
    //Get the amount of Monitors products available in the store
    //Hover over the Components menu
    //Verify that the drop-down menu for the section Monitors shows the same amount  of Monitors products available in the store

    public static void main(String[] args) {
        try(Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();

            Page page = context.newPage();

            page.navigate("https://naveenautomationlabs.com/opencart/");

            Locator componentsMenu = page
                    .locator("#menu")
                    .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions().setName("Components"));
            Locator monitorsMenu = page.locator(".dropdown-menu")
                    .getByRole(AriaRole.LINK, new Locator.GetByRoleOptions()
                            .setName("Monitors")
                            .setExact(false)
                    );

            componentsMenu.hover();
            monitorsMenu.click();

            int productsAmount = page.locator(".product-layout").count();

            componentsMenu.hover();
            String text = monitorsMenu.innerText();

            text = text.replace("(", ",").split(",")[1];
            text = text.substring(0, text.length() - 1);

            assert(String.valueOf(productsAmount).equals(text));

            assertThat(monitorsMenu)
                    .hasText("Monitors (" + productsAmount + ")");
        }

//        try (Playwright playwright = Playwright.create()) {
//            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
//                    .setHeadless(false));
//            BrowserContext context = browser.newContext();
//            Page page = context.newPage();
//            page.navigate("https://naveenautomationlabs.com/opencart/");
//            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Monitors (2)")).click();
//            page.locator("#content div").filter(new Locator.FilterOptions().setHasText("Apple Cinema 30\" The 30-inch Apple Cinema HD Display delivers an amazing 2560 x ")).first().click();
//            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Monitors (2)").setExact(true)).click();
//        }
    }
}
