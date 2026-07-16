package net.engineeringdigest.journalApp.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
//service to interact with redis
public class RedisService {
    @Autowired
    private RedisTemplate redisTemplate;
    //get method
    public <T> T get(String key, Class<T> entityClass){
        try {
            Object o = redisTemplate.opsForValue().get(key);
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(o.toString(),entityClass);
        } catch (JsonProcessingException e) {
            log.error("Exception",e);
            return null;
        }
    }

    //set method
    public void set(String key, Object o, Long ttl){
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonValues = objectMapper.writeValueAsString(o);
            redisTemplate.opsForValue().set(key,o.toString() ,ttl, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("Exception",e);
        }
    }
}
