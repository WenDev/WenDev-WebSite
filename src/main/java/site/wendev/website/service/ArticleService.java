package site.wendev.website.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import site.wendev.website.entities.Article;
import site.wendev.website.vo.ArticleVO;

public interface ArticleService {
    Article findById(Long id);
    Article findAndConvert(Long id);
    Page<Article> list(Pageable pageable, ArticleVO article);
    Page<Article> list(Pageable pageable);
    Page<Article> list(Pageable pageable, String param);
    Article add(Article article);
    Article modify(Long id, Article article);
    void delete(Long id);
}
