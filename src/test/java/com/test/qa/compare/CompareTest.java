package com.test.qa.compare;

import com.test.qa.main.pages.VideoPage;
import com.test.qa.utils.DriverFactory;
import com.test.qa.utils.PropertyLoader;
import com.test.qa.utils.TestRunner;
import com.test.qa.utils.Utils;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Features;
import ru.yandex.qatools.allure.annotations.Stories;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Stories("Youtube videos")
@Features("AddBlock ON/OFF")
public class CompareTest extends TestRunner {
    @Test
    public void compare() {
        int videosCount = 10;
        List<File> addBlockOn = new ArrayList<>(videosCount);
        List<File> addBlockOff = new ArrayList<>(videosCount);
        String nameFormat = "clip_#%s-addBlock_%s.wav";
        String searchPhrase = "2017 official trailers";

        List<VideoPage> videoPages = mainPage.findVideos(searchPhrase)
            .sortByRating()
            .getVideos(videosCount);

        final int[] index = {1};
        videoPages.forEach(page -> {
            page.open();
            addBlockOn.add(Utils.makeRecord(String.format(nameFormat, index[0]++, "off")));
        });

        index[0] = 1;
        WebDriver newDriver = DriverFactory.getDriver(
            PropertyLoader.loadCapabilitiesFromFile("addblock.chrome.capabilities")
        );
        videoPages.forEach(page -> {
            page.setDriver(newDriver);
            page.open();
            addBlockOff.add(Utils.makeRecord(String.format(nameFormat, index[0]++, "on")));
        });
        Utils.compareAudioFiles(addBlockOn, addBlockOff);
        DriverFactory.dismissDriver(newDriver);
    }
}
