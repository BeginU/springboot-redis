package com.lemon.springbootredis.util;

public enum RedisCacheEnum {

    REDISCACHE_KEY_USER("USER_%s_CACHE","缓存用户基本信息key{id}");
    private String key;
    private String desc;

    RedisCacheEnum(String key, String desc) {
        this.key = key;
        this.desc = desc;
    }

    public String getKey() {
        return key;
    }

    public String getDesc() {
        return desc;
    }
}