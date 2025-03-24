package com.pzen.server.webAdmin.service;

import com.pzen.webAdmin.dto.SourceDTO;
import com.pzen.webAdmin.entity.Source;
import io.ebean.Query;
import org.springframework.data.domain.Page;

public interface SourceService {

    Source add(SourceDTO dto);

    Source del(SourceDTO dto);

    Source update(SourceDTO dto);

    Source findOne(SourceDTO dto);

    Source getSourceInfo(Long id);

    Page<Source> findPage(Query<Source> query, SourceDTO dto);

}
