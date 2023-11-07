
public class Sum {
    public static void main(String[] args) {
        int sum = 0;

        for (int i = 0; i < args.length; i++) {
            int number = 0;

            String s = "";
            int start = 0;
            int finish = 0;

            String arg = args[i].strip();

            for (int j = 0; j < arg.length() + 1; j++) {
                if (j != arg.length()) {
                    finish++;
                }
                if (j == arg.length() || Character.isWhitespace(arg.charAt(j))) {
                    s = arg.substring(start, finish);
                    s = s.strip();
                    start = finish;

                    if (!s.isEmpty()) {
                        number = Integer.parseInt(s);
                        sum += number;
                    }
                    s = "";
                }
            }
        }

        System.out.println(sum);
    }
}