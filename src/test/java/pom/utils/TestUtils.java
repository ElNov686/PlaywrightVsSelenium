package pom.utils;

import com.microsoft.playwright.Playwright;

public class TestUtils {

    public static void setTestId(String attribute, Playwright playwright) {
        playwright.selectors().setTestIdAttribute(attribute);
    }

}
