
public class SumFloat {
    public static void main(String[] args) {
        float sum = 0;

        for (int i = 0; i < args.length; i++) {
            float number = 0;

            String s = "";
            int start = 0;
            int finish = 0;

            // String arg = args[i];

            for (int j = 0; j < args[i].length() + 1; j++) {
                if (j != args[i].length()) {
                    finish++;
                }
                if (j == args[i].length() || Character.isWhitespace(args[i].charAt(j))) {
                    s = args[i].substring(start, finish);
                    s = s.strip();
                    start = finish;
                    //isBlank();
                    if (!s.isEmpty()) {
                        number = Float.parseFloat(s);
                        sum += number;
                    }
                    s = "";
                }
            }
        }

        System.out.println(sum);
    }
}