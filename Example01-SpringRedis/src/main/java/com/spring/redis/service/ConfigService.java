package com.spring.redis.service;

import com.spring.redis.exception.ConfigException;
import redis.clients.jedis.JedisPubSub;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface ConfigService {
    String getEntry(String var1) throws ConfigException;

    void setEntry(String var1, String var2) throws ConfigException;

    void removeEntry(String var1) throws ConfigException;

    String getAttribute(String var1, String var2) throws ConfigException;

    Map<String, String> getAttributes(String var1, List<String> var2) throws ConfigException;

    Map<String, String> getAttributes(String var1) throws ConfigException;

    void setAttribute(String var1, String var2, String var3) throws ConfigException;

    void setAttributes(String var1, Map<String, String> var2) throws ConfigException;

    void removeAttributes(String var1, String... var2) throws ConfigException;

    Set<String> getKeys(String var1) throws ConfigException;

    void psubscribe(JedisPubSub var1, String... var2) throws ConfigException;
}
