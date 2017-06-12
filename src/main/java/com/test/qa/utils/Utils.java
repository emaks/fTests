package com.test.qa.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musicg.fingerprint.FingerprintSimilarity;
import com.musicg.wave.Wave;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import ru.yandex.qatools.allure.annotations.Attachment;
import ru.yandex.qatools.allure.annotations.Step;
import ru.yandex.qatools.htmlelements.element.HtmlElement;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Utils {
    public static LinkedHashMap<String, Object> parseJson(String context) {
        try {
            //noinspection unchecked
            return new ObjectMapper().readValue(context, LinkedHashMap.class);
        } catch (IOException e) {
            return null;
        }
    }

    public static ExpectedCondition<Boolean> invisibilityOf(final HtmlElement element) {
        return new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return !element.exists();
            }

            @Override
            public String toString() {
                return "invisibility of " + element;
            }
        };
    }

    @Step("Make record {0}")
    public static File makeRecord(String audioName) {
        JavaSoundRecorder recorder = new JavaSoundRecorder();
        Thread stopper = new Thread(() -> {
            try {
                Thread.sleep(JavaSoundRecorder.RECORD_TIME);
            } catch (InterruptedException ex) {
                throw new Error(ex);
            }
            recorder.finish();
        });
        stopper.start();
        recorder.start(audioName);
        return recorder.getAudioFile();
    }

    @Step("Compare audio files")
    @Attachment(value = "CompareResults", type = "text/plain")
    public static List<String> compareAudioFiles(List<File> addBlockOn, List<File> addBlockOff) {
        List<String> compareResults = new ArrayList<>();
        int index = 0;
        for (File file : addBlockOn) {
            Wave record1 = new Wave(file.getPath());
            Wave record2 = new Wave(addBlockOff.get(index).getPath());
            FingerprintSimilarity Similarity = record1.getFingerprintSimilarity(record2);
            compareResults.add(String.format(
                "Similarity of '%s', '%s' files: %s%%",
                file.getName(),
                addBlockOff.get(index++).getName(),
                100 * Similarity.getSimilarity()
            ));
        }
        return compareResults;
    }
}
