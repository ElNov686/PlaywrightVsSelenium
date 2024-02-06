import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TC06Refactored {

    //TC-06
    //Go to https://www.target.com/
    //Click on the Deals menu
    //Click on the Clearance submenu
    //Click on the Filter icon
    //Click on the Price filter
    //Set the filter range as “0 to 5”
    //Click Apply
    //Click See Results
    //Verify that all shown products on page #1 have prices, which are  less or equal to $5
    //Click on the Page # 2
    //Verify that all shown products on page #2 have prices, which are  less or equal to $5
    //Click on the last page
    //Verify that all shown products on the last page have prices, which are  less or equal to $5

    //Page
    private static Page page = getPage();

    //Locators
    private static final Locator MIN_PRICE_FIELD = page.locator("[name='minPriceValue']");
    private static final Locator MAX_PRICE_FIELD = page.locator("[data-test='priceFacetMax']");
    private static final Locator ALL_CARDS_ON_PAGE = page.locator(".dOpyUp");
    private static final Locator UNLOADED_CARDS =
            page.locator("[data-test='@web/site-top-of-funnel/ProductCardPlaceholder']");
    private static final Locator PRICES =
            page.locator("[data-test='current-price']>span:not(.h-text-md)");

    //Constants
    private static final String TARGET_BASE_URL = "https://www.target.com/";

    private static final String DEALS = "Deals";
    private static final String CLEARANCE = "Clearance";
    private static final String FILTERS = "Filters";
    private static final String PRICE = "Price";
    private static final String APPLY = "Apply";
    private static final String SEE_RESULTS = "See results";
    private static final String PAGES = "page";
    private static final String PAGES_LIST = "page ";

    //data
    private static final String MIN_PRICE = "0";
    private static final String MAX_PRICE = "5";
    private static final String PAGE_ONE = "page 1";
    private static final String PAGE_TWO = "page 2";
    private static final String PAGE_LAST = "";
    private static final List<String> PAGES_PROVIDER = List.of(PAGE_ONE, PAGE_TWO, PAGE_LAST);

    private static Page getPage() {
        Playwright playwright = Playwright.create();
        Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        BrowserContext context = browser.newContext();

        return context.newPage();
    }

    private static void openUrl(String url) {
        page.navigate(url);
    }

    private static Locator getLabel(String label) {
        return page.getByLabel(label);
    }

    private static List<String> getInnerText(String label) {
        return getLabel(label).allInnerTexts();
    }

    private static List<String> getInnerText(Locator locator) {
        return locator.allInnerTexts();
    }

    private static void clickLabel(String label) {
        page.getByLabel(label).click();
    }

    private static void clickLink(String text) {
        page.getByRole(AriaRole.LINK)
                .filter(new Locator.FilterOptions().setHasText(text))
                .click();
    }

    private static void clickButton(String text) {
        page.getByRole(AriaRole.BUTTON)
                .filter(new Locator.FilterOptions().setHasText(text))
                .click();
    }

    private static void inputMinPrice(String minPrice) {
        MIN_PRICE_FIELD.fill(minPrice);
    }

    private static void inputMaxPrice(String maxPrice) {
        MAX_PRICE_FIELD.fill(maxPrice);
    }

    private static List<Locator> getUnloadedCards() {
        return UNLOADED_CARDS.all();
    }

    private static void loadCards() {
        List<Locator> unloadedCards = getUnloadedCards();

        while (unloadedCards.size() != 0 && unloadedCards.get(0).count() == 1) {
            try {
                unloadedCards.get(0).hover();
            } catch (Exception e) {
                System.out.println("Can't hover");
            }
            unloadedCards = getUnloadedCards();
        }
    }

    private static List<Double> getMinPricesList() {
        List<Double> minPrices = new ArrayList<>();
        List<String> prices = getInnerText(PRICES);

        for (String currentPrice : prices) {
            minPrices.add(Double.parseDouble(
                    currentPrice
                            .split("-")[0]
                            .split("\\$")[1]
                            .trim()));
        }
        System.out.println(minPrices);

        return minPrices;
    }

    private static List<String> getPagesList() {
        clickButton(PAGES);
        List<String> pagesList = getInnerText(PAGES_LIST);
        pagesList.removeIf(String::isEmpty);

        return pagesList;
    }

    private static String getLastPage() {
        List<String> pages = getPagesList();
        int lastPageIndex = pages.size() - 1;

        return pages.get(lastPageIndex);
    }

    public static void main(String[] args) {
        //run test multiple (3) times to check the stability
        for (int i = 0; i < 3; i++) {

            //data provider
            for (String pageNumber : PAGES_PROVIDER) {
                int pricesChecked = 0;

                //test each page independently
                try {
                    openUrl(TARGET_BASE_URL);
                    clickLabel(DEALS);
                    clickLink(CLEARANCE);
                    clickLabel(FILTERS);
                    clickButton(PRICE);
                    inputMinPrice(MIN_PRICE);
                    inputMaxPrice(MAX_PRICE);
                    clickButton(APPLY);
                    clickButton(SEE_RESULTS);

                    //get the last page number
                    if (pageNumber.equals(PAGE_LAST)) {
                        pageNumber = getLastPage();
                    }
                    //click on page if page is NOT page 1
                    else if (!pageNumber.equals(PAGE_ONE)) {
                        clickButton(PAGES);
                        clickLabel(pageNumber);
                    }

                    loadCards();

                    //confirm all prices loaded
                    assertThat(PRICES).hasCount(ALL_CARDS_ON_PAGE.count());
                    pricesChecked = PRICES.count();

                    //assert each min price is in range
                    for (Double minPrice : getMinPricesList()) {
                        Assertions.assertTrue(minPrice >= 0 && minPrice <= 5);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                finally {
                    System.out.println("testing " + pageNumber + "\t\t\t Prices checked " + pricesChecked);
                    System.out.println("================================================================");
                }
            }
        }
    }
}
