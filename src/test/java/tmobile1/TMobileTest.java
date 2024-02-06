package tmobile1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static tmobile1.Constants.*;

public class TMobileTest extends BaseTest {

    @Test
    public void testSortByPriceLowToHigh(){
        open(TMOBILE_URL);
        hoverMenu(PHONES_AND_DEVICES);
        clickMenu(TABLETS);
        sortBy(PRICE_LOW_TO_HIGH);

        final List<Double> fullPricesList = getFullPrices();
        final Double lowestPrice = fullPricesList.get(0);
        final Double highestPrice = fullPricesList.get(fullPricesList.size() - 1);

        //Verify that the prices of displayed products are arranged in ascending order, from the lowest to the highest.

        Assertions.assertTrue(lowestPrice <= highestPrice);

        for (int i = 0; i < fullPricesList.size() - 1; i++) {
            final Double thisFullPrice = fullPricesList.get(i);
            final Double nextFullPrice = fullPricesList.get(i + 1);

            Assertions.assertTrue(thisFullPrice <= nextFullPrice);
        }
    }
}
