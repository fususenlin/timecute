package com.matrixloop.timecute.app.db.redis;



import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

import com.matrixloop.timecute.utils.log.*;

public enum JedisDBIns {
	INSTANCE;
	
	private volatile ConcurrentHashMap<RedisServer, JedisPool> jedisPoolContainer ;
	private final TreeMap<String, AtomicInteger> jedisClientCounterMap ;
	
	JedisDBIns() {
		this.jedisPoolContainer = new ConcurrentHashMap<RedisServer, JedisPool>() ;
		this.jedisClientCounterMap = new TreeMap<String, AtomicInteger>() ;
	}
	
	public synchronized void destory() {
		RedisServer[] ss = RedisServer.values() ;
		for(RedisServer rs : ss){
			JedisPool p = jedisPoolContainer.get(rs) ;
			if(p != null){
				p.destroy() ;
			}
		}
		// 清空ConcurrentHashMap中的Redis Server 连接池
		jedisPoolContainer.clear() ;
	}
	
	public synchronized void init(){
		RedisServer[] ss = RedisServer.values() ;
		for(RedisServer rs : ss){
			final JedisPool p = new JedisPool(rs.getConfig(), rs.getHost(), rs.getPort(), rs.getTimeout());
			jedisPoolContainer.put(rs, p) ;
		}
	}
	
	public Jedis getJedis(RedisServer server){
		Jedis j = null ;
		try {
			JedisPool pool = jedisPoolContainer.get(server) ;
			
			if(pool == null){
				synchronized (this) {
					pool = new JedisPool(server.getConfig(), server.getHost(), server.getPort(), server.getTimeout());
					if(pool != null){
						j = pool.getResource() ;
						jedisPoolContainer.putIfAbsent(server , pool) ;
					}
				}
			} else {
				j = pool.getResource() ;
			}
			
			//统计状态：在成功的获取到一个Jedis连接的时候counter+1
			if(j != null){
				AtomicInteger counter = this.jedisClientCounterMap.get(server.name()) ;
				if(counter == null){
					synchronized (server) {
						counter = new AtomicInteger(1) ;
						this.jedisClientCounterMap.put(server.name(), counter) ;
					}
				} else {
					this.jedisClientCounterMap.get(server.name()).incrementAndGet() ;
				}
			}
			return j ;
		} catch (JedisException e) {
			this.releaseBrokenJedis(server, j) ;
			SLogger.warn("[Release Broken]Redis Connect Failed for " + server.getHost() + ":" + server.getPort() + ",timeout=" + server.getTimeout(), e); 
			return null ;
		} catch (Exception e) {
			SLogger.warn("Redis Connect Failed for " + server.getHost() + ":" + server.getPort() + ",timeout=" + server.getTimeout(), e); 
			return null;
		}
	}
	
	public void release(RedisServer server,Jedis jedis) {
		JedisPool pool = jedisPoolContainer.get(server) ;
		if(pool != null && jedis != null){
			pool.returnResource(jedis) ;
			
			//统计状态：在释放完成一个Jedis连接之后，counter.release+1
			AtomicInteger counter = this.jedisClientCounterMap.get(server.name() + ".release") ;
			if(counter == null){
				synchronized (server) {
					counter = new AtomicInteger(1) ;
					this.jedisClientCounterMap.put(server.name() + ".release", counter) ;
				}
			} else {
				this.jedisClientCounterMap.get(server.name() + ".release").incrementAndGet() ;
			}
		} else {
			SLogger.warn("[Jedis Release], pool=" + pool + ";jedis=" + jedis) ;
		}
	}
	
	public void releaseBrokenJedis(RedisServer server,Jedis jedis) {
		JedisPool pool = jedisPoolContainer.get(server) ;
		if(pool != null && jedis != null){
			pool.returnBrokenResource(jedis) ;
			
			//统计状态：在释放完成一个Brokend Jedis连接之后，counter.broken+1
			AtomicInteger counter = this.jedisClientCounterMap.get(server.name() + ".broken") ;
			if(counter == null){
				synchronized (server) {
					counter = new AtomicInteger(1) ;
					this.jedisClientCounterMap.put(server.name() + ".broken", counter) ;
				}
			} else {
				this.jedisClientCounterMap.get(server.name() + ".broken").incrementAndGet() ;
			}
		} else {
			SLogger.warn("[Jedis Release Broken], pool=" + pool + ";jedis=" + jedis) ;
		}
		SLogger.warn("[Jedis Release Broken]Broken Jedis released : " + jedis) ;
	}
	
	public Map<String, AtomicInteger> getRedisStatus(){
		return this.jedisClientCounterMap ;
	}
}
