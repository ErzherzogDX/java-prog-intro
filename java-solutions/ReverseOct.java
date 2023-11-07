import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ReverseOct {
    public static void main(String[] args) throws IOException {

        int n = 5;
        String[] arrayFirst = new String[n];
        //  Scanner in = new Scanner(System.in);
        EScanner scanner = new EScanner(System.in, StandardCharsets.UTF_8);

        int currentChar = 0;

        while (scanner.hasNextLine()) {
            if (currentChar > arrayFirst.length - 1) {
                n = 2 * n;
                arrayFirst = Arrays.copyOf(arrayFirst, n);
                continue;
            }

            arrayFirst[currentChar] = scanner.nextLine();
            currentChar++;
        }

        for (int i = currentChar - 1; i >= 0; i--) {
            String[] line;

            if (arrayFirst[i] == "") {
                System.out.println(" ");
                continue;
            } else {
                line = arrayFirst[i].split("\\s+");
            }

            for (int j = line.length - 1; j >= 0; j--) {
                System.out.print(line[j] + " ");
            }
            System.out.println();
        }

        scanner.close();
    }
}