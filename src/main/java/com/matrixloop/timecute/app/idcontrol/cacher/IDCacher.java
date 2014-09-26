package com.matrixloop.timecute.app.idcontrol.cacher;

import org.msgpack.MessagePack;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;
import redis.clients.jedis.exceptions.JedisException;
import com.matrixloop.timecute.app.db.redis.RedisServer;
import com.matrixloop.timecute.app.db.redis.RedisTask;
import com.matrixloop.timecute.app.db.redis.RedisTaskExecutor;
import com.matrixloop.timecute.app.idcontrol.IDType;
import com.matrixloop.timecute.utils.log.SLogger;

public enum IDCacher {
	
	INSTANCE;
	
	private static String RedisKey = "TimeCute.ID";
	protected static final MessagePack msgPack = new MessagePack();
	
	
	public boolean exists(final IDType type) {
		RedisTask<Boolean> task = new RedisTask<Boolean>() {

			public Boolean task(Jedis jedis) throws JedisConnectionException {
				return jedis.hexists(RedisKey, type.name());
			}
		};
		return new RedisTaskExecutor<Boolean>().execute(task, RedisServer.myredisServer);
	}
	
	public boolean put(final long id, final IDType type){
		RedisTask<Boolean> task = new RedisTask<Boolean>() {

			public Boolean task(Jedis jedis) throws JedisException {
				try {
					jedis.hset(RedisKey, type.name(), Long.toString(id));
					return true;
				} catch (Exception e) {
					SLogger.error(e, e);
					return false;
				}
			}	
		};
		return new RedisTaskExecutor<Boolean>().execute(task, RedisServer.myredisServer);
	}
	
	public long incrAndGet(final IDType type){
		RedisTask<Long> task = new RedisTask<Long>() {

			public Long task(Jedis jedis) throws JedisException {
				return jedis.hincrBy(RedisKey, type.name(), 1L);
			}	
		};
		return new RedisTaskExecutor<Long>().execute(task, RedisServer.myredisServer);
	}
	
}
