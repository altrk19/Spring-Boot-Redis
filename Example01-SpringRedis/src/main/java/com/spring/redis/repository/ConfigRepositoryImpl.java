package com.spring.redis.repository;

import com.spring.redis.exception.ConfigException;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class ConfigRepositoryImpl implements ConfigRepository {

    private final Jedis jedis;

    public ConfigRepositoryImpl(Jedis jedis) {
        this.jedis = jedis;
    }

    public String getValue(String key) throws ConfigException {
        String value;
        try {
            value = jedis.get(key);
        } catch (Exception var7) {
            throw new ConfigException(var7);
        } finally {
            this.releaseJedis(jedis);
        }

        return value;
    }

    public void setValue(String key, String value) throws ConfigException {
        try {
            jedis.set(key, value);
        } catch (Exception var8) {
            throw new ConfigException(var8);
        } finally {
            this.releaseJedis(jedis);
        }

    }

    public String getAttribute(String key, String attributeKey) throws ConfigException {
        String attribute;
        try {
            attribute = jedis.hget(key, attributeKey);
        } catch (Exception var8) {
            throw new ConfigException(var8);
        } finally {
            this.releaseJedis(jedis);
        }

        return attribute;
    }

    public List<String> getAttributes(String key, List<String> attributeKeys) throws ConfigException {
        List<String> attributeList;
        try {
            String[] attributeKeyArray = new String[attributeKeys.size()];
            attributeKeys.toArray(attributeKeyArray);
            attributeList = jedis.hmget(key, attributeKeyArray);
        } catch (Exception var9) {
            throw new ConfigException(var9);
        } finally {
            this.releaseJedis(jedis);
        }

        return attributeList;
    }

    public void setAttribute(String key, String attributeKey, String value) throws ConfigException {
        try {
            jedis.hset(key, attributeKey, value);
        } catch (Exception var9) {
            throw new ConfigException(var9);
        } finally {
            this.releaseJedis(jedis);
        }

    }

    public Map<String, String> getAttributes(String key) throws ConfigException {
        Map<String, String> attributeMap;
        try {
            attributeMap = jedis.hgetAll(key);
        } catch (Exception var7) {
            throw new ConfigException(var7);
        } finally {
            this.releaseJedis(jedis);
        }

        return attributeMap;
    }

    public void setAttributes(String key, Map<String, String> attributeMap) throws ConfigException {
        try {
            jedis.hmset(key, attributeMap);
        } catch (Exception var8) {
            throw new ConfigException(var8);
        } finally {
            this.releaseJedis(jedis);
        }

    }

    public void removeAttributes(String key, String... attributeKeys) throws ConfigException {
        try {
            jedis.hdel(key, attributeKeys);
        } catch (Exception var8) {
            throw new ConfigException(var8);
        } finally {
            this.releaseJedis(jedis);
        }

    }

    public void removeKey(String key) throws ConfigException {
        try {
            jedis.del(key);
        } catch (Exception var7) {
            throw new ConfigException(var7);
        } finally {
            this.releaseJedis(jedis);
        }

    }

    public Set<String> getKeys(String pattern) throws ConfigException {
        Set<String> keys;

        try {
            keys = jedis.keys(pattern);
        } catch (Exception var8) {
            throw new ConfigException(var8);
        } finally {
            this.releaseJedis(jedis);
        }

        return keys;
    }

    public void psubscribe(final JedisPubSub subscriber, final String... channels) throws ConfigException {
        try {
            (new Thread(() -> {
                jedis.psubscribe(subscriber, channels);
                ConfigRepositoryImpl.this.releaseJedis(jedis);
            })).start();
        } catch (Exception var4) {
            throw new ConfigException(var4);
        }
    }

    private void releaseJedis(Jedis jedis) {
        if (jedis != null) {
            jedis.close();
        }

    }
}