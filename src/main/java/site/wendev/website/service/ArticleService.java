package site.wendev.website.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import site.wendev.website.entities.Article;
import site.wendev.website.vo.ArticleVO;

import java.util.List;
import java.util.Map;

public interface ArticleService {
    Article findById(Long id);
    Article findAndConvert(Long id);
    Page<Article> list(Pageable pageable, ArticleVO article);
    Page<Article> list(Pageable pageable);
    Page<Article> list(Long tagId, Pageable pageable);
    Page<Article> list(Pageable pageable, String param);
    Map<String, List<Article>> archiveArticle();
    Article add(Article article);
    Article modify(Long id, Article article);
    void delete(Long id);
}
