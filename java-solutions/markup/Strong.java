package markup;
import java.util.List;

public class Strong extends Markup {

    public Strong(List<MarkdownManager> words) {
        super(words);
    }
    @Override
    public void toMarkdown(StringBuilder sb) {
        super.toMarkdown(sb, "__");
    }
    @Override
    public void toHtml(StringBuilder sb) {
        super.toHtml(sb, "<strong>", "</strong>");
    }
}
