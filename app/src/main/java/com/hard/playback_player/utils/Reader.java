package com.hard.playback_player.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class Reader {
    public static String readFromFile(String path) throws FileNotFoundException {
        if (path == null)
            return null;

        FileReader fileReader = new FileReader(new File(path));
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        StringBuilder stringBuilder = new StringBuilder();

        String line;
        try {
            String prefix = "";
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(prefix);
                prefix = "\n";
                stringBuilder.append(line);
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

    public static void download(String url, File path) {
        BufferedInputStream inputStream = null;
        FileOutputStream outputStream = null;

        try {
            inputStream = new BufferedInputStream(new URL(url).openStream());
            outputStream = new FileOutputStream(path);

            final byte dataBuffer[] = new byte[1024];
            int read;
            while ((read = inputStream.read(dataBuffer, 0, dataBuffer.length)) > 0) {
                outputStream.write(dataBuffer, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();

                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
