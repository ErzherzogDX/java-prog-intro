package md2html;

import java.io.*;
import java.util.*;

import static java.lang.Math.*;

public class Md2Html {
    boolean hashMode = false;
    boolean paragraphMode = false;
    int newLine_line = -1;
    int total_hash = 0;

    HashMap<String, String> convert = new HashMap<>();
    Stack<String> empStack = new Stack<>();

    public static void main(String[] args) {
        (new Md2Html()).apply(args[0], args[1]);
    }

    private void apply(String inputFileName, String outputFileName) {
        ArrayList<String> new_text = new ArrayList<String>();
        //String[] args = new String[2]; args[0] = "c:/Users/erzhe/Documents/in.txt"; args[1] = "c:/Users/erzhe/Documents/out.txt";
        boolean obrabotka = false;
        boolean isLongSpace = false;

        String line = "";
        String pastLine = "";
        empStack.push("");

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(new FileInputStream(inputFileName), "UTF8"));

            try {
                boolean work = true;
                while (work) {
                    line = reader.readLine();
                    newLine_line++;

                    if (line == null) {
                        work = false;
                        emptyLine(pastLine, new_text);
                        continue;
                    }
                    if (line.length() == 0) {
                        if (!isLongSpace) {
                            isLongSpace = true;
                            emptyLine(pastLine, new_text);
                            obrabotka = true;
                        }
                        continue;
                    } else {
                        line = " " + line + " ";
                        isLongSpace = false;

                        if (!obrabotka) {
                            new_text.add(pastLine);
                        } else {
                            obrabotka = false;
                        }
                    }

                    StringBuilder newLine = new StringBuilder();
                    int start = 1;
                    int hashNum = 0;

                    for (int i = 1; i < line.length(); i++) {
                        if (line.charAt(i) == '#') {
                            hashNum++;
                        } else break;
                    }

                    if (!hashMode && !paragraphMode) {
                        if (line.charAt(1) == '#') {
                            if (line.charAt(hashNum + 1) == ' ') {
                                hashMode = true;
                                total_hash = hashNum;
                            } else {
                                paragraphMode = true;
                            }
                        } else {
                            paragraphMode = true;
                        }

                        if (paragraphMode) {
                            newLine.append("<p>");
                        } else {
                            newLine.append("<h" + total_hash + ">");
                            start = total_hash + 2;
                        }
                    }

                    for (int i = 1; i < line.length(); i++) {
                        if (isSpecialChar(line.charAt(i))) {
                            newLine.append(line, start, i);

                            if (line.charAt(i) == '-') {
                                if (line.charAt(i + 1) == '-') {
                                    i++; newLine.append(formatting( "--"));
                                } else{
                                    start = i; continue;
                                }
                            }

                            if (line.charAt(i) == '`') {
                                newLine.append(formatting( "`"));
                            }

                            if (line.charAt(i) == '~') {
                                if (line.charAt(i - 1) == '\\') {
                                    shielding(line, i, newLine);
                                    start = i;
                                    continue;
                                }
                                newLine.append(formatting( "~"));

                            }

                            if (line.charAt(i) == '*' || line.charAt(i) == '_') {
                                int j = i;
                                while (true) {
                                    if (line.charAt(j) == '*' || line.charAt(j) == '_') {
                                        if (j - i > 0 && line.charAt(j) != line.charAt(j - 1)) {
                                            break;
                                        } j++;
                                    } else {
                                        break;
                                    }
                                }

                                String c = line.substring(i, j);
                                    if (empStack.peek().equals(c)) {
                                        empStack.pop();
                                        i = bolding(c, "</em>", "</strong>", newLine, i);
                                    } else {
                                        if (line.charAt(i + 1) == ' ' || line.charAt(i - 1) == '\\') {
                                            shielding(line, i, newLine);
                                            start = i;
                                            continue;
                                        }
                                        empStack.push(c);
                                        i = bolding(c, "<em>", "<strong>", newLine, i);
                                }
                            }

                            if (line.charAt(i) == '<') {
                                newLine.append("&lt;");
                            }
                            if (line.charAt(i) == '>') {
                                newLine.append("&gt;");
                            }
                            if (line.charAt(i) == '&') {
                                newLine.append("&amp;");
                            }

                            start = i + 1;
                        }
                    }

                    newLine.append(line, start, line.length() - 1);
                    pastLine = newLine.toString();
                }
            } finally {
                reader.close();
            }

            BufferedWriter writter = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(outputFileName), "UTF8"));
            int emptyLines = 0;
            for (int i = 0; i < new_text.size(); i++) {
                if (new_text.get(i).equals("")) {
                    emptyLines++;
                } else {
                    break;
                }
            }
            for (int i = emptyLines; i < new_text.size(); i++) {
                writter.write(new_text.get(i));
                writter.newLine();
            }
            writter.close();
        } catch (IOException e) {
            System.out.println("Invalid input! Вы проиграли!!! " + e.getMessage());
        }
    }

    private void emptyLine(String pastLine, ArrayList new_text) {
        newLine_line--;
        StringBuilder newLine_end = new StringBuilder();
        newLine_end.append(pastLine);

        if (hashMode) {
            hashMode = false;
            newLine_end.append("</h" + total_hash + ">");
        } else if (paragraphMode) {
            paragraphMode = false;
            newLine_end.append("</p>");
        }
        new_text.add(newLine_end.toString());
    }

    private static boolean isSpecialChar(char c) {
        return c == '*' || c == '-' || c == '_' || c == '`' || c == '~' || c == '<' || c == '>' || c == '&';
    }

    private void shielding(String line, int i, StringBuilder newLine) {
        int slashNum = 0;
        for (int z = i - 1; z >= 0; z--) {
            if (line.charAt(z) == '\\') {
                slashNum++;
            } else {
                break;
            }
        }
        newLine.setLength(newLine.length() - slashNum);
    }

    private int bolding(String c, String em, String strong, StringBuilder newLine, int i) {
        if (c.length() == 1) {
            newLine.append(em);
        } else {
            i++;
            newLine.append(strong);
        }
        return i;
    }
    private String formatting(String sym) {
        HashMap<String, String> convert = new HashMap<>();
        convert.put("`", "code");
        convert.put("--", "s");
        convert.put("~", "mark");
        convert.put("_", "em");
        convert.put("*", "em");
        convert.put("__", "strong");
        convert.put("**", "strong");

        StringBuilder s = new StringBuilder();
        s.append("<");
        if(empStack.peek().equals(sym)){
            s.append("/"); empStack.pop();
        } else empStack.push(sym);
        return (s.append(convert.get(sym) + ">")).toString();
    }
}