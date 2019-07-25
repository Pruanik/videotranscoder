package ru.mybanana.application.repository;

import org.springframework.data.repository.CrudRepository;
import ru.mybanana.application.models.VideoModel;

public interface VideoRepository extends CrudRepository<VideoModel, Integer> {
}
