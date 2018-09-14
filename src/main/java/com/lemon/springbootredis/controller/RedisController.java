package com.lemon.springbootredis.controller;

import com.alibaba.fastjson.JSONObject;
import com.lemon.springbootredis.model.User;
import com.lemon.springbootredis.service.RedisService;
import com.lemon.springbootredis.util.RedisCacheEnum;
import org.hibernate.validator.constraints.EAN;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * rediscontroller
 *
 * @author lumingming
 * @version 1.0
 * @create 2018-09-03 19:45
 **/
@RestController
@RequestMapping("/redis")
public class RedisController {
    @Autowired
    private RedisService redisService;

    /**
    *  将字符串存缓存
    * @author lumingming
    * @date 2018/9/4
    * @param
    * @return
    */
    @RequestMapping("/set")
    public Boolean set(String key ,String value){
        return redisService.set(key,value,null);
    }

    /**
    *  将对象转换成json存入缓存
    * @author lumingming
    * @date 2018/9/4
    * @param
    * @return
    */
    @RequestMapping("/setObject")
    public Boolean setObject(){
        User user = new User();
        user.setId(1L);
        user.setName("王阳");
        user.setAge(24L);
        user.setSex(1);
        user.setPhone(18366201192L);
        String key = String.format(RedisCacheEnum.REDISCACHE_KEY_USER.getKey(),user.getId());
        return redisService.set(key, JSONObject.toJSONString(user),null);
    }

    /**
     *  将对象转换成json存入缓存
     * @author lumingming
     * @date 2018/9/4
     * @param
     * @return
     */
    @RequestMapping("/setList")
    public Boolean setList(){
        User user = new User();
        user.setName("王阳");
        user.setAge(24L);
        user.setSex(1);
        user.setPhone(18366201192L);
        List<User> list = new ArrayList();
        list.add(user);
        list.add(user);
        return redisService.set("userList", JSONObject.toJSONString(list),null);
    }

    /**
    *  计数器
    * @author lumingming
    * @date 2018/9/5
    * @param
    * @return
    */
    @RequestMapping("/count")
    public void count(String key){
        redisService.incr(key);
    }



    /**
    *  根据key
    * @author lumingming
    * @date 2018/9/4
    * @param
    * @return
    */
    @RequestMapping("/get")
    public String get(String key){
        return redisService.get(key);
    }



}
