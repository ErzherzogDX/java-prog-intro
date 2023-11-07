package markup;
public class Text implements MarkdownManager {

    private final String text;

    public Text(String text) {
        this.text = text;
    }
    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(text);
    }

    @Override
    public void toHtml(StringBuilder sb) {
        sb.append(text);
    }
}
