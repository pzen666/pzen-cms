package com.pzen.server.utils;

import cn.hutool.json.JSONObject;
import com.pzen.server.filter.xssAndSqlFilter.XssAndSqlHttpServletRequestWrapper;
import com.pzen.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;

public class XssSqlUtils {


    public static boolean object(Object j) {
        if (j != null) {
            String param = "";
            if (j instanceof String) {
                param = j.toString();
            }
            if (j instanceof JSONObject) {
                param = JsonUtils.toJson(j);
            }
            if (StringUtils.isNotBlank(param)) {
                if (XssAndSqlHttpServletRequestWrapper.checkXSSAndSql(param)) {
                    return true;
                }
            }
        }
        return false;
    }


}
