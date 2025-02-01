package com.pzen.server.utils;

import com.pzen.utils.Condition;
import io.ebean.ExpressionList;
import io.ebean.Query;

import java.util.Map;

public class QueryConditionBuilder {

    public static <T> void buildConditions(Query<T> query, Map<String, Condition> conditions) {
        if (conditions != null) {
            ExpressionList<T> expressionList = query.where();
            for (Map.Entry<String, Condition> entry : conditions.entrySet()) {
                String fieldName = entry.getKey();
                Condition condition = entry.getValue();
                String type = condition.getType();
                Object value = condition.getValue();
                switch (type) {
                    case "equals":
                        expressionList.eq(fieldName, value);
                        break;
                    case "like":
                        expressionList.like(fieldName, "%" + value + "%");
                        break;
                    case "gt":
                        expressionList.gt(fieldName, value);
                        break;
                    case "lt":
                        expressionList.lt(fieldName, value);
                        break;
                    // 可以添加更多条件类型
                    default:
                        break;
                }
            }
        }
    }

}
