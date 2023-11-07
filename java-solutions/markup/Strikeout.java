package markup;
import java.util.List;

public class Strikeout extends Markup {

    public Strikeout(List<MarkdownManager> words) {
        super(words);
    }
    @Override
    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb, "~");
    }
    @Override
    public void toHtml(StringBuilder sb) {
        super.toHtml(sb, "<s>", "</s>");
    }
}
