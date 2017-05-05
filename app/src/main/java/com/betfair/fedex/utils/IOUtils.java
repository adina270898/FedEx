package com.betfair.fedex.utils;

import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by TusaA on 5/3/2017.
 */

public class IOUtils {

    public static void deleteFileFromInternalStorage(Context context, String fileName) {
        File internalFolder = context.getFilesDir();
        File file = new File(internalFolder, fileName);
        file.delete();
    }

    public static String readFromInternalStorage(Context context, String fileName) {
        StringBuilder sb = new StringBuilder();
        try {
            FileInputStream inputStream = context.openFileInput(fileName);
            BufferedReader bf = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bf.readLine()) != null) {
                sb.append(line);
            }
            bf.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static void writeToInternalStorage(Context context, String fileName, String data) {
        try {
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void appendToInternalStorage(Context context, String fileName, String data) {
        try {
            FileOutputStream outputStream = context.openFileOutput(fileName, Context.MODE_APPEND);
            outputStream.write(data.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
