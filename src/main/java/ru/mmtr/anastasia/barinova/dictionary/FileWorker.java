package ru.mmtr.anastasia.barinova.dictionary;

import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FileWorker {

    private File dictionaryDirectory;
    private Map<String, String> dictionary;
    private int numberDictionary;
    public static final String ONE_DICT = "^[A-Za-z]{4}[-][A-Za-z0-9]+$";
    public static final String TWO_DICT = "^[0-9]{5}[-][A-Za-z0-9]+$";

    public FileWorker() {

    }

    public File setTranslator(String dictionaryDirectory) throws IllegalArgumentException {
        this.dictionaryDirectory = new File(dictionaryDirectory);
        if (!this.dictionaryDirectory.isDirectory() || !this.dictionaryDirectory.exists()) {
            throw new IllegalArgumentException("Папка со словарями не является директорией или не создана");
        }
        this.dictionary = new HashMap<>();
        return this.dictionaryDirectory;
    }


    public void setDictionary(File dictionaryFile) {
        if (dictionaryFile.length() == 0)
            throw new IllegalArgumentException("Файл словаря пуст");
        else {
            StringBuilder data = new StringBuilder("");
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(dictionaryFile), "windows-1251"))) {
                while (reader.ready()) {
                    data.append(reader.readLine());
                    data.append("\n\r");
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] strings = data.toString().split("\\n+\\r+");
            for (String string : strings) {
                this.dictionary.put(string.substring(0, string.indexOf('-')), string.substring(string.lastIndexOf('-') + 1));
            }
        }
    }

    public File find(int a, File[] files) throws IOException {
        numberDictionary = a;
        boolean b;
        switch (a) {
            case 1: {
                for (int i = 0; i < files.length; i++) {
                    b = true;
                    BufferedReader in = new BufferedReader(new FileReader(files[i]));
                    String str;
                    while ((str = in.readLine()) != null) {
                        if (!str.matches(ONE_DICT))
                            b = false;
                    }
                    if (b) return files[i];
                }
                break;
            }
            case 2: {
                for (int i = 0; i < files.length; i++) {
                    b = true;
                    BufferedReader in = new BufferedReader(new FileReader(files[i]));
                    String str;
                    while ((str = in.readLine()) != null) {
                        if (!str.matches(TWO_DICT))
                            b = false;
                    }
                    if (b) return files[i];
                }
                break;
            }
        }

        return null;
    }
}