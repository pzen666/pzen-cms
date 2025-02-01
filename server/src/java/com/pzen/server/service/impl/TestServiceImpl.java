package com.pzen.server.service.impl;

import com.pzen.dto.TestDTO;
import com.pzen.entity.Test;
import com.pzen.entity.Test2;
import com.pzen.server.service.TestService;
import com.pzen.server.utils.EntityHelper;
import com.pzen.server.utils.QueryConditionBuilder;
import io.ebean.DB;
import io.ebean.PagedList;
import io.ebean.Query;
import io.ebean.annotation.Transactional;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class TestServiceImpl implements TestService {

    @Override
    public Page<Test> findPage(Query<Test> query, Pageable pageable) {
        int page = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();
        int startIndex = page * pageSize;
        Optional<Sort.Order> optional = pageable.getSort().stream().findAny();
        String orderBy = "";
        if (optional.isPresent()) {
            orderBy = optional.get().getProperty() + " " + optional.get().getDirection();
        }
        PagedList<Test> pagedList = query
                .setFirstRow(startIndex)
                .setMaxRows(pageSize)
                .orderBy(orderBy)
                .findPagedList();
        return new PageImpl<>(pagedList.getList(), pageable, pagedList.getTotalCount());
    }

    @Override
    public Page<Test> findAllPage(TestDTO dto) {
        Query<Test> query = DB.find(Test.class);
        // 使用工具类构建查询条件
        QueryConditionBuilder.buildConditions(query, dto.getConditions());
        // 创建 Pageable 对象
        int page = dto.getPage() != null ? dto.getPage() : 0; // 页码，从0开始
        int pageSize = dto.getPageSize() != null ? dto.getPageSize() : 10; // 每页大小
        int startIndex = page * pageSize;
        Sort sort = Sort.by(Sort.Direction.ASC, dto.getSortName()); // 排序，按 fieldName 升序排序
        if (dto.getSortName() != null && !dto.getSortName().isEmpty() && dto.getSortOrder() != null && !dto.getSortOrder().isEmpty()) {
            if (dto.getSortOrder().equals("desc")) {
                sort = Sort.by(Sort.Direction.ASC, dto.getSortName());
            } else {
                sort = Sort.by(Sort.Direction.DESC, dto.getSortName());
            }
        }
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        Optional<Sort.Order> optional = pageable.getSort().stream().findAny();
        String orderBy = "";
        if (optional.isPresent()) {
            orderBy = optional.get().getProperty() + " " + optional.get().getDirection();
        }
        PagedList<Test> pagedList = query
                .setFirstRow(startIndex)
                .setMaxRows(pageSize)
                .orderBy(orderBy)
                .findPagedList();
        return new PageImpl<>(pagedList.getList(), pageable, pagedList.getTotalCount());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Test add(TestDTO dto) {
        Test test = new Test();
        EntityHelper.convertDtoToEntity(dto, test);
//        test.save();
        DB.byName("db").save(test);
        return test;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Test2 add2(TestDTO dto) {
        Test2 test = new Test2();
        EntityHelper.convertDtoToEntity(dto, test);
//        test.save();
        DB.byName("db2").save(test);
        return test;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Test update(TestDTO dto) {
        //        Test test = DB.find(Test.class, dto.getVideoId());
        Test test = DB.find(Test.class).where().eq("videoName", dto.getVideoName()).findOne();
        EntityHelper.convertDtoToEntity(dto, test);
        return test;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Test del(TestDTO dto) {
        Test test = DB.find(Test.class).where().eq("videoName", dto.getVideoName()).findOne();
        if (test != null) {
            test.delete();
        }
        return test;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Test deleteRollback(TestDTO dto) {
        Test test = DB.find(Test.class).where().eq("videoName", dto.getVideoName()).findOne();
        if (test != null) {
            test.delete();
        }
        try {
            //添加强制报错;
            long a = 1 / 0;
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return test;
    }

}
