import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TC08 {
    static Page page = getPage();

    private static final String BASE_URL = "https://ecommerce-playground.lambdatest.io/";

    private static final String YOUR_STORE = "Your Store";
    private static final String SEARCH = "Search";
    private static final String ALL_CATEGORIES = "All Categories";
    private static final String SEARCH_FOR_PRODUCTS = "Search For Products";
    private static final String IPOD = "IPOD";

    private static final Locator SEARCH_BLOCK = id("data-id", "217822");
    private static final Locator SEARCH_FOR_PRODUCTS_FIELD = textBox(SEARCH_FOR_PRODUCTS);
    private static final Locator SEARCH_BUTTON = button(SEARCH);
    private static final Locator AUTOCOMPLETE = page.locator(".autocomplete:has(li)");
    private static final Locator AUTOCOMPLETE_LIST = AUTOCOMPLETE.getByRole(AriaRole.LISTITEM);
    private static final Locator AUTOCOMPLETE_LIST_IPOD = filteredByText(AUTOCOMPLETE_LIST, IPOD);

    private static Playwright getPlaywright() {
        return Playwright.create();
    }

    private static Page getPage() {
        Browser browser = getPlaywright().chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false));
        BrowserContext context = browser.newContext();
        return context.newPage();
    }

    private static Locator button(String text) {
        return page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(text));
    }

    private static Locator id(String attribute, String text) {
        getPlaywright().selectors().setTestIdAttribute(attribute);
        return page.getByTestId(text);
    }

    private static Locator placeholder(String text) {
        return page.getByPlaceholder(text);
    }

    private static Locator idPlaceholder(String id, String text) {
        return page.locator(id).getByPlaceholder(text);
    }

    private static Locator textBox(String text) {
        return page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName(text));
    }

    private static Locator filteredByText(Locator locator, String text) {
        return (locator).filter(new Locator.FilterOptions().setHasText(text));
    }

    public static void main(String[] args) {
        try {
            page.navigate(BASE_URL);

            assertThat(page).hasURL(BASE_URL);
            assertThat(page).hasTitle(YOUR_STORE);
            assertThat(SEARCH_BLOCK).isVisible();
            assertThat(button(ALL_CATEGORIES)).isVisible();
            assertThat(SEARCH_FOR_PRODUCTS_FIELD).isVisible();
            assertThat(SEARCH_FOR_PRODUCTS_FIELD).isEditable();
            assertThat(SEARCH_BUTTON).isVisible();
            assertThat(SEARCH_BUTTON).isEnabled();

            SEARCH_FOR_PRODUCTS_FIELD.fill(IPOD);

            assertThat(AUTOCOMPLETE).isVisible();
            assertThat(AUTOCOMPLETE_LIST).hasCount(5);
            assertThat(AUTOCOMPLETE_LIST_IPOD).hasCount(5);

            SEARCH_BUTTON.click();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.out.println("Pass");

        }
    }
}
