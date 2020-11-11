package com.hkycu.goodsseckill.redis;

public class BasePrefix implements KeyPrefix {
    private String prefix;

    public BasePrefix(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className + ":" + prefix;// className:mp
    }
}