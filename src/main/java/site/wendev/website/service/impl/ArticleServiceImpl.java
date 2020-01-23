package site.wendev.website.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.wendev.website.dao.ArticleRepository;
import site.wendev.website.entities.Article;
import site.wendev.website.entities.Type;
import site.wendev.website.exception.NotFoundException;
import site.wendev.website.service.ArticleService;
import site.wendev.website.util.MarkdownUtils;
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
    @Transactional
    public Article findAndConvert(Long id) {
        var articleOptional = articleRepository.findById(id);
        if (articleOptional.isEmpty()) {
            throw new NotFoundException("id为" + id + "的文章不存在");
        } else {
            var article = new Article();
            var article1 = articleOptional.get();
            BeanUtils.copyProperties(article1, article);
            var content = article.getContent();
            article.setContent(MarkdownUtils.markdown2Html(content));

            articleRepository.updateViews(id);

            return article;
        }
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
    public Page<Article> list(Pageable pageable, String param) {
        return articleRepository.findByParam(param, pageable);
    }

    @Override
    public Page<Article> list(Pageable pageable) {
        return articleRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Article add(Article article) {
        return articleRepository.save(article);
    }

    @Override
    @Transactional
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
    @Transactional
    public void delete(Long id) {
        articleRepository.deleteById(id);
    }
}
