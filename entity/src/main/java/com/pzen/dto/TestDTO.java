package com.pzen.dto;


import com.pzen.utils.Condition;
import lombok.Data;

import java.util.Map;

@Data
public class TestDTO {


    private Integer page;
    private Integer pageSize;
    private String sortName;//排序字段
    private String sortOrder;//排序方式
    private Map<String, Condition> conditions; // 使用 Map 来存储多个查询条件
    public String getSortName() {
        return (sortName == null || sortName.isEmpty()) ? "videoId" : sortName;
    }




    private Long videoId;
    private String videoName;
    private String name;
}
