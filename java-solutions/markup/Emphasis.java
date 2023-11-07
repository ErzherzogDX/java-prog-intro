package markup;
import java.util.List;

public class Emphasis extends Markup{

    public Emphasis(List<MarkdownManager> words) {
        super(words);
    }
    @Override
    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb, "*");
    }
    @Override
    public void toHtml(StringBuilder sb) {
        super.toHtml(sb, "<em>", "</em>");
    }
}
