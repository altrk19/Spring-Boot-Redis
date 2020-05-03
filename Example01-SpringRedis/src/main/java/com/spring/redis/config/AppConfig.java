package com.spring.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableCaching
public class AppConfig {
    private static final String CONFIG_CONNECTION_PASSWORD = null;
    private static final Integer CONFIG_CONNECTION_PORT = 6379;
    private static final String CONFIG_CONNECTION_HOST = "172.28.226.97";
    private static final Integer CONFIG_TIMEOUT = 5000;
    private static final Logger log = LoggerFactory.getLogger(AppConfig.class);

    private static JedisPool jedisPool = new JedisPool(new JedisPoolConfig(), CONFIG_CONNECTION_HOST,
            CONFIG_CONNECTION_PORT, CONFIG_TIMEOUT, CONFIG_CONNECTION_PASSWORD);

    @Bean
    public Jedis jedis() {
        log.debug("Get new jedis resource from pool. Current pool stats: Active= {} Idle= {} Waiters= {} ",
                jedisPool.getNumActive(), jedisPool.getNumIdle(), jedisPool.getNumWaiters());
        return jedisPool.getResource();
    }



}