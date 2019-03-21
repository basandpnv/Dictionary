import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws UnsupportedTranslationException {
        try {
            consoleMenu();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void consoleMenu() throws UnsupportedTranslationException, IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("dict.xml");

        Translator client = context.getBean(Translator.class);
        File dictionaryFile;
        int numberDictionary;
        String directory;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.println("Введите путь к папке со словарями:");
            directory = reader.readLine();
            File[] files =  client.setTranslator(directory).listFiles();
            System.out.println();
            System.out.println("Выберите словарь  ");
            System.out.println("1. Буквенный словарь");
            System.out.println("2. Числовой словарь");
            System.out.println();

            numberDictionary = Integer.parseInt(reader.readLine());

            dictionaryFile = client.find(numberDictionary,files);
            if(dictionaryFile!=null)
            client.setDictionary(dictionaryFile);
else
                throw new IllegalArgumentException("Файл со словарем не найден");
            String menu = "Выберите действие\n" + "1 - Просмотр словаря\n" + "2 - Удаление по ключу\n" + "3 - Поиск по ключу\n"
                    + "4 - Добавление записи\n" + "5 - Выход\n";

            System.out.println(menu);
            System.out.println();
            boolean isQuit = false;
            while (!isQuit) {
                switch (Integer.parseInt(reader.readLine())) {
                    case 1:
                        client.printDictionary();
                        break;

                    case 2:
                        System.out.println("Введите слово, которое необходимо удалить");
                        client.delete(reader.readLine());
                        client.updateFile(dictionaryFile);
                        break;

                    case 3:
                        System.out.println("Введите слово, перевод которого нужно найти");
                        client.search(reader.readLine());
                        break;

                    case 4:
                        System.out.println("Введите слово");
                        String a = reader.readLine();
                        System.out.println("Введите перевод");
                        String b = reader.readLine();
                        client.add(client.opr(a,b),a,b);
                        client.updateFile(dictionaryFile);
                        break;

                    case 5:

                        isQuit = true;
                        break;

                    default:
                        System.out.println("Такого пункта в меню нет");
                        break;
                }

                System.out.println(menu);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}