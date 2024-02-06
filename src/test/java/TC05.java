import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

public class TC05 {


    public static void main(String[] args) {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            page.navigate("https://www.walmart.com/");

            page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Savings")).click();
            page.getByTestId("horizontal-scroller-next").click();
            page.getByAltText("Clearance").click();
            page.getByAltText("Five dollars and under facet").click();
            page.locator("div[data-automation-id='product-price'] div span").innerText();


        }
    }
}
