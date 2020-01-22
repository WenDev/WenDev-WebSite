package site.wendev.website.util;

import org.commonmark.ext.gfm.tables.TableBlock;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.node.Code;
import org.commonmark.node.IndentedCodeBlock;
import org.commonmark.node.Node;
import org.commonmark.node.Paragraph;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.NodeRenderer;
import org.commonmark.renderer.html.*;

import java.util.*;

public class MarkdownUtils {
    /**
     * Markdown格式的文档转换成HTML
     */
    public static String markdown2Html1(String markdown) {
        var parser = Parser.builder().build();
        var render = HtmlRenderer.builder().build();
        var document = parser.parse(markdown);
        return render.render(document);
    }

    public static String markdown2Html(String markdown) {
        var tableExtension = Arrays.asList(TablesExtension.create());
        var parser = Parser.builder().extensions(tableExtension).build();
        var document = parser.parse(markdown);
        var render = HtmlRenderer.builder().nodeRendererFactory(new HtmlNodeRendererFactory() {
            @Override
            public NodeRenderer create(HtmlNodeRendererContext context) {
                return new IndentedCodeBlockNodeRenderer(context);
            }
        })
                .extensions(tableExtension)
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
        }
    }

    static class IndentedCodeBlockNodeRenderer implements NodeRenderer {

        private final HtmlWriter html;

        IndentedCodeBlockNodeRenderer(HtmlNodeRendererContext context) {
            this.html = context.getWriter();
        }

        @Override
        public Set<Class<? extends Node>> getNodeTypes() {
            // Return the node types we want to use this renderer for.
            return Collections.<Class<? extends Node>>singleton(IndentedCodeBlock.class);
        }

        @Override
        public void render(Node node) {
            var attrs = new HashMap<String, String>();
            attrs.put("class", "prettyprint linenums");
            IndentedCodeBlock codeBlock = (IndentedCodeBlock) node;
            html.line();
            html.tag("pre", attrs);
            html.text(codeBlock.getLiteral());
            html.tag("/pre");
            html.line();
        }
    }
}
