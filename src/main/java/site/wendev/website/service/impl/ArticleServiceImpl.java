package site.wendev.website.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import site.wendev.website.dao.ArticleRepository;
import site.wendev.website.entities.Article;
import site.wendev.website.entities.Type;
import site.wendev.website.service.ArticleService;
import site.wendev.website.vo.ArticleVO;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public Article findById(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @Override
    public Page<Article> list(Pageable pageable, ArticleVO article) {
        return articleRepository.findAll(new Specification<Article>() {
            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                if (!"".equals(article.getTitle()) && article.getTitle() != null) {
                    // 字符串模糊查询——like查询
                    predicates.add(criteriaBuilder.like(root.<String>get("title"), "%" + article.getTitle() + "%"));
                }

                if (article.getTypeId() != null) {
                    predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), article.getTypeId()));
                }

                query.where(predicates.toArray(new Predicate[predicates.size()]));
                return null;
            }
        }, pageable);
    }

    @Override
    public Article add(Article article) {
        return articleRepository.save(article);
    }

    @Override
    public Article modify(Long id, Article article) {
        var articleOptional = articleRepository.findById(id);
        if (articleOptional.isEmpty()) {
            return null;
        } else {
            var newArticle = articleOptional.get();
            newArticle.setTitle(article.getTitle());
            newArticle.setContent(article.getContent());
            newArticle.setType(article.getType());
            newArticle.setTags(article.getTags());
            newArticle.setUpdateTime(new Date());
            return articleRepository.save(newArticle);
        }
    }

    @Override
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}
