package ru.mybanana.application.repository;

import org.springframework.data.repository.CrudRepository;
import ru.mybanana.application.models.Videos;

public interface VideoRepository extends CrudRepository<Videos, Integer> {
}
