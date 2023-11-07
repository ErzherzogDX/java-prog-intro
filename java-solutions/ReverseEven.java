import java.util.Arrays;
import java.util.Scanner;

public class ReverseEven {
    public static void main(String[] args) {

        int n = 5;
        String[] arrayFirst = new String[n];
        Scanner in = new Scanner(System.in);

        int currentChar = 0;

        while (in.hasNextLine()) {
            if(currentChar > arrayFirst.length - 1){
                n = 2*n;
                arrayFirst = Arrays.copyOf(arrayFirst, n);
                continue;
            }

            arrayFirst[currentChar] = in.nextLine();
            currentChar++;
        }

        for(int i = currentChar-1; i >= 0; i--){
            String[] line;

            if(arrayFirst[i] == ""){
                System.out.println(" ");
                continue;
            }
            else {
                line = arrayFirst[i].split("\\s+");
            }

            for(int j = line.length - 1; j >= 0; j--){
                int lineInt = Integer.parseInt(line[j]);
                if (lineInt % 2 == 0) {
                    System.out.print(line[j] + " ");
                }
            }
            System.out.println();
        }
    }
}