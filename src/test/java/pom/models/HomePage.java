package pom.models;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public final class HomePage extends Asserts {

    public HomePage(final Page page, final Playwright playwright) {
        super(page, playwright);
    }

    @Override
    public String getUrl() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    public Page page() {
        return getPage();
    }

    public String getHomePageUrl() {
        return getPage().url();
    }

    public String getHomePageTitle() {
        return getPage().title();
    }
}
