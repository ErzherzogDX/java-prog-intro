import java.io.*;
import java.nio.charset.Charset;

public class EScanner {

    private final char[] buffer = new char[512];
    private final InputStreamReader reader;
    private int currentChar = 0;
    private int endChar = 0;

    public EScanner(InputStream stream, Charset charset) {
        reader = new InputStreamReader(stream, charset);
    }

    public boolean hasNextLine() throws IOException {
        updateBuffer();
        return currentChar <= endChar;
    }

    public String nextLine() throws IOException {
        StringBuilder line = new StringBuilder();
        int startChar = currentChar;

        while (currentChar <= endChar) {
            if (currentChar == endChar) {
                line.append(buffer, startChar, currentChar - startChar);
                updateBuffer();
                startChar = 0;
            } else {
                if (!stopSymbol(buffer[currentChar])) {
                    currentChar++;
                } else break;
            }
        }

        line.append(buffer, startChar, currentChar - startChar);
        
        if (buffer[currentChar] == '\r') {
            if (currentChar + 1 >= endChar) {
                currentChar++;
                updateBuffer();
                if (buffer[currentChar] == '\n') currentChar++;
            } else {
                if (buffer[currentChar + 1] == '\n') currentChar += 2;
                else currentChar++;
            }
        } else currentChar++;

        return line.toString();
    }

    public void close() throws IOException {
        reader.close();
    }

    private void updateBuffer() throws IOException {
        if (currentChar >= endChar) {
            currentChar = 0;
            endChar = reader.read(buffer);
        }
    }

    private boolean stopSymbol(char a) {
        return (a == '\r' || a == '\n');
    }

}
