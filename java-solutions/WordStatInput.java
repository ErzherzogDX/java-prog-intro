import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;


public class WordStatInput {

    public static void main(String[] args) throws IOException {
        Map<String, Integer> wordStatistics = new LinkedHashMap<String, Integer>();
        try {
            EScanner scanner = new EScanner(new FileInputStream(args[0]), StandardCharsets.UTF_8);
            //BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(args[0]), "UTF8"));
            try {
                boolean work = true;
                while (scanner.hasNextLine()) {
                    String line = "";
                    line = scanner.nextLine();

                    int start = 0;
                    int finish = 0;
                    String s;

                    for (int j = 0; j <= line.length(); j++) {
                        finish++;
                        if (j == line.length() || ((!(Character.isLetter(line.charAt(j)))) && !(Character.getType(line.charAt(j)) == Character.DASH_PUNCTUATION) && !((line.charAt(j)) == '\''))) {
                            s = line.substring(start, finish - 1).toLowerCase();
                            start = finish;
                            if (!s.isEmpty()) {
                                wordStatistics.put(s, wordStatistics.getOrDefault(s, 0) + 1);
                            }
                            s = "";
                        }
                    }
                }
            } finally {
                scanner.close();
            }
            BufferedWriter writter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), "UTF8"));

            try {
                for (String word : wordStatistics.keySet()) {
                    writter.write(word + " " + wordStatistics.get(word));
                    writter.newLine();
                }
            } catch (IOException e) {
                System.out.println("Invalid output! Вы проиграли!!! " + e.getMessage());
            } finally {
                writter.close();
            }
        } catch (IOException e) {
            System.out.println("Invalid input! Вы проиграли!!! " + e.getMessage());
        }
    }
}
