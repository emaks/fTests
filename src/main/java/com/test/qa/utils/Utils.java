package com.test.qa.utils;

import com.sun.jndi.toolkit.url.Uri;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Utils {
    public static void pause(int seconds) {
        try {
            Thread.sleep(TimeUnit.SECONDS.toMillis(seconds));
        } catch (InterruptedException ignored) {
        }
    }

    public static Uri getUri(String url) {
        try {
            return new Uri(url);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<Integer> getRandomListOrder(int count) {
        List<Integer> values = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            values.add(i);
        }
        Collections.shuffle(values);
        return values;
    }
}
