import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
public class Translator {
    private File dictionaryDirectory;
    private Map<String, String> dictionary;
    private int numberDictionary;

    public Translator() throws UnsupportedTranslationException {

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
        if (dictionaryFile.length()==0)
            throw new IllegalArgumentException("Файл словаря пуст");
            else{
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
        public File find(int a,File [] files) throws IOException {
            File file = null;
            numberDictionary=a;
            boolean b;
            switch (a) {
                case 1: {
                    for (int i = 0; i < files.length; i++) {
                        b = true;
                        BufferedReader in = new BufferedReader(new FileReader(files[i]));
                        String str;
                        while ((str = in.readLine()) != null) {
                            if (!str.matches("^[A-Za-z]{4}[-][A-Za-z0-9]+$"))
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
                            if (!str.matches("^[0-9]{5}[-][A-Za-z0-9]+$"))
                                b = false;
                        }
                        if (b) return files[i];
                    }
                    break;
                }
            }

            return file;
        }

    public int opr(String key,String value){
        if(key.matches("^[A-Za-z]{4}$")&value.matches("^[A-Za-z0-9]+$"))
            return 1;
        else
            if(key.matches("^[0-9]{5}$")&value.matches("^[a-zA-Z0-9]+$"))
                return 2;
        else return 0;
    }

    public void updateFile(File dictionaryFile){
        Writer writer = null;
        try {
            writer = new FileWriter(dictionaryFile);
            for(Map.Entry<String, String> line : this.dictionary.entrySet()) {
                String a=line.getKey();
                String b= line.getValue();
                writer.write(a+"-"+b);
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

    public void printDictionary()
    {
        for(Map.Entry<String, String> pair : this.dictionary.entrySet())
        {
            String key = pair.getKey();
            String value = pair.getValue();
            System.out.println(key+" - "+value);
        }
    }

    public void delete(String key) {
        if (this.dictionary.containsKey(key.toLowerCase())){
            dictionary.remove(key);
        }
        else{
            System.out.println("Такого слова нет в словаре");
        }
    }

    public void add(int opr, String key, String value){
        if(opr==numberDictionary){
            dictionary.put(key,value);
        }
        else
            System.out.println("Добавление такой пары невозможно");
    }

    public void search(String key) {
        if (this.dictionary.containsKey(key.toLowerCase())){
            System.out.println(key+"-"+dictionary.get(key));
        }
        else{
            System.out.println("Такого слова нет в словаре");
        }
    }

}