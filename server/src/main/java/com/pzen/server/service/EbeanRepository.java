package com.pzen.server.service;

import io.ebean.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface EbeanRepository<T, ID> extends PagingAndSortingRepository<T, ID>, QueryByExampleExecutor<T> {

    <S extends T> S save(S object);

    <S extends T> S update(S object);

    <S extends T> List<S> saveAll(List<S> object);

    <S extends T> List<S> updateAll(List<S> object);

    List<T> findAll();

    Page<T> findAll(Query<T> query, Pageable pageable);

    List<T> findAllById(List<ID> id);

    Optional<T> findById(ID id);

}
