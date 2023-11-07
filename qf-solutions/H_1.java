import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.Math.*;


public class A {
    public static void main(String[] args) throws IOException {
        Scanner in = new Scanner(System.in);

        final int K = 2000;
        final int N = 200002;

        int a[] = new int[N];
        int queries[] = new int[N];
        int mx;

        int n = in.nextInt();
        mx = 0;
        for (int i = 1; i <= n; i++) {
            a[i] = in.nextInt();
            mx = max(mx, a[i]);
        }

        int q = in.nextInt();
        for (int i = 0; i < q; i++) {
            queries[i] = in.nextInt();
        }

        int result[] = new int[N];
        int pref[] = new int[N];
        int mem[] = new int[K];

        for (int k = 1; k < K; k++) {
            if (k < mx) {
                continue;
            }
            int i = 1;
            while (i <= n) {
                int sm = a[i];
                while (i < n && (sm + a[i + 1]) <= k) {
                    i++;
                    sm += a[i];
                }
                mem[k]++;
                i++;
            }
        }
        for (int i = 1; i <= n; i++) {
            pref[i] = pref[i - 1] + a[i];
        }
        for (int i = 0; i < q; i++) {
            int k = queries[i];
            if (k < mx) {
                result[i] = -1;
                continue;
            }
            if (k < K) {
                result[i] = mem[k];
                continue;
            }
            int pos = 1;
            while (pos <= n) {
                int left_pos = pos;
                int right_pos = n + 1;
                int middle_pos;
                while (right_pos - left_pos > 1) {
                    middle_pos = (left_pos + right_pos) / 2;
                    if (pref[middle_pos] - pref[pos - 1] <= k) {
                        left_pos = middle_pos;
                    } else {
                        right_pos = middle_pos;
                    }
                }
                int next_pos = left_pos;
                result[i]++;
                pos = next_pos + 1;
            }
        }

        for (int i = 0; i < q; i++) {
            if (result[i] == -1) {
                System.out.println("Impossible");
            } else {
                System.out.println(result[i]);
            }
        }

    }
}