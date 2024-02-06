import com.microsoft.playwright.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class Playwright1 {

    //TC01 “Main page title is "Сurrent weather and forecast - OpenWeatherMap"
    //Go to "https://openweathermap.org/"
    //Verify that the main page title is "Current weather and forecast - OpenWeatherMap"
    //
    //
    //TC02 “Current temperature is shown in С by default”
    //Go to "https://openweathermap.org/"
    //Verify that the current temp is shown on the main page
    //Verify that the current temp’s unit is a Celcium by  default

    public static void main(String[] args) {
        try(Playwright playwright = Playwright.create();) {

            Browser browser = playwright
                    .chromium()
                    .launch(
                            new BrowserType.LaunchOptions()
                                    .setHeadless(false)
                    );

            Page page = browser.newPage();

            //TC01
//            page.navigate("https://openweathermap.org/");
//            String title = page.title();
//
//            System.out.println(title);
//
//            assertThat(page).hasTitle("Сurrent weather and forecast - OpenWeatherMap1");

            //TC02
            page.navigate("https://openweathermap.org/");

            Locator currTemp = page.locator("css=.current-temp .heading");
            String currentTemp = currTemp.innerText();

            System.out.println(currentTemp);

            assertThat(currTemp).isVisible();
            assertThat(currTemp).containsText("C");

        }

    }
}
