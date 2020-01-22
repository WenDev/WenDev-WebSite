package site.wendev.website.util;

import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Code;
import org.commonmark.node.Node;
import org.commonmark.node.Paragraph;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.AttributeProvider;
import org.commonmark.renderer.html.AttributeProviderContext;
import org.commonmark.renderer.html.AttributeProviderFactory;
import org.commonmark.renderer.html.HtmlRenderer;

import java.util.Arrays;
import java.util.Map;

public class MarkdownUtils {
    /**
     * Markdown格式的文档转换成HTML
     */
    public static String markdown2Html(String markdown) {
        var parser = Parser.builder().build();
        var render = HtmlRenderer.builder().build();
        var document = parser.parse(markdown);
        return render.render(document);
    }

    public static String extensions(String markdown) {
        var tableExtension = Arrays.asList(TablesExtension.create());
        var parser = Parser.builder().extensions(tableExtension).build();
        var document = parser.parse(markdown);
        var render = HtmlRenderer.builder().extensions(tableExtension)
                .attributeProviderFactory(new AttributeProviderFactory() {
                    @Override
                    public AttributeProvider create(AttributeProviderContext attributeProviderContext) {
                        return new CustomAttributeProvider();
                    }
                }).build();
        return render.render(document);
    }

    public static class CustomAttributeProvider implements AttributeProvider {
        @Override
        public void setAttributes(Node node, String s, Map<String, String> map) {
            if (node instanceof TableBlock) {
                map.put("class", "table table-striped");
            }

            if (node instanceof Code) {
                map.put("class", "prettyprint linenums");
            }
        }
    }
}
