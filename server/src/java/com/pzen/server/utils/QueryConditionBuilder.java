package com.pzen.server.utils;

import com.pzen.utils.Condition;
import com.pzen.utils.StringUtils;
import io.ebean.ExpressionList;
import io.ebean.Query;

import java.util.Collection;
import java.util.List;
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
                if (value != null && StringUtils.isNotEmpty(String.valueOf(value)) && !String.valueOf(value).equals("null")) {
                    switch (type) {
                        case "eq"://等于
                            expressionList.eq(fieldName, value);
                            break;
                        case "ne"://不等于
                            expressionList.ne(fieldName, value);
                            break;
                        case "gt"://大于
                            expressionList.gt(fieldName, value);
                            break;
                        case "gte"://大于等于
                            expressionList.gt(fieldName, value).eq(fieldName, value);
                            break;
                        case "lt"://小于
                            expressionList.lt(fieldName, value);
                            break;
                        case "lte"://小于等于
                            expressionList.lt(fieldName, value).eq(fieldName, value);
                            break;
                        case "lik"://模糊查询
                            expressionList.like(fieldName, "%" + value + "%");
                            break;
                        case "startSwitch"://字符串末尾  模糊查询
                            expressionList.like(fieldName, value + "%");
                            break;
                        case "endSwitch": //字符串开头 模糊查询
                            expressionList.like(fieldName, "%" + value);
                            break;
                        case "i":// in 在其中
                            if (value instanceof Collection) {
                                expressionList.in(fieldName, (Collection<?>) value);
                            }
                            break;
                        case "notIn"://不在其中
                            if (value instanceof Collection) {
                                expressionList.notIn(fieldName, (Collection<?>) value);
                            }
                            break;
                        case "isNull"://为空
                            expressionList.isNull(fieldName);
                            break;
                        case "isNotNull"://不为空
                            expressionList.isNotNull(fieldName);
                            break;
                        case "between"://范围查询
                            if (value instanceof List<?> rangeValues) {
                                if (rangeValues.size() == 2) {
                                    expressionList.between(fieldName, rangeValues.get(0), rangeValues.get(1));
                                }
                            }
                            break;
                        default:
                            // 处理未知的条件类型
                            break;
                    }

                }
            }
        }
    }

}
