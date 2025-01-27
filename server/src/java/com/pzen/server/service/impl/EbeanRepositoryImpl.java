package com.pzen.server.service.impl;

import com.pzen.server.service.EbeanRepository;
import io.ebean.DB;
import io.ebean.PagedList;
import io.ebean.Query;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.FluentQuery;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class EbeanRepositoryImpl<T, ID> implements EbeanRepository<T, ID> {

    private final Class<T> entityType;

    public EbeanRepositoryImpl(Class<T> entityType){
        this.entityType = entityType;
    }

    @Override
    public <S extends T> S save(S object) {
        DB.save(object);
        return object;
    }

    @Override
    public <S extends T> S update(S object) {
        DB.update(object);
        return object;
    }

    @Override
    public <S extends T> List<S> saveAll(List<S> object) {
        DB.saveAll(object);
        return object;
    }

    @Override
    public <S extends T> List<S> updateAll(List<S> object) {
        DB.updateAll(object);
        return object;
    }

    @Override
    public List<T> findAll() {
        return DB.find(entityType).findList();
    }

    @Override
    public Page<T> findAll(Query<T> query, Pageable pageable) {
        int page = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int startIndex = page * pageSize;

        Optional<Sort.Order> optional = pageable.getSort().stream().findAny();
        String orderBy = "";

        if (optional.isPresent()){
            orderBy = optional.get().getProperty() + " " + optional.get().getDirection();
        }

        PagedList<T> pagedList = query
                .setFirstRow(startIndex)
                .setMaxRows(pageSize)
                .orderBy(orderBy)
                .findPagedList();

        return new PageImpl<>(pagedList.getList(), pageable, pagedList.getTotalCount());
    }

    @Override
    public List<T> findAllById(List<ID> id) {
        return DB.find(entityType).where().in("id", id).findList();
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(DB.find(entityType, id));
    }

    @Override
    public Iterable<T> findAll(Sort sort) {
        Optional<Sort.Order> optional = sort.stream().findAny();
        String orderBy = "";
        if (optional.isPresent()){
            orderBy = optional.get().getProperty() + " " + optional.get().getDirection();
        }
        return DB.find(entityType).where().orderBy().desc(orderBy).findList();
//        return DB.find(entityType).where().order(orderBy).findList();
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        int page = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int startIndex = page * pageSize;

        PagedList<T> pagedList = DB.find(entityType)
                .setFirstRow(startIndex)
                .setMaxRows(pageSize)
                .findPagedList();

        return new PageImpl<>(pagedList.getList(), pageable, pagedList.getTotalCount());
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return (Optional<S>) Optional.ofNullable(DB.find(entityType).findOne());
    }

    @Override
    public <S extends T> Iterable<S> findAll(Example<S> example) {
        return null;
    }

    @Override
    public <S extends T> Iterable<S> findAll(Example<S> example, Sort sort) {
        return null;
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends T> long count(Example<S> example) {
        return DB.find(entityType).findCount();
    }

    @Override
    public <S extends T> boolean exists(Example<S> example) {
        return count(example) > 0;
    }

    @Override
    public <S extends T, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }
}