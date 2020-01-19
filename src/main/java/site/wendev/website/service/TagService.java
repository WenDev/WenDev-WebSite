package site.wendev.website.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.wendev.website.entities.Tag;

/**
 * Tag相关服务的接口
 *
 * @author jiangwen
 */
public interface TagService {
    Tag add(Tag tag);
    Tag findByName(Tag tag);
    Tag findById(Long id);
    Tag modify(Long id, Tag tag);
    void delete(Long id);
    Page<Tag> listTag(Pageable pageable);
}
