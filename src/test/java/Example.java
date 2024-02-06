import com.microsoft.playwright.*;
import com.microsoft.playwright.options.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Example {
    public static void main(String[] args) {
//        try (Playwright playwright = Playwright.create()) {
//            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
//                    .setHeadless(false));
//            BrowserContext context = browser.newContext();
//            Page page = context.newPage();
//            page.navigate("https://petstore.octoperf.com/");
//            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Enter the Store")).click();
//            Locator l = page.getByText("Saltwater, Freshwater Various Breeds Various Breeds, Exotic Varieties Lizards, T");
//
//            assertThat(l).containsText("Saltwater, Freshwater");
//        }

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
            page.navigate("https://petstore.octoperf.com/");
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Enter the Store")).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign In")).click();
            page.locator("input[name = 'username']").click();
            page.locator("input[name = 'username']").fill("Tester1");
            page.locator("input[name='password']").click();
//            page.locator("input[name=\"password\"]").fill("vvv");
            page.fill("input[name=\"password\"]","vvv");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login")).click();
            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign Out"));

            assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign Out"))).isVisible();

        }
    }
}