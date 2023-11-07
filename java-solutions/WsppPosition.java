import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;


public class WsppPosition {

    public static void main(String[] args) throws IOException {
        Map<String, IntList> wordStatistics = new LinkedHashMap<String, IntList>();
        try {
            EScanner scanner = new EScanner(new FileInputStream(args[0]), StandardCharsets.UTF_8);
            try {
                int totalCounterNums = 1;
                int totalCounterString = 1;
                while (scanner.hasNextLine()) {
                    String line = "";
                    line = scanner.nextLine().toLowerCase();

                    int start = 0;
                    int finish = 0;
                    String s;

                    for (int j = 0; j <= line.length(); j++) {
                        finish++;
                        if (j == line.length() || ((!(Character.isLetter(line.charAt(j)))) && !(Character.getType(line.charAt(j)) == Character.DASH_PUNCTUATION) && !((line.charAt(j)) == '\''))) {
                            s = line.substring(start, finish - 1);
                            start = finish;
                            if (!s.isEmpty()) {
                                IntList listTemp = wordStatistics.getOrDefault(s, new IntList());
                                listTemp.set(totalCounterNums, totalCounterString);
                                wordStatistics.put(s, listTemp);

                                totalCounterNums++;
                            }
                            s = "";
                        }
                    }

                    totalCounterNums = 1;
                    totalCounterString++;
                }
            } finally {
                scanner.close();
            }
            BufferedWriter writter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));

            try {
                for (String word : wordStatistics.keySet()) {
                    writter.write(word + " " + wordStatistics.get(word).getNumOfOccurrences());
                    int[] positionList = wordStatistics.get(word).getList();
                    int i = 0;

                    while ((positionList[i] != 0 || positionList[i + 1] != 0) && i < positionList.length) {
                        writter.write(" " + positionList[i] + ":" + positionList[i + 1]);
                        i += 2;
                    }
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
