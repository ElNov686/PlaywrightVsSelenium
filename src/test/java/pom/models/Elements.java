package pom.models;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;

public abstract class Elements extends BasePage {

    protected Elements(Page page, Playwright playwright) {
        super(page, playwright);
    }

    public Locator button(String text) {
        return getPage().getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(text));
    }

    public Locator link(String text) {
        return getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(text));
    }

    public Locator exactLink(String text) {
        return getPage().getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName(text).setExact(true));
    }

    public Locator heading(String text) {
        return getPage().getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName(text));
    }

    public Locator dataId(String text) {
        getPlaywright().selectors().setTestIdAttribute("data-id");
        return getPage().getByTestId(text);
    }

    public Locator text(String text) {
        return getPage().getByText(text);
    }

    public Locator alert() {
        return getPage().getByRole(AriaRole.ALERT);
    }

    public Locator image(String text) {
        return getPage().getByRole(AriaRole.IMG, new Page.GetByRoleOptions().setName(text));
    }

    public void clickButton(String text) {
        button(text).click();
    }
}
