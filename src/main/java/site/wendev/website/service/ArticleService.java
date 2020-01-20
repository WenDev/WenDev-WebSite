package site.wendev.website.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.wendev.website.entities.Article;
import site.wendev.website.vo.ArticleVO;

public interface ArticleService {
    Article findById(Long id);
    Page<Article> list(Pageable pageable, ArticleVO article);
    Article add(Article article);
    Article modify(Long id, Article article);
    void delete(Long id);
}
