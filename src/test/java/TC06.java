import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;

import java.util.ArrayList;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;


public class TC06 {
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


    public static void main(String[] args) {
        List<Double> minPrices = new ArrayList<>();

        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(
                    new BrowserType.LaunchOptions()
                            .setHeadless(false)
            );
            BrowserContext context = browser.newContext();
            Page page = context.newPage();

            page.navigate("https://www.target.com/");
            page.getByLabel("Deals").click();
            page.getByRole(AriaRole.LINK).filter(new Locator.FilterOptions().setHasText("Clearance")).click();
            page.getByLabel("Filters").click();
            page.getByRole(AriaRole.BUTTON).filter(new Locator.FilterOptions().setHasText("Price")).click();
            page.locator("[name='minPriceValue']").fill("0");
            page.locator("[data-test='priceFacetMax']").fill("5");
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Apply")).click();
            page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("See results")).click();

//            page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Explore more of what’s going on right now")).click();
//            page.locator("[data-test='page-title']").click();
//            page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Explore more of what’s going on right now")).click();

            List<Locator> unloadCards = page.locator("[data-test='@web/site-top-of-funnel/ProductCardPlaceholder']").all();
            while (unloadCards.size() != 0) {
                Locator firstUnloaded = page.locator("div[data-test='@web/site-top-of-funnel/ProductCardPlaceholder']").first();
                if(firstUnloaded.count() == 1) {
                    firstUnloaded.hover();
                }
                unloadCards = page.locator("[data-test='@web/site-top-of-funnel/ProductCardPlaceholder']").all();
            }

            assertThat(page.locator("[data-test='current-price']>span:not(.h-text-md)")).hasCount(24);

            List<String> prices = page.locator("[data-test='current-price']>span:not(.h-text-md)").allInnerTexts();
            for (String currentPrice : prices) {
                minPrices.add(
                        Double.parseDouble(
                                currentPrice
                                        .split("-")[0]
                                        .split("\\$")[1]
                                        .trim()));
            }

            System.out.println("=======================");

            //assert average
            int sum = 0;
            for (Double price : minPrices) {
                sum += price;
            }

            assert (sum / minPrices.size() <= 5);

            //assert each
            for (Double price : minPrices) {
                System.out.println(price);
                assert (price >= 0);
                assert (price <= 5);
            }


            page.getByRole(AriaRole.BUTTON).filter(new Locator.FilterOptions().setHasText("page")).click();

            assertThat(page.getByLabel("page ")).not().hasCount(0);
            List<String> pages = page.getByLabel("page ").allInnerTexts();
            pages.removeIf(String::isEmpty);
            System.out.println(pages);





            //Page 2
            if(page.getByLabel("page 2").count() == 1) {
                page.getByLabel("page 2").click();
            }

//            page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Explore more of what’s going on right now")).click();
//            page.locator("[data-test='page-title']").click();
//            page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Explore more of what’s going on right now")).click();

            List<Locator> unloadCards2 = page.locator("[data-test='@web/site-top-of-funnel/ProductCardPlaceholder']").all();
            while (unloadCards2.size() != 0) {
                Locator firstUnloaded = page.locator("div[data-test='@web/site-top-of-funnel/ProductCardPlaceholder']").first();
                if(firstUnloaded.count() == 1) {
                    firstUnloaded.hover();
                }
                unloadCards2 = page.locator("[data-test='@web/site-top-of-funnel/ProductCardPlaceholder']").all();
            }

            assertThat(page.locator("[data-test='current-price']>span:not(.h-text-md)")).hasCount(24);

            List<String> prices2 = new ArrayList<>();
            List<Double> minPrices2 = new ArrayList<>();

            prices2 = page.locator("[data-test='current-price']>span:not(.h-text-md)").allInnerTexts();
            for (String currentPrice : prices2) {
                minPrices2.add(Double.parseDouble(currentPrice.split("-")[0].split("\\$")[1].trim()));
            }


            System.out.println("=======================");

            //assert average
            sum = 0;
            for (Double price : minPrices2) {
                sum += price;
            }

            assert (sum / minPrices2.size() <= 5);

            //assert each
            for (Double price : minPrices2) {
                System.out.println(price);
                assert (price >= 0);
                assert (price <= 5);
            }




            //Page Last
            page.getByRole(AriaRole.BUTTON).filter(new Locator.FilterOptions().setHasText("page")).click();

            assertThat(page.getByLabel("page ")).not().hasCount(0);

            List<String> pagesLast = page.getByLabel("page ").allInnerTexts();
            pagesLast.removeIf(String::isEmpty);
            System.out.println(pagesLast);

            String lastPage = pagesLast.get(pagesLast.size() - 1);

            if(page.getByLabel(lastPage).count() == 1) {
                page.getByLabel(lastPage).click();
            }


//            page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Explore more of what’s going on right now")).click();
//            page.locator("[data-test='page-title']").click();
//            page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Explore more of what’s going on right now")).click();

            List<Locator> unloadCardsLast = page.locator("[data-test='@web/site-top-of-funnel/ProductCardPlaceholder']").all();
            while (unloadCardsLast.size() != 0) {
                Locator firstUnloaded = page.locator("div[data-test='@web/site-top-of-funnel/ProductCardPlaceholder']").first();
                if(firstUnloaded.count() == 1) {
                    firstUnloaded.hover();
                }
                unloadCardsLast = page.locator("[data-test='@web/site-top-of-funnel/ProductCardPlaceholder']").all();
            }


            int lastPageCardsAmount = page.locator("[data-test='current-price']>span:not(.h-text-md)").count();

            assert(lastPageCardsAmount >= 1);
            assert(lastPageCardsAmount <= 24);


            List<String> pricesLast = new ArrayList<>();
            List<Double> minPricesLast = new ArrayList<>();

            pricesLast = page.locator("[data-test='current-price']>span:not(.h-text-md)").allInnerTexts();
            for (String currentPrice : pricesLast) {
                minPricesLast.add(Double.parseDouble(currentPrice.split("-")[0].split("\\$")[1].trim()));
            }


            System.out.println("=======================");

            //assert average
            sum = 0;
            for (Double price : minPricesLast) {
                sum += price;
            }

            assert (sum / minPricesLast.size() <= 5);

            //assert each
            for (Double price : minPricesLast) {
                System.out.println(price);
                assert (price >= 0);
                assert (price <= 5);
            }
        }

    }

}
