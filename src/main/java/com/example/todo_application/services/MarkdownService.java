package com.example.todo_application.services;

import lombok.Data;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.node.Node;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

import org.commonmark.Extension;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

@Service
@Data
public class MarkdownService {
    private final Parser parser = Parser.builder().build();
    private final HtmlRenderer renderer = HtmlRenderer.builder().build();

    public MarkdownService() {}

    public String convertToHtml(String str) {
        List<Extension> extensions = Collections.singletonList(
                StrikethroughExtension.create()
        );

        // Настраиваем парсер и рендерер
        Parser parser = Parser.builder()
                .extensions(extensions)
                .build();

        HtmlRenderer renderer = HtmlRenderer.builder()
                .extensions(extensions)
                .build();

        // Парсим Markdown и рендерим в HTML
        Node document = parser.parse(str);
        String html = renderer.render(document);
        System.out.println("Generated HTML: " + html); // Логгируем результат
        return html;
    }
}
