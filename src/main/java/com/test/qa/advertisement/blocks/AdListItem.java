package com.test.qa.advertisement.blocks;

import org.openqa.selenium.internal.Locatable;
import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.element.CheckBox;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.TextBlock;

@FindBy(css = "[id^='tr_']")
public class AdListItem extends HtmlElement {
    @FindBy(css = "[id^='dm_']")
    private TextBlock adName;
    @FindBy(css = "[name='mid[]']")
    private CheckBox selectCheckBox;

    public String getAdId() {
        return adName.getAttribute("id");
    }

    @Step("Select ad")
    public void selectAd() {
        ((Locatable) selectCheckBox.getWrappedElement()).getCoordinates().inViewPort();
        selectCheckBox.select();
    }
}
