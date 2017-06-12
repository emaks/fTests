package com.test.qa.main.pages;

import com.test.qa.utils.Page;
import org.openqa.selenium.WebDriver;

public class VideoPage extends Page<VideoPage> {
    public VideoPage(WebDriver driver, String title, String mca) {
        super(driver);
        setMca("watch?" + mca);
        setTitle(title + " - YouTube");
    }
}
