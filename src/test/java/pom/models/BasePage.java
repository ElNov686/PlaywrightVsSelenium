package pom.models;

import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public abstract class BasePage {
    private final Playwright playwright;
    private Page page;

    public BasePage(final Page page, final Playwright playwright) {
        this.page = page;
        this.playwright = playwright;
    }

    protected Page getPage() {
        return page;
    }

    protected Playwright getPlaywright() {
        return playwright;
    }

    public abstract String getUrl();
    public abstract String getTitle();
}
