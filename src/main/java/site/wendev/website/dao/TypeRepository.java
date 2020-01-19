package site.wendev.website.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import site.wendev.website.entities.Type;

public interface TypeRepository extends JpaRepository<Type, Long> {
    Type findByName(String name);
}
