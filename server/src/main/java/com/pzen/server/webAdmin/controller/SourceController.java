package com.pzen.server.webAdmin.controller;

import com.pzen.server.webAdmin.service.SourceService;
import com.pzen.utils.Result;
import com.pzen.webAdmin.dto.SourceDTO;
import com.pzen.webAdmin.entity.Source;
import io.ebean.DB;
import io.ebean.Query;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 采集数据源管理
 */
@RestController
@CrossOrigin
@RequestMapping("/source")
public class SourceController {


    private final SourceService sourceService;

    public SourceController(SourceService sourceService) {
        this.sourceService = sourceService;
    }

    @PostMapping("/save")
    public Result<Object> save(@RequestBody SourceDTO dto) {
        Source s = sourceService.add(dto);
        return Result.success(s, null);
    }

    @PostMapping("/delete")
    public Result<Object> delete(@RequestBody SourceDTO dto) {
        Source s = sourceService.del(dto);
        return Result.success(s, null);
    }

    @PostMapping("/update")
    public Result<Object> update(@RequestBody SourceDTO dto) {
        Source s = sourceService.update(dto);
        return Result.success(s, null);
    }

    @PostMapping("/findOne")
    public Result<Source> findOne(@RequestBody SourceDTO dto) {
        Source s = sourceService.findOne(dto);
        return Result.success(s, null);
    }

    @GetMapping("/getSourceInfo/{id}")
    public Result<Source> getSourceInfo(@PathVariable Long id) {
        Source s = sourceService.getSourceInfo(id);
        return Result.success(s);
    }

    @PostMapping("/page")
    public Result<Page<Source>> page(@RequestBody SourceDTO dto) {
        Query<Source> query = DB.find(Source.class);
        Page<Source> list = sourceService.findPage(query, dto);
        return Result.success(list, null);
    }


}
