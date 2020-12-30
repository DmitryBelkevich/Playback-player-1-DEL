package com.hard.playback_player.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
}
