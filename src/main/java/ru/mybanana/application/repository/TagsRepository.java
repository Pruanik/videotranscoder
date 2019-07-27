package ru.mybanana.application.repository;

import org.springframework.data.repository.CrudRepository;
import ru.mybanana.application.models.Tags;

public interface TagsRepository extends CrudRepository<Tags, Integer> {
}
