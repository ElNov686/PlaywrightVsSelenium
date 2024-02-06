import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class TC09TMobileTest {

    //‼️ Кто вчера не был, но хочет написать тест на PW. Сценарий такой:
    //1. Зайти на сайт t-mobile.com
    //2. В верхнем меню Products & что-то там еще (не помню) выбрать Tablets
    //3. На странице Tablets отсортировать цену  Low to High
    //4. Verify that the prices of displayed products are arranged in ascending order, from the lowest to the highest.
    // ❗️На самом деле надо проверить цену всех продуктов на странице. Уточняю, тк displayed, оказалось, можно понять по-разному )


    String line = "_____________________________________________________________________";

    Playwright playwright = Playwright.create();
    Browser browser = playwright
            .chromium()
            .launch(new BrowserType.LaunchOptions()
                    .setHeadless(false));
    BrowserContext context = browser.newContext();
    Page page = context.newPage();

    @Test
    public void testSortByPriceLowToHigh() {
        page.navigate("https://www.t-mobile.com/");

        //Navigate to page Tablets
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Phones & devices"))
                .hover();
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Tablets")).click();

       //set up testId as "id"
        playwright.selectors().setTestIdAttribute("id");

        //Sort by price "Low to High"
        page.getByTestId("mat-select-value-1").click();
        page.getByTestId("mat-option-5").click();

        //set up testId as "data-testid"
        playwright.selectors().setTestIdAttribute("data-testid");

        //Get all cards
        Locator allCardsLocator = page.locator("[_ngcontent-serverapp-c179] .small-card");
        List<Locator> allCards = allCardsLocator.all();
        System.out.println("How many cards are visible by PlayWright after landing on the page Tablets = " + allCards.size());
        System.out.println(line);

        //Get all cards texts (with products names and prices)
        List<String> allCardsTexts = allCardsLocator.allInnerTexts();
        System.out.println("allCardsTexts.size() = " + allCardsTexts.size());
        System.out.println(line);
//        System.out.println("Cards texts: \n\n" + allCardsTexts);
//        System.out.println(line);

        //But PlayWright can see less amount of cards than expected. I have 6 cards, so we need to scroll

        //get last card's text
        String lastCardAllText = allCardsTexts.get(allCardsTexts.size() - 1);
        System.out.println("lastCardText = \n\n" + lastCardAllText);
        System.out.println(line);

        //get last card locator
        Locator lastCard = allCards.get(allCards.size() - 1);

        //hover (scroll) to the last visible card
        lastCard.hover();

        //get new List of visible cards (after first scroll)
        List<Locator> allCardsAfterScroll = page.locator("[_ngcontent-serverapp-c179] .small-card").all();
        System.out.println("How many cards are visible by PlayWright after first scroll = " + allCardsAfterScroll.size());
        System.out.println(line);

       //Get all cards texts after first scroll (with products names and prices)
        List<String> allCardsTextsAfterScroll = allCardsLocator.allInnerTexts();
        System.out.println("allCardsTextsAfterFirstScroll.size() = " + allCardsTextsAfterScroll.size());
        System.out.println(line);
//        System.out.println("Cards texts: \n\n" + allCardsTexts);
//        System.out.println(line);

        //get last card's text (after first scroll)
        String lastCardAllTextAfterScroll = allCardsTextsAfterScroll.get(allCardsTextsAfterScroll.size() - 1);
        System.out.println("lastCard (after first scroll) text = \n\n" + lastCardAllTextAfterScroll);
        System.out.println(line);

        int j = 0;
        //get first previously invisible card, and add all previously invisible cards texts to the List allCardsTexts
        for(int i = 0; i < allCardsTextsAfterScroll.size(); i ++) {
            if (allCardsTextsAfterScroll.get(i).equals(lastCardAllText)) {
                for (j = i + 1; j < allCardsTextsAfterScroll.size(); j ++) {
                    allCardsTexts.add(allCardsTextsAfterScroll.get(j));
                }
                break;
            }
        }

        //make sure allCardsTexts List was updated correctly
        Assertions.assertEquals(allCardsTexts.size(), j);
        Assertions.assertEquals(allCardsTexts.get(allCardsTexts.size() - 1), lastCardAllTextAfterScroll);


        System.out.println(line);
        System.out.println("Updated list size = " + allCardsTexts.size());
        System.out.println("Expected new size = " + j);
        System.out.println("Last card text = \n\n" + allCardsTexts.get(allCardsTexts.size() - 1));
        System.out.println(line);

        //get last card locator
        lastCard = allCardsAfterScroll.get(allCardsAfterScroll.size() - 1);

        //hover (scroll) to the last visible card
        lastCard.hover();

        //get new List of visible cards (after second scroll)
        List<Locator> allCardsAfterSecondScroll = page.locator("[_ngcontent-serverapp-c179] .small-card").all();
        System.out.println("How many cards are visible by PlayWright after second scroll = " + allCardsAfterSecondScroll.size());
        System.out.println(line);

        //Get all cards texts after second scroll (with products names and prices)
        List<String> allCardsTextsAfterSecondScroll = page.locator("[_ngcontent-serverapp-c179] .small-card").allInnerTexts();
        System.out.println("allCardsTextsAfterSecondScroll.size() = " + allCardsTextsAfterSecondScroll.size());
        System.out.println(line);
//        System.out.println("Cards texts: \n\n" + allCardsTexts);
//        System.out.println(line);

        //get last card's text (after second scroll)
        String lastCardAllTextAfterSecondScroll = allCardsTextsAfterSecondScroll.get(allCardsTextsAfterSecondScroll.size() - 1);
        System.out.println("lastCard (after second scroll) text = \n\n" + lastCardAllTextAfterSecondScroll);
        System.out.println(line);

        int oldSize = allCardsTexts.size();
        j = 0;
        int newJ = 0;

        //get first previously invisible card, and add all previously invisible cards texts to the List allCardsTexts
        for(int i = 0; i < allCardsTextsAfterSecondScroll.size(); i ++) {
            if (allCardsTextsAfterSecondScroll.get(i).equals(lastCardAllTextAfterScroll)) {
                for (j = i + 1; j < allCardsTextsAfterSecondScroll.size(); j ++) {
                    if (!allCardsTextsAfterSecondScroll.get(j).equals("")) {
                        allCardsTexts.add(allCardsTextsAfterSecondScroll.get(j));
                        lastCardAllTextAfterSecondScroll = allCardsTextsAfterSecondScroll.get(j);
                        newJ ++;
                    }
                }
                break;
            }
        }

        //make sure allCardsTexts List was updated correctly
        Assertions.assertEquals(allCardsTexts.size(), oldSize + newJ);
        Assertions.assertEquals(allCardsTexts.get(allCardsTexts.size() - 1),  lastCardAllTextAfterSecondScroll);

        //create list of full prices from allCardsTexts
        List<Double> fullPrices = new ArrayList<>();
        for (String text : allCardsTexts) {
            String[] splitText = text.split("Full price");
            String price = splitText[1].replace("$", "");
            price = price.replace(" ", "");
            price = price.replace(":", "");
            price = price.replace("\n", "");
            price = price.replace("+tax", "");
            price = price.replace(",", "");
            fullPrices.add(Double.parseDouble(price));
        }

        System.out.println(fullPrices);


        //Verify that the prices of displayed products are arranged in ascending order, from the lowest to the highest.
        Assertions.assertTrue(fullPrices.get(0) <= fullPrices.get(fullPrices.size() - 1));

        for (int i = 0; i < fullPrices.size() - 1; i ++) {
            Assertions.assertTrue(fullPrices.get(i) <= fullPrices.get(i + 1));
        }
    }
}
