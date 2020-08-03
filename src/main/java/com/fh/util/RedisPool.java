package com.fh.util;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisPool {
    //静态方法
    private static JedisPool jedisPool =null;
    //私有化静态对象
    private static void redisPool(){
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();

         jedisPool = new JedisPool(jedisPoolConfig,"127.0.0.1",6379);
    }
  //静态块 只调用一次
   static {
        redisPool();
   }
   //公共化
   public static Jedis getJedisPool(){
        return jedisPool.getResource();
   }

}
