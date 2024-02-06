import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Selenium {

    //TC01 “Main page title is "Сurrent weather and forecast - OpenWeatherMap"
    //Go to "https://openweathermap.org/"
    //Verify that the main page title is "Сurrent weather and forecast - OpenWeatherMap"
    //
    //
    //TC02 “Current temperature is shown in Celsium by default”
    //Go to "https://openweathermap.org/"
    //Verify that the current temp is shown on the main page
    //Verify that the current temp’s unit is a Celcium by  default


    public static void main(String[] args) throws InterruptedException {

        WebDriver driver = new ChromeDriver();
        //TC01
//        driver.get("https://openweathermap.org/");
//        String title = driver.getTitle();
//
//        System.out.println(title);
//
//        assert(title.equals("Сurrent weather and forecast - OpenWeatherMap1"));

        //TC02

        driver.get("https://openweathermap.org/");
        Thread.sleep(8000);
        WebElement currTemp = driver.findElement(By.cssSelector(".current-temp .heading"));


        System.out.println(currTemp.getText());

        assert(currTemp).isDisplayed();
        assert(currTemp.getText()).contains("C");

        driver.close();
    }
}
