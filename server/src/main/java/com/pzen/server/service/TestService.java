package com.pzen.server.service;

import com.pzen.dto.TestDTO;
import com.pzen.entity.Test;
import com.pzen.entity.Test2;
import io.ebean.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TestService {

    Page<Test> findPage(Query<Test> query, Pageable pageable);

    Page<Test> findAllPage(TestDTO dto);


    Test add(TestDTO dto);

    Test2 add2(TestDTO dto);

    Test update(TestDTO dto);

    Test del(TestDTO dto);

    Test deleteRollback(TestDTO dto);

}
