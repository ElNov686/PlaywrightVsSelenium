package tmobile1;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Assertions;

import java.util.ArrayList;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static tmobile1.Constants.*;

public abstract class BaseTest {
    Playwright playwright = Playwright.create();
    Browser browser = playwright
            .chromium()
            .launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));
    BrowserContext context = browser.newContext();
    Page page = context.newPage();

    public void open(String url) {
        page.navigate(url);
    }

    public void hoverMenu(String menu) {
        getLink(menu).hover();
    }

    public void clickMenu(String menu) {
        getLink(menu).click();
    }

    public Locator getLink(String text) {
        return page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(text));
    }

    public void setTestId(String id) {
        playwright.selectors().setTestIdAttribute(id);
    }

    public Locator getId(String testId) {
        setTestId("id");
        return page.getByTestId(testId);
    }

    public void sortBy(String sortingCriteria) {
        getId(SORT_BY_FEATURED).click();
        getId(sortingCriteria).click();
    }

    public Locator getCardsLocator() {
        return page.locator(CARDS_LOCATOR);
    }

    public List<Locator> getAllCards() {
        return getCardsLocator().all();
    }

    public Locator getLastCard() {
        final List<Locator> allCards = getAllCards();
        return allCards.get(allCards.size() - 1);
    }

    public List<String> getCardsText() {
        return getCardsLocator().allInnerTexts();
    }

    public String getLastCardText(List<String> cardsText) {
        return cardsText.get(cardsText.size() - 1);
    }

    public List<Double> getFullPrices() {
        List<Double> fullPrices = new ArrayList<>();
        List<String> allCardsTexts = getAllCardsTexts();
        for (String text : allCardsTexts) {
            String[] splitText = text.split("Full price");
            String price = splitText[1]
                    .replace("$", "")
                    .replace(" ", "")
                    .replace(":", "")
                    .replace("\n", "")
                    .replace("+tax", "")
                    .replace(",", "");

            fullPrices.add(Double.parseDouble(price));
        }

        System.out.println(fullPrices);
        return fullPrices;
    }

    public List<String> getAllCardsTexts() {
        List<String> allCardsTexts = getCardsText();
        String lastCardText = getLastCardText(allCardsTexts);

        while(!lastCardText.isEmpty()) {
            getLastCard().hover();

            List<String> cardsTexts = getCardsText();

            int oldAmountOfCards = allCardsTexts.size();
            int cardsAdded = 0;

            for (int i = 0; i < cardsTexts.size(); i++) {
                if (cardsTexts.get(i).equals(lastCardText)) {
                    for (int j = i + 1; j < cardsTexts.size(); j++) {
                        if (!cardsTexts.get(j).equals("")) {
                            allCardsTexts.add(cardsTexts.get(j));
                            lastCardText = cardsTexts.get(j);
                            cardsAdded++;
                        }
                    }
                    break;
                }
            }
            //make sure allCardsTexts List was updated correctly
            Assertions.assertEquals(allCardsTexts.size(), (oldAmountOfCards + cardsAdded), "");
            Assertions.assertEquals(allCardsTexts.get(allCardsTexts.size() - 1), lastCardText);
        }

        return allCardsTexts;
    }


}
