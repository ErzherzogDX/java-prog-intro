package markup;
import java.util.List;

abstract class Markup implements MarkdownManager{
    protected List<MarkdownManager> list;
    protected Markup(List<MarkdownManager> words) {
        this.list = words;
    }
    protected void toMarkdown(StringBuilder sb, String token) {
        sb.append(token);
        for (MarkdownManager word : list) {
            word.toMarkdown(sb);
        }
        sb.append(token);
    }

    protected void toHtml(StringBuilder sb, String start, String finish) {
        sb.append(start);
        for (MarkdownManager word : list) {
            word.toHtml(sb);
        }
        sb.append(finish);
    }
}
