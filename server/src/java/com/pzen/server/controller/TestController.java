package com.pzen.server.controller;

import com.pzen.dto.TestDTO;
import com.pzen.entity.Test;
import com.pzen.entity.Test2;
import com.pzen.server.service.TestService;
import com.pzen.server.utils.QueryConditionBuilder;
import com.pzen.utils.Result;
import io.ebean.DB;
import io.ebean.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
@RequestMapping("/test")
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }

    //入参为:
    // {
    //  "page" : 0,
    //  "size" : 10,
    //  "sortName" : "videoId",
    //  "conditions" : {
    //    "videoName" : {"type": "equals","value": "1"},
    //    "name" : {"type": "equals","value": null}
    //  }
    //}
    @RequestMapping("/find")
    public Result<Page<Test>> find(@RequestBody TestDTO dto) {
        Query<Test> query = DB.find(Test.class);
        // 使用工具类构建查询条件
        QueryConditionBuilder.buildConditions(query, dto.getConditions());
        // 创建 Pageable 对象
        int page = dto.getPage() != null ? dto.getPage() : 0; // 页码，从0开始
        int size = dto.getPageSize() != null ? dto.getPageSize() : 10; // 每页大小
        Sort sort = Sort.by(Sort.Direction.ASC, dto.getSortName()); // 排序，按 fieldName 升序排序
        if (dto.getSortName() != null && !dto.getSortName().isEmpty() && dto.getSortOrder() != null && !dto.getSortOrder().isEmpty()) {
            if (dto.getSortOrder().equals("desc")) {
                sort = Sort.by(Sort.Direction.ASC, dto.getSortName());
            } else {
                sort = Sort.by(Sort.Direction.DESC, dto.getSortName());
            }
        }
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Test> list = testService.findPage(query, pageable);
        return Result.success(list, null);
    }

    @RequestMapping("/findAll")
    public Result<Page<Test>> findAll(@RequestBody TestDTO dto) {
        Page<Test> list = testService.findAllPage(dto);
        return Result.success(list, null);
    }

    //{
    //  "videoName" : "2",
    //  "name" : "1"
    //}
    @RequestMapping("/save")
    public Result<Object> save(@RequestBody TestDTO dto) {
        Test test = testService.add(dto);
        return Result.success(test, null);
    }

    @RequestMapping("/saveDB2")
    public Result<Object> save2(@RequestBody TestDTO dto) {
        Test2 test = testService.add2(dto);
        return Result.success(test, null);
    }

    //{
    //  "videoName" : "222",
    //  "name" : "1"
    //}
    @RequestMapping("/update")
    public Result<Object> update(@RequestBody TestDTO dto) {
        Test test = testService.update(dto);
        return Result.success(test, null);
    }

    @RequestMapping("/delete")
    public Result<Object> delete(@RequestBody TestDTO dto) {
        Test test = testService.del(dto);
        return Result.success(test, null);
    }

    //{
    //  "videoName" : "222",
    //  "name" : "1"
    //}
    @RequestMapping("/deleteRollback")
    public Result<Object> deleteRollback(@RequestBody TestDTO dto) {
        Test test = testService.deleteRollback(dto);
        return Result.success(test, null);
    }

}
