package pom.models;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.LocatorAssertions;

import java.util.List;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public abstract class Asserts extends Elements {

    public Asserts(Page page, Playwright playwright) {
        super(page, playwright);
    }

    //URL, Title
    public void assertUrl(String url) {
        assertThat(getPage()).hasURL(url);
    }

    public void assertTitle(String title) {
        assertThat(getPage()).hasTitle(title);
    }

    //Button
    public void assertThatButtonIsVisible(String text) {
        assertThat(button(text)).isVisible();
    }

    public void assertThatButtonIsEnabled(String text) {
        assertThat(button(text)).isEnabled();
    }

    public void assertThatButton_IsVisible_IsEnabled(String text) {
        assertThat(button(text)).isVisible();
        assertThat(button(text)).isEnabled();
    }

    //Link
    public void assertThatLinkIsVisible(String text) {
        assertThat(link(text)).isVisible();
    }

    public void assertThatLinkIsEnabled(String text) {
        assertThat(link(text)).isEnabled();
    }

    //Heading
    public void assertThatHeadingIsVisible(String text) {
        assertThat(heading(text)).isVisible();
    }

    //id, data-id,
    public void assertThatDataIdIsVisible(String dataId) {
        assertThat(dataId(dataId)).isVisible();
    }

    public void assertDataIdHasText(String dataId, String textToAssert) {
        assertHasText(dataId(dataId), textToAssert);
    }

    //HasText
    public void assertHasText(Locator locator, String textToAssert) {
        assertThat(locator).hasText(textToAssert);
    }

    public void assertHasText(Locator locator, List<String> textToAssert) {
        assertThat(locator).hasText(textToAssert.toString(), new LocatorAssertions.HasTextOptions().setUseInnerText(true));
    }


}
