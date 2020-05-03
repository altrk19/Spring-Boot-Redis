package com.spring.redis.service;

import com.spring.redis.exception.ConfigException;
import com.spring.redis.repository.ConfigRepository;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPubSub;

import java.util.*;

@Service
public class ConfigServiceImpl implements ConfigService {
    private final ConfigRepository configRepository;

    public ConfigServiceImpl(ConfigRepository configRepository) {
        this.configRepository = configRepository;
    }

    @Override
    public String getEntry(String key) throws ConfigException {
        this.checkParameter(key);
        return this.configRepository.getValue(key);
    }

    @Override
    public void setEntry(String key, String value) throws ConfigException {
        this.checkParameter(key);
        this.configRepository.setValue(key, value);
    }

    @Override
    public String getAttribute(String key, String attributeKey) throws ConfigException {
        this.checkParameter(key);
        return this.configRepository.getAttribute(key, attributeKey);
    }

    @Override
    public Map<String, String> getAttributes(String key, List<String> attributeKeys) throws ConfigException {
        this.checkParameter(key);
        List<String> attributeValues = this.configRepository.getAttributes(key, attributeKeys);
        return this.convertAttributeMap(attributeKeys, attributeValues);
    }

    @Override
    public void setAttribute(String key, String attributeKey, String value) throws ConfigException {
        this.checkParameter(key);
        this.checkParameter(attributeKey);
        this.configRepository.setAttribute(key, attributeKey, value);
    }

    @Override
    public Map<String, String> getAttributes(String key) throws ConfigException {
        this.checkParameter(key);
        Map<String, String> attributeMap = this.configRepository.getAttributes(key);
        attributeMap = this.convertAttributeMap(attributeMap.keySet(), attributeMap.values());
        return attributeMap;
    }

    @Override
    public void setAttributes(String key, Map<String, String> attributeMap) throws ConfigException {
        this.checkParameter(key);
        if (attributeMap == null) {
            throw new ConfigException("attributeMap must not be null");
        } else {
            this.configRepository.setAttributes(key, attributeMap);
        }
    }

    @Override
    public void removeAttributes(String key, String... attributeKeys) throws ConfigException {
        this.checkParameter(key);
        if (attributeKeys == null) {
            throw new ConfigException("attributeKeys must not be null");
        } else {
            this.configRepository.removeAttributes(key, attributeKeys);
        }
    }

    @Override
    public void removeEntry(String key) throws ConfigException {
        this.checkParameter(key);
        this.configRepository.removeKey(key);
    }

    @Override
    public Set<String> getKeys(String pattern) throws ConfigException {
        return this.configRepository.getKeys(pattern);
    }

    @Override
    public void psubscribe(JedisPubSub subscriber, String... channels) throws ConfigException {
        this.configRepository.psubscribe(subscriber, channels);
    }

    private Map<String, String> convertAttributeMap(Collection<String> attributeKeys, Collection<String> attributeValues) {
        List<String> keys = new ArrayList<>(attributeKeys);
        List<String> values = new ArrayList<>(attributeValues);
        Map<String, String> attributeMap = new HashMap<>();

        for(int i = 0; i < keys.size(); ++i) {
            attributeMap.put(keys.get(i), values.get(i));
        }

        return attributeMap;
    }

    private void checkParameter(String key) throws ConfigException {
        if (key == null) {
            throw new ConfigException("key must not be null");
        }
    }

}

