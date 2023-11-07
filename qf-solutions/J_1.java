import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class A {

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();

        int[][] a = new int[n][n];
        int[][] result = a;
        in.nextLine();

        for(int i = 0; i < n; i++){
            String s = in.nextLine();
            char[] line = s.toCharArray();

            for(int j = 0; j < n; j++){
                a[i][j] = line[j] - '0';
            }
        }

        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < n; j++) {
                result[i][j] = a[i][j];
                if (a[i][j] == 0){
                    continue;
                }
                for (int k = i - 1; k >= 0; k--) {
                    a[k][j] -= a[k][i];
                    a[k][j] %= 10;
                    if (a[k][j] < 0){
                        a[k][j] += 10;
                    }
                }
            }
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(result[i][j]);
            }
            System.out.println();
        }
    }
}