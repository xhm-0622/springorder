package com.fh.util;


import redis.clients.jedis.Jedis;

import java.util.List;

public class RedisUtil {
    /**
     * 设置
     * @param key
     * @param value
     */
    public static void set(String key,String value){
        Jedis jedisPool=null;
        try {
             jedisPool = RedisPool.getJedisPool();
            jedisPool.set(key,value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedisPool!=null){
               jedisPool.close();
            }
        }
    }

    /**
     * 获取
     * @param key
     * @return
     */
    public static  String get(String key){
        Jedis pool=null;
        String s=null;
        try {
            pool = RedisPool.getJedisPool();
             s = pool.get(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pool!=null){
                pool.close();
            }
        }
        return s;
    }

    /**
     * 删除
     * @param key
     */
    public static void del(String key){
        Jedis pool =null;
        try {
            pool = RedisPool.getJedisPool();
            pool.del(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pool!=null){
                pool.close();
            }
        }
    }

    public static void hdel(String key,String field){
        Jedis pool =null;
        try {
            pool = RedisPool.getJedisPool();
            pool.hdel(key,field);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pool!=null){
                pool.close();
            }
        }
    }

    /**
     * 给string类型
     * 每个key 设置过期时间
     * @param key
     * @param value
     * @param time
     */
    public static void  setEx(String key,String value,Integer time){
        Jedis pool =null;
        try {
             pool = RedisPool.getJedisPool();
             pool.setex(key,time,value);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (pool!=null){
                pool.close();
            }
        }
    }

    /**
     * 设置 hash中的set
     * @param key
     * @param value
     * @param field
     */
    public static void hset(String key,String value,String field){
        Jedis jedis =null;
        try {
            jedis = RedisPool.getJedisPool();
            jedis.hset(key,value,field);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
    }

    /**
     * 获取 hash中的get
     * @param key
     * @param field
     * @return
     */
    public static String hget(String key,String field){
        Jedis jedis =null;
        String s =null;
        try {
            jedis = RedisPool.getJedisPool();
            s = jedis.hget(key,field);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return s;
    }

     public static List<String> hget(String key){
         Jedis jedis =null;
         List<String> hvals=null;
         try {
             jedis = RedisPool.getJedisPool();
              hvals = jedis.hvals(key);
         } catch (Exception e) {
             e.printStackTrace();
         }finally {
             if(jedis!=null){
                 jedis.close();
             }
         }
         return hvals;
     }

    /**
     * 判断hash中key是否存在
     * @param key
     * @param id
     * @return
     */
    public static Boolean exists(String key,String id){
        Jedis jedis =null;
        Boolean s =null;
        try {
            jedis = RedisPool.getJedisPool();
            s = jedis.hexists(key,id);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return s;
    }

    /**
     * 判断key是否存在
     * @param key
     * @return
     */
    public static Boolean exists1(String key){
        Jedis jedis =null;
        Boolean exists =null;
        try {
            jedis = RedisPool.getJedisPool();
            exists = jedis.exists(key);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(jedis!=null){
                jedis.close();
            }
        }
        return exists;
    }

}
