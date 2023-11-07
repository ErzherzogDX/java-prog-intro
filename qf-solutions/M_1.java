import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class A {

    public static void main(String[] args) throws IOException {


        Scanner in = new Scanner(System.in);
        int t = in.nextInt();

        while (t > 0) {
            int n = in.nextInt();
            int result = 0;

            int[] main_arr = new int[n];
            for(int i = 0; i < n; i++){
                main_arr[i] = in.nextInt();
            }

            Map<Integer, Integer> map = new HashMap<>();
            for (int j = n - 1; j > 0; j--) {
                for (int i = 0; i < j; i++) {
                    int temp = 2 * main_arr[j] - main_arr[i];
                    result += map.getOrDefault(temp, 0);
                }
                map.put(main_arr[j], map.getOrDefault(main_arr[j], 0) + 1);
            }

            System.out.println(result);
            t--;
        }
    }
}