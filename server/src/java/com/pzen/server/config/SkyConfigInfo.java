package com.pzen.server.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @auth cheng
 * @time 2024/11/8
 * @desc sky配置
 */
@Component
@ConfigurationProperties(prefix = "sky")
public class SkyConfigInfo {

    private Filter filter;

    public static class Filter {
        private List<String> openUri;

        public List<String> getOpenUri() {
            return openUri;
        }

        public void setOpenUri(List<String> openUri) {
            this.openUri = openUri;
        }
    }

    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }
}
