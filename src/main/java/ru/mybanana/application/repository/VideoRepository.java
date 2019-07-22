package ru.mybanana.application.repository;

import ru.mybanana.application.interfaces.Video;

import java.util.Optional;

public class VideoRepository implements Video {
    @Override
    public <S extends Video> S save(S s) {
        return null;
    }

    @Override
    public <S extends Video> Iterable<S> saveAll(Iterable<S> iterable) {
        return null;
    }

    @Override
    public Optional<Video> findById(Integer integer) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public Iterable<Video> findAll() {
        return null;
    }

    @Override
    public Iterable<Video> findAllById(Iterable<Integer> iterable) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Integer integer) {

    }

    @Override
    public void delete(Video video) {

    }

    @Override
    public void deleteAll(Iterable<? extends Video> iterable) {

    }

    @Override
    public void deleteAll() {

    }
}
