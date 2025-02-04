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
    private Redis redis;
    private SmCrypt smCrypt;

    public static class Filter {
        private List<String> openUri;

        public List<String> getOpenUri() {
            return openUri;
        }

        public void setOpenUri(List<String> openUri) {
            this.openUri = openUri;
        }
    }
    public static class Redis {
        private String host;
        private int port;
        private String password;
        private int database0;
        private int database1;
        private int database2;
        private int database3;
        private int database4;
        private int database5;
        private int database6;
        private int database7;
        private int database8;
        private int database9;
        private int timeout;
        private Pool pool;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getDatabase0() {
            return database0;
        }

        public void setDatabase0(int database0) {
            this.database0 = database0;
        }

        public int getDatabase1() {
            return database1;
        }

        public void setDatabase1(int database1) {
            this.database1 = database1;
        }

        public int getDatabase2() {
            return database2;
        }

        public void setDatabase2(int database2) {
            this.database2 = database2;
        }

        public int getDatabase3() {
            return database3;
        }

        public void setDatabase3(int database3) {
            this.database3 = database3;
        }

        public int getDatabase4() {
            return database4;
        }

        public void setDatabase4(int database4) {
            this.database4 = database4;
        }

        public int getDatabase5() {
            return database5;
        }

        public void setDatabase5(int database5) {
            this.database5 = database5;
        }

        public int getDatabase6() {
            return database6;
        }

        public void setDatabase6(int database6) {
            this.database6 = database6;
        }

        public int getDatabase7() {
            return database7;
        }

        public void setDatabase7(int database7) {
            this.database7 = database7;
        }

        public int getDatabase8() {
            return database8;
        }

        public void setDatabase8(int database8) {
            this.database8 = database8;
        }

        public int getDatabase9() {
            return database9;
        }

        public void setDatabase9(int database9) {
            this.database9 = database9;
        }

        public int getTimeout() {
            return timeout;
        }

        public void setTimeout(int timeout) {
            this.timeout = timeout;
        }

        public Pool getPool() {
            return pool;
        }

        public void setPool(Pool pool) {
            this.pool = pool;
        }

        public static class Pool {
            private int maxActive;
            private int maxWait;
            private int maxIdle;
            private int minIdle;

            // Getters and Setters

            public int getMaxActive() {
                return maxActive;
            }

            public void setMaxActive(int maxActive) {
                this.maxActive = maxActive;
            }

            public int getMaxWait() {
                return maxWait;
            }

            public void setMaxWait(int maxWait) {
                this.maxWait = maxWait;
            }

            public int getMaxIdle() {
                return maxIdle;
            }

            public void setMaxIdle(int maxIdle) {
                this.maxIdle = maxIdle;
            }

            public int getMinIdle() {
                return minIdle;
            }

            public void setMinIdle(int minIdle) {
                this.minIdle = minIdle;
            }
        }
    }

    public static class SmCrypt{
        private String publicKey;
        private String privateKey;

        public String getPublicKey() {
            return publicKey;
        }

        public void setPublicKey(String publicKey) {
            this.publicKey = publicKey;
        }

        public String getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(String privateKey) {
            this.privateKey = privateKey;
        }
    }
    public Filter getFilter() {
        return filter;
    }

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public Redis getRedis() {
        return redis;
    }

    public void setRedis(Redis redis) {
        this.redis = redis;
    }

    public SmCrypt getSmCrypt() {
        return smCrypt;
    }

    public void setSmCrypt(SmCrypt smCrypt) {
        this.smCrypt = smCrypt;
    }
}
