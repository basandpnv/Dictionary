package ru.mmtr.anastasia.barinova.dictionary;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

@Component
public class Translator {
    private Map<String, String> dictionary;
    private int numberDictionary;
    public Translator(Map<String, String> dictionary, int numberDictionary) throws UnsupportedTranslationException {
        this.dictionary = dictionary;
        this.numberDictionary = numberDictionary;
    }


    public int opr(String key, String value) {
        if (key.matches("^[A-Za-z]{4}$") & value.matches("^[A-Za-z0-9]+$"))
            return 1;
        else if (key.matches("^[0-9]{5}$") & value.matches("^[a-zA-Z0-9]+$"))
            return 2;
        else return 0;
    }

    public void updateFile(File dictionaryFile) {
        Writer writer = null;
        try {
            writer = new FileWriter(dictionaryFile);
            for (Map.Entry<String, String> line : this.dictionary.entrySet()) {
                String a = line.getKey();
                String b = line.getValue();
                writer.write(a + "-" + b);
                writer.write(System.getProperty("line.separator"));
            }
            writer.flush();
        } catch (Exception e) {

        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException ex) {
                }
            }
        }
    }

    public void printDictionary() {
        for (Map.Entry<String, String> pair : this.dictionary.entrySet()) {
            String key = pair.getKey();
            String value = pair.getValue();
            System.out.println(key + " - " + value);
        }
    }

    public void delete(String key) {
        if (this.dictionary.containsKey(key.toLowerCase())) {
            dictionary.remove(key);
        } else {
            System.out.println("Такого слова нет в словаре");
        }
    }

    public void add(int opr, String key, String value) {
        if (opr == numberDictionary) {
            dictionary.put(key, value);
        } else
            System.out.println("Добавление такой пары невозможно");
    }

    public void search(String key) {
        if (this.dictionary.containsKey(key.toLowerCase())) {
            System.out.println(key + "-" + dictionary.get(key));
        } else {
            System.out.println("Такого слова нет в словаре");
        }
    }

}