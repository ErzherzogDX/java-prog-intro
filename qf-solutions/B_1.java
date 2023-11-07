import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class A {

    public static void main(String[] args) throws IOException {
        int n;
        final double pi = 3.1415926536;
        Scanner in = new Scanner(System.in);

        n = in.nextInt();
        final int maximum = 50000;
        int x = 0;
        double mini = 1000;

        for(int i = 0; i < maximum; i++){
            double v = i - (int)((i / (2 * pi))) * 2 * pi;
            v+=(4*pi);

            while(v > (2*pi)) {
                v = v - (2*pi);
            }

            if(v < mini){
                mini = v;
                x = i;
            }
        }

        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = -25000; i <= 25000; i++) {
            list.add(i*x);
        }

        for (int i = 0; i < n; i++) {
            System.out.println(list.get(i));
        }
    }
}
