package com.test.qa.advertisement.blocks;

import org.openqa.selenium.support.FindBy;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.element.HtmlElement;
import ru.yandex.qatools.htmlelements.element.Link;

@FindBy(css = "td[valign=top]")
public class AdGroup extends HtmlElement {
    @FindBy(css = "div.main_head a")
    private Link groupName;

    public String getGroupName() {
        return groupName.getText();
    }

    @Step("Click on ad group name link.")
    public String open() {
        String[] nodes = groupName.getReference().split("/");
        groupName.click();
        return nodes[nodes.length - 1];
    }
}
