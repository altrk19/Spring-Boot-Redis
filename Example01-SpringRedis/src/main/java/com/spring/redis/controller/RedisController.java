package com.spring.redis.controller;

import com.spring.redis.exception.ConfigException;
import com.spring.redis.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
public class RedisController {
    private static final Logger logger = LoggerFactory.getLogger(RedisController.class);

    private ConfigService configService;

    public RedisController(ConfigService configService) {
        this.configService = configService;
    }

    @GetMapping("/setEntry")
    public String setEntry() throws ConfigException {
        logger.debug("added value1");
        configService.setEntry("entry1", "value1");
        return "added value";
    }

    @GetMapping("/getEntry")
    public String getEntry() throws ConfigException {
        logger.debug("retrieved value1");
        return configService.getEntry("entry1");
    }

    @GetMapping("/deleteEntry")
    public String deleteEntry() throws ConfigException {
        logger.debug("removed value1");
        configService.removeEntry("entry1");
        return "removed value";
    }

    @GetMapping("/setAttribute1")
    public String setAttribute1() throws ConfigException {
        logger.debug("added attribute");
        configService.setAttribute("attribute1", "key1", "value1");
        configService.setAttribute("attribute1", "key2", "value2");
        configService.setAttribute("attribute1", "key3", "value3");
        return "added attribute1";
    }

    @GetMapping("/setAttribute2")
    public String setAttribute2() throws ConfigException {
        logger.debug("added attribute");
        Map<String, String> attributeMap = new HashMap<>();
        attributeMap.put("key11", "value11");
        attributeMap.put("key12", "value12");
        attributeMap.put("key13", "value13");
        configService.setAttributes("attribute2", attributeMap);
        return "added attribute2";
    }

    @GetMapping("/getAttribute1")
    public String getAttribute1() throws ConfigException {
        logger.debug("retrieved attribute");
        return configService.getAttribute("attribute1", "key1");      //output:value1
    }

    @GetMapping("/getAttribute1All")
    public Map<String, String> getAttribute1All() throws ConfigException {
        logger.debug("retrieved attribute");
        return configService.getAttributes("attribute1");     //output:{"key1":"value1","key2":"value2","key3:"value3"}
    }

    @GetMapping("/getAttribute2All")
    public Map<String, String> getAttribute2All() throws ConfigException {
        logger.debug("retrieved attribute");
        return configService.getAttributes("attribute2");     //output:{"key13":"value13","key12":"value12","key11":"value11"}
    }

    @GetMapping("/getAttribute2Partial")
    public Map<String, String> getAttribute2Partial() throws ConfigException {
        logger.debug("retrieved attribute");
        List<String> attributes = new ArrayList<>();
        attributes.add("key11");
        attributes.add("key13");
        return configService.getAttributes("attribute2", attributes);  //output:{"key11":"value11","key13":"value13"}
    }

    @GetMapping("/deleteAttribute1")
    public String deleteAttribute() throws ConfigException {
        logger.debug("removed attribute");
        configService.removeAttributes("attribute1","key3");
        return "removed attribute key3";
    }

    @GetMapping("/getKeys")
    public Set<String> getKeys() throws ConfigException {
        logger.debug("retrieved keys");
        return configService.getKeys("attr");  //it requires pattern
    }

}
