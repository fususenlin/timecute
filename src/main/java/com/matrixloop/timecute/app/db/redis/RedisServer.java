package com.matrixloop.timecute.app.db.redis;



import redis.clients.jedis.JedisPoolConfig;

public enum RedisServer {
	
	myredisServer("xxxx" , 6379 , 3000);

    
    private String host ;
    private int port ;
    private int timeout ;
    private JedisPoolConfig config ;
    
    RedisServer(String hostName , int port , int timeout){
        this.host=hostName;
        this.port = port ;
        this.timeout = timeout ;
        
        this.config = new JedisPoolConfig();
        config.setMaxTotal(256);
        config.setMaxIdle(128);
        config.setMaxWaitMillis(1000);
        config.setTestOnBorrow(true);
        config.setTestWhileIdle(true);
        config.setTestOnReturn(true);
        config.setMinEvictableIdleTimeMillis(30000);
        config.setTimeBetweenEvictionRunsMillis(30000);
        config.setNumTestsPerEvictionRun(-1);
    }

    public String getHost() {
        return this.host ;
    }

    public int getPort() {
        return this.port ;
    }

    public int getTimeout() {
        return this.timeout ;
    }

    public JedisPoolConfig getConfig() {
        return this.config ;
    }
}
