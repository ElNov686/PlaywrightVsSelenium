import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import java.util.ArrayList;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TC03 {

//    TC-03
//    Go to https://naveenautomationlabs.com/opencart/
//    In the Search field input “phone”
//    Click Search button
//    Verify that at least one product is shown on the screen as a Search result

    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            page.navigate("https://naveenautomationlabs.com/opencart/");

            String input = "phone";

            page.getByPlaceholder("Search").fill(input);
            page.locator("button > i.fa-search").click();

            //search result == 1, not meet criteria "at least"
            assertThat(page.locator("#product-search div.product-layout")).hasCount(1);

            // search result != 0 (at least 1 result exists)
            assertThat(page.locator("#product-search div.product-layout")).not().hasCount(0);

            // search result contains "phone" != 0 (at least 1 result with word "phone" exists)
            assertThat(page.getByAltText("phone")).not().hasCount(0);

            assertThat(page.getByText("Showing")).isVisible();
        }

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://petstore.octoperf.com/");
            page.navigate("https://naveenautomationlabs.com/opencart/");
            page.getByPlaceholder("Search").click();
            page.getByPlaceholder("Search").fill("phone");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("")).click();
            page.locator("#content div").filter(new Locator.FilterOptions().setHasText("iPhone iPhone is a revolutionary new mobile phone that allows you to make a call")).nth(1).click();
            page.getByText("iPhone", new Page.GetByTextOptions().setExact(true)).click();
            page.navigate("https://naveenautomationlabs.com/opencart/index.php?route=product/search&search=phone");
            page.getByText("Showing 1 to 1 of 1 (1 Pages)").click();
        }

        //Add assertions to codegen https://github.com/microsoft/playwright/issues/14913
    }
    int[] arr;
}
