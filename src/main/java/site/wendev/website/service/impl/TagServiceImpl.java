package site.wendev.website.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.wendev.website.dao.TagRepository;
import site.wendev.website.entities.Tag;
import site.wendev.website.service.TagService;

/**
 * 标签相关服务的接口实现类
 *
 * @author jiangwen
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Override
    @Transactional
    public Tag add(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    @Transactional
    public Tag findByName(Tag tag) {
        return tagRepository.findByName(tag.getName());
    }

    @Override
    @Transactional
    public Tag findById(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Tag modify(Long id, Tag tag) {
        var tagOptional = tagRepository.findById(id);
        if (tagOptional.isEmpty()) {
            return null;
        } else {
            var newTag = tagOptional.get();
            newTag.setName(tag.getName());
            return tagRepository.save(newTag);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }
}
