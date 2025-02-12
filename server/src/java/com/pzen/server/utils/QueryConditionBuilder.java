package com.pzen.server.utils;

import com.pzen.utils.Condition;
import com.pzen.utils.StringUtils;
import io.ebean.ExpressionList;
import io.ebean.Query;

import java.util.Collection;
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
                        case "eq":
                            expressionList.eq(fieldName, value);
                            break;
                        case "ne":
                            expressionList.ne(fieldName, value);
                            break;
                        case "gt":
                            expressionList.gt(fieldName, value);
                            break;
                        case "gte":
                            expressionList.gtIfPresent(fieldName, value);
                            break;
                        case "lt":
                            expressionList.lt(fieldName, value);
                            break;
                        case "lte":
                            expressionList.ltIfPresent(fieldName, value);
                            break;
                        case "lik":
                            expressionList.like(fieldName, "%" + value + "%");
                            break;
                        case "startSwitch":
                            expressionList.like(fieldName, value + "%");
                            break;
                        case "endSwitch":
                            expressionList.like(fieldName, "%" + value);
                            break;
                        case "in":
                            if (value instanceof Collection) {
                                expressionList.in(fieldName, (Collection<?>) value);
                            }
                            break;
                        case "notin":
                            if (value instanceof Collection) {
                                expressionList.notIn(fieldName, (Collection<?>) value);
                            }
                            break;
                        case "isNull":
                            expressionList.isNull(fieldName);
                            break;
                        case "isNotNull":
                            expressionList.isNotNull(fieldName);
                            break;
                        case "between":
                            if (value instanceof Object[] rangeValues) {
                                if (rangeValues.length == 2) {
                                    expressionList.between(fieldName, rangeValues[0], rangeValues[1]);
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
