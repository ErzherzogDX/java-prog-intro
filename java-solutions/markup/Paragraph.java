package markup;
import java.util.List;

public class Paragraph extends Markup {
    public Paragraph(List<MarkdownManager> list) {
        super(list);
    }
    @Override
    public void toMarkdown(StringBuilder sb){
        super.toMarkdown(sb, "");
    }
    @Override
    public void toHtml(StringBuilder sb) {
        super.toHtml(sb, "", "");
    }
}
