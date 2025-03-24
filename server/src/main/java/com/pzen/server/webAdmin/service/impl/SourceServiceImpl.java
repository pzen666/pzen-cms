package com.pzen.server.webAdmin.service.impl;

import com.pzen.server.utils.EntityHelper;
import com.pzen.server.utils.QueryConditionBuilder;
import com.pzen.server.webAdmin.service.SourceService;
import com.pzen.webAdmin.dto.SourceDTO;
import com.pzen.webAdmin.entity.Source;
import io.ebean.DB;
import io.ebean.PagedList;
import io.ebean.Query;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SourceServiceImpl implements SourceService {

    @Override
    public Source add(SourceDTO dto) {
        Source source = new Source();
        EntityHelper.convertDtoToEntity(dto, source);
        DB.byName("db").save(source);
        return source;
    }

    @Override
    public Source del(SourceDTO dto) {
        Source source = DB.find(Source.class).where().eq("id", dto.getId()).findOne();
        if (source != null) {
            DB.byName("db").delete(source);
        }
        return source;
    }

    @Override
    public Source update(SourceDTO dto) {
        Source source = DB.find(Source.class).where().eq("id", dto.getId()).findOne();
        if (source != null) {
            DB.byName("db").update(source);
        }
        return source;
    }

    @Override
    public Source findOne(SourceDTO dto) {
        Query<Source> query = DB.byName("db").find(Source.class);
        // 使用工具类构建查询条件
        QueryConditionBuilder.buildConditions(query, dto.getConditions());
        return query.findOne();
    }

    @Override
    public Source getSourceInfo(Long id) {
        return DB.find(Source.class).where().eq("id", id).findOne();
    }

    @Override
    public Page<Source> findPage(Query<Source> query, SourceDTO dto) {
        // 使用工具类构建查询条件
        QueryConditionBuilder.buildConditions(query, dto.getConditions());
        // 创建 Pageable 对象
        int page = dto.getPage() != null ? dto.getPage() : 0; // 页码，从0开始
        int pageSize = dto.getPageSize() != null ? dto.getPageSize() : 10; // 每页大小
        Sort sort = Sort.by(Sort.Direction.ASC, dto.getSortName()); // 排序，按 fieldName 升序排序
        if (dto.getSortName() != null && !dto.getSortName().isEmpty() && dto.getSortOrder() != null && !dto.getSortOrder().isEmpty()) {
            if (dto.getSortOrder().equals("desc")) {
                sort = Sort.by(Sort.Direction.ASC, dto.getSortName());
            } else {
                sort = Sort.by(Sort.Direction.DESC, dto.getSortName());
            }
        }
        Pageable pageable = PageRequest.of(page, pageSize, sort);
        int startIndex = page * pageSize;
        Optional<Sort.Order> optional = pageable.getSort().stream().findAny();
        String orderBy = "";
        if (optional.isPresent()) {
            orderBy = optional.get().getProperty() + " " + optional.get().getDirection();
        }
        PagedList<Source> pagedList = query
                .setFirstRow(startIndex)
                .setMaxRows(pageSize)
                .orderBy(orderBy)
                .findPagedList();
        return new PageImpl<>(pagedList.getList(), pageable, pagedList.getTotalCount());
    }

}
