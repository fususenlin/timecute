package com.matrixloop.timecute.business.demo;



import org.msgpack.MessagePack;





import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.util.SafeEncoder;

import com.matrixloop.timecute.app.db.redis.RedisServer;
import com.matrixloop.timecute.app.db.redis.RedisTask;
import com.matrixloop.timecute.app.db.redis.RedisTaskExecutor;
import com.matrixloop.timecute.utils.log.*;

public enum DemoCache {
	
	INSTANCE;
	
	private static final byte[] HashKey = SafeEncoder.encode("MyCache.Demo");
	protected static final MessagePack msgPack = new MessagePack();
	
	public boolean put(final long id, final DemoBean bean){
		RedisTask<Boolean> task = new RedisTask<Boolean>() {

			public Boolean task(Jedis jedis) throws JedisException {
				try {
					jedis.hset(HashKey, SafeEncoder.encode(Long.toString(id)), msgPack.write(bean));
					return true;
				} catch (Exception e) {
					SLogger.error(e, e);
					return false;
				}
			}	
		};
		return new RedisTaskExecutor<Boolean>().execute(task, RedisServer.myredisServer);
	}
	
	public DemoBean get(final long id){
		RedisTask<DemoBean> task = new RedisTask<DemoBean>() {

			public DemoBean task(Jedis jedis) throws JedisException {
				byte[] idByte = jedis.hget(HashKey, SafeEncoder.encode(Long.toString(id)));
				DemoBean demo = null;
				if (idByte != null) {
					try {
						demo = msgPack.read(idByte, DemoBean.class);
					} catch (Exception e) {
						jedis.hdel(HashKey, SafeEncoder.encode(Long.toString(id)));
					}
				}
				return demo;
			}	
		};
		return new RedisTaskExecutor<DemoBean>().execute(task, RedisServer.myredisServer);
	}
}
