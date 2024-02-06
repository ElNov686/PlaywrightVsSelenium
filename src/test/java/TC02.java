import com.microsoft.playwright.*;
import com.microsoft.playwright.options.AriaRole;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
//TC-02 “Sign-out link with the text “Sign Out”
//
//Go to https://petstore.octoperf.com/
//Enter the Store
//Click the “Sign In” link on the top menu
//Enter username “Tester1”
//Enter Password “vvv”
//Verify that after successful login, the top menu has the sign-out link with the text “Sign Out”

public class TC02 {

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
        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Enter the Store")).click();

        page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign In")).click();

//        page.getByRole(AriaRole.TEXTBOX).fill("Tester1");
        page.locator("input[name = 'username']").fill("Tester1");
        page.locator("input[name = 'password']").fill("vvv");
        page.locator("input[name = 'signon']").click();

        assertThat(page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign Out"))).isVisible();

        playwright.close();

        WebDriver driver = new ChromeDriver();
        driver.get("https://petstore.octoperf.com/");
        driver.findElement(By.linkText("Enter the Store")).click();
        driver.findElement(By.linkText("Sign In")).click();
        driver.findElement(By.name("username")).sendKeys("Tester1");
        driver.findElement(By.name("password")).sendKeys("vvv");
        driver.findElement(By.name("signon")).click();

        assert(driver.findElement(By.cssSelector("a[href$='signonForm=']")).getText()
                .equals("Sign In"));

        driver.close();



    }
}
