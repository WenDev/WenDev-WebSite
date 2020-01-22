package site.wendev.website.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import site.wendev.website.entities.Type;

import java.util.List;

public interface TypeService {
    Type addType(Type type);
    Type findByName(Type type);
    Type getType(Long typeId);
    Page<Type> listType(Pageable pageable);
    List<Type> listType(Integer size);
    Type modify(Long typeId, String newName);
    void deleteType(Long typeId);
}
