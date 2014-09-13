package com.matrixloop.timecute.app.db.redis;



import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;
import com.matrixloop.timecute.utils.log.*;

public class RedisTaskExecutor<T> {
	
public RedisTaskExecutor(){}
	
	public T execute(RedisTask<T> executor , RedisServer redisServer){
		T rst = null ;
		Jedis j = null ;
		try{
			j = JedisDBIns.INSTANCE.getJedis(redisServer) ;
			if(j != null){
				rst = executor.task(j) ;
				JedisDBIns.INSTANCE.release(redisServer,j) ;
			} else {
				SLogger.warn("Stop process as Redis Connect Failed for " + redisServer.getHost() + ":" + redisServer.getPort() + ",timeout=" + redisServer.getTimeout()); 
			}
		}catch(JedisException e){
			JedisDBIns.INSTANCE.releaseBrokenJedis(redisServer,j) ;
			SLogger.error(e, e) ;
		}catch(Exception e){
			JedisDBIns.INSTANCE.release(redisServer,j) ;
			SLogger.error(e, e) ;
		}
		
		return rst ;
	}
}
