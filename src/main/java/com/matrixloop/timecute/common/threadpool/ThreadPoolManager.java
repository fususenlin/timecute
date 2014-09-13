package com.matrixloop.timecute.common.threadpool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import com.matrixloop.timecute.utils.log.SLogger;

public enum ThreadPoolManager {
	
	INSTANCE;
	
	public static final int THREAD_POOL_SIZE = 10;
	public static final int DAEMON_THREAD_POOL_SIZE = 4;
	
	public volatile ScheduledExecutorService daemonScheduledPool = null;
	public volatile ScheduledExecutorService scheduledPool = null;
	
	
	ThreadPoolManager() {
		
		daemonScheduledPool = Executors.newScheduledThreadPool(DAEMON_THREAD_POOL_SIZE, new DaemonThreadFactory("deamonSchuledPool"));
		scheduledPool = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
	}
	
	public void destory(){
		if(null != daemonScheduledPool){
			daemonScheduledPool.shutdown();
			daemonScheduledPool.shutdownNow();
			daemonScheduledPool = null;
			SLogger.info("ThreadPoolManager - daemonScheduledPool has been shutdown.");
		}
		if(null != scheduledPool){
			scheduledPool.shutdown();
			scheduledPool.shutdownNow();
			scheduledPool = null;
			SLogger.info("ThreadPoolManager - scheduledPool has been shutdown.");
		}
	}
}
