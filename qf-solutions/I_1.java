import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

import static java.lang.Math.*;


public class A {

    public static void main(String[] args) throws IOException {
        int n;
        Scanner in = new Scanner(System.in);

        n = in.nextInt();
        double xl = 999999999,  yl = 999999999;
        double xr = -999999999, yr = -999999999;

        for(int i = 0; i < n; i++){
            int x, y, h;
            x = in.nextInt();
            y = in.nextInt();
            h = in.nextInt();

            xl = min(xl, x-h);
            xr = max(xr, x+h);
            yl = min(yl, y-h);
            yr = max(yr, y+h);
        }

        int h = (int) (Math.ceil((max(xr - xl, yr - yl))/2));
        int x = (int) ((xl + xr)/2);
        int y = (int) ((yl + yr)/2);

        System.out.println(x + " " + y + " " + h);
    }
}
