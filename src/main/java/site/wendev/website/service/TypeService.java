package site.wendev.website.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.wendev.website.entities.Type;

public interface TypeService {
    Type addType(Type type);
    Type findByName(Type type);
    Type getType(Long typeId);
    Page<Type> listType(Pageable pageable);
    Type modify(Long typeId, String newName);
    void deleteType(Long typeId);
}
