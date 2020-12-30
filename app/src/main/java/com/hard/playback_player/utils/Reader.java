package com.hard.playback_player.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Reader {
    public static String readFromFile(String path) throws FileNotFoundException {
        if (path == null)
            return null;

        StringBuilder stringBuilder = new StringBuilder();

        FileReader fileReader = new FileReader(new File(path));

        BufferedReader bufferedReader = new BufferedReader(fileReader);

        String line = null;
        try {
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append('\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null)
                    bufferedReader.close();

                if (fileReader != null)
                    fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }

    public static String readFromUrl(String url) {
        BufferedInputStream inputStream = null;
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new URL(url).openStream()));

            final char dataBuffer[] = new char[1024];
            int read;
            while ((read = bufferedReader.read(dataBuffer, 0, dataBuffer.length)) > 0) {
                stringBuilder.append(dataBuffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return stringBuilder.toString();
    }
}
