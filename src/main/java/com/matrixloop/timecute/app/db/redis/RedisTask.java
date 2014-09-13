package com.matrixloop.timecute.app.db.redis;



import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

public interface RedisTask<T> {
	T task(Jedis jedis) throws JedisException ;
}
