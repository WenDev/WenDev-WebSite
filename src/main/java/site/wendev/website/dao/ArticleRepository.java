package site.wendev.website.dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import site.wendev.website.entities.Article;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long>, JpaSpecificationExecutor<Article> {
    @Query("select article from Article article where article.title like ?1 or article.content like ?1")
    Page<Article> findByParam(String param, Pageable pageable);

    @Modifying
    @Transactional
    @Query("update Article a set a.views = a.views + 1 where a.id = ?1")
    int updateViews(Long id);

    @Query("select function('date_format', a.createTime, '%Y') as year from Article a group by function('date_format', a.createTime, '%Y') order by year desc")
    List<String> findByGroupYear();

    @Query("select a from Article a where function('date_format', a.createTime, '%Y') = ?1")
    List<Article> findByYear(String year);
}
