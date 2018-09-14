package com.lemon.springbootredis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis
 *
 * @author lumingming
 * @create 2018-09-03 17:25
 **/
@Service
public class RedisService {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public Boolean setString(String key, String value){
        return this.set(key,value,null);
    }

    public Boolean setList(String key, List<String> stringList){
        return this.set(key,stringList,null);
    }


    // 设置key缓存
    public Boolean set(String key, Object object,Long seconds){
        boolean flag = false;
        if(!StringUtils.isEmpty(key) && object != null){
            // 如果是string类型
            if (object instanceof String){
                String strValue = (String) object;
                stringRedisTemplate.opsForValue().set(key,strValue);
                if(seconds != null){
                    stringRedisTemplate.expire(key,seconds, TimeUnit.SECONDS);
                }
                flag = true;
                return flag;
            }
            // 如果是list类型
            if(object instanceof List){
                flag = true;
                List<String> listValue = (List<String>) object;
                for(String strValue:listValue){
                    stringRedisTemplate.opsForList().leftPush(key,strValue);
                }
                if(seconds != null){
                    stringRedisTemplate.expire(key,seconds, TimeUnit.SECONDS);
                }
                return flag;
            }
            // 如果是set类型
            if(object instanceof Set || object instanceof SortedSet){
                flag = true;
                Set<String> setValue = (Set<String>) object;
                for (String strValue: setValue){
                    stringRedisTemplate.opsForSet().add(key,strValue);
                }
                if(seconds != null){
                    stringRedisTemplate.expire(key,seconds, TimeUnit.SECONDS);
                }
                return flag;
            }
            // 如果是hash类型
            if(object instanceof HashMap){
                flag = true;
                HashMap<String,String> hashMapValue = (HashMap<String, String>) object;
                stringRedisTemplate.opsForHash().putAll(key,hashMapValue);
                if(seconds != null){
                    stringRedisTemplate.expire(key,seconds, TimeUnit.SECONDS);
                }
                return flag;
            }
        }
        return flag;
    }

    // 取缓存
    public String get(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    // 判断key是否存在
    public Boolean exist(String key){
        boolean flag = false;
        if(this.get(key) != null){
            flag = true;
        }
        return flag;
    }

    public void incr(String key){
        stringRedisTemplate.opsForValue().increment(key,1L);
    }

 }
