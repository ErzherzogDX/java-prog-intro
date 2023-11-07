import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.Math.*;


public class A {


    static final int MOD = 998244353;
    public static long multiply(long a, long b) {
        return (a * b) % MOD;
    }

    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        int n = in.nextInt();
        int k = in.nextInt();

        long[] pw = new long[n + 1];
        long[] double_pol = new long[n + 1];
        long[] total = new long[n + 1];
        pw[0] = 1;

        long result = 0;
        for (int i = 1; i <= n; i++) {
            pw[i] = multiply(pw[i - 1], k);
            if(i % 2 == 1) {
                total[i] += i * pw[(i + 1) / 2];
            }
            else{
                total[i] += multiply(i / 2, pw[i / 2] + pw[i / 2 + 1]);
            }

            double_pol[i] += total[i];

            for (int j = 2; i * j <= n; j++) {
                total[i * j] = total[i * j] - multiply(double_pol[i], j - 1);
                double_pol[i * j] = double_pol[i * j] - double_pol[i];
            }

            result += total[i];
        }
        System.out.println(result % MOD);
    }
}