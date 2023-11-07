import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;


public class A {

    public static void main(String[] args) throws IOException {
        double a, b, n;
        Scanner in = new Scanner(System.in);

        a = in.nextInt();
        b = in.nextInt();
        n = in.nextInt();
        int b2 = (int) (Math.ceil ((n-b)/(b-a)));
        int res = 2*b2  + 1;
        System.out.println(res);
    }
}
