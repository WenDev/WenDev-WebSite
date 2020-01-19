package site.wendev.website.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import site.wendev.website.entities.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
}
