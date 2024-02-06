import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

//TC-01 “Menu Fish → Submenus “Saltwater”, “Freshwater”
//
//        Go to https://petstore.octoperf.com/
//        Enter the Store
//        Verify that the left side menu “Fish” has the text “Saltwater”, “Freshwater”

public class TC01 {

    public static void main(String[] args) {
        Playwright playwright = Playwright.create();
        Browser browser = playwright
                .chromium()
                .launch(
                        new BrowserType
                                .LaunchOptions()
                                .setHeadless(false)
                );

        Page page = browser.newPage();

        page.navigate("https://petstore.octoperf.com/");
        Locator enterTheStoreLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Enter the Store"));
        enterTheStoreLink.click();

        //div[@id='SidebarContent']/a[2]/preceding-sibling::text()[normalize-space()]

        Locator l = page.locator("#SidebarContent");
        assertThat(l).containsText("Saltwater, Freshwater");

//        String txt = page.locator("#SidebarContent:below(a[href*='FISH'], 15)").textContent();
//        System.out.println(txt);

        playwright.close();

        WebDriver driver = new ChromeDriver();


        //Selenium
        driver.get("https://petstore.octoperf.com/");
        driver.findElement(By.linkText("Enter the Store")).click();
        assert (
                driver.findElement(
                        By.xpath("//div[@id='SidebarContent']/a[2]/preceding-sibling::text()[normalize-space()]")
                ).equals("Saltwater, Freshwater"));

        driver.close();

    }
}
