package com.test.qa.utils;

import org.hamcrest.Matchers;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Vector;

import static org.hamcrest.MatcherAssert.assertThat;

public class JavaSoundRecorder {
    public static final long RECORD_TIME = 60000;
    private AudioFileFormat.Type fileType = AudioFileFormat.Type.WAVE;
    private TargetDataLine line;
    private File audioFile;

    public void start(String fileName) {
        audioFile = new File(String.format("%s/%s", System.getProperty("buildDirectory"), fileName));
        Vector<AudioFormat> audioFormats = getSupportedFormats(TargetDataLine.class);
        assertThat("Lines are not supported", audioFormats, Matchers.not(Matchers.empty()));
        try {
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, audioFormats.get(0));
            line = (TargetDataLine) AudioSystem.getLine(info);
            line.open(audioFormats.get(0));
            line.start();
            System.out.println("Start capturing...");
            AudioInputStream ais = new AudioInputStream(line);
            System.out.println("Start recording...");
            AudioSystem.write(ais, fileType, audioFile);
        } catch (LineUnavailableException | IOException ex) {
            throw new Error(ex.getCause());
        }
    }

    public void finish() {
        line.stop();
        line.close();
        System.out.println("Finish recording...");
    }

    public File getAudioFile() {
        return audioFile;
    }

    public Vector<AudioFormat> getSupportedFormats(Class<?> dataLineClass) {
        float sampleRates[] = {8000.0f, 11025.0f, 16000.0f, 22050.0f, 44100.0f};
        int channels[] = {1, 2};
        int bytesPerSample[] = {1, 2};

        AudioFormat format;
        DataLine.Info lineInfo;

        Vector<AudioFormat> formats = new Vector<>();

        for (Mixer.Info mixerInfo : AudioSystem.getMixerInfo()) {
            for (float sampleRate : sampleRates) {
                for (int channel : channels) {
                    for (int aBytesPerSample : bytesPerSample) {
                        format = new AudioFormat(
                            AudioFormat.Encoding.PCM_SIGNED,
                            sampleRate,
                            8 * aBytesPerSample,
                            channel,
                            aBytesPerSample,
                            sampleRate,
                            false
                        );
                        lineInfo = new DataLine.Info(dataLineClass, format);
                        if (AudioSystem.isLineSupported(lineInfo)) {
                            if (AudioSystem.getMixer(mixerInfo).isLineSupported(lineInfo)) {
                                formats.add(format);
                            }
                        }
                    }
                }
            }
        }
        return formats;
    }
}
