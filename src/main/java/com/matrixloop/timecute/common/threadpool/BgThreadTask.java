package com.matrixloop.timecute.common.threadpool;

public class BgThreadTask{
	
	//单例
	private BgThreadTask(){}	
	private static class BgThreadTaskHolder {
		static final BgThreadTask task = new BgThreadTask();
	}
	public static BgThreadTask getInstance(){
		return BgThreadTaskHolder.task;
	}
	
	public void startBgTask(){
		//ThreadPoolManager.INSTANCE.daemonScheduledPool.scheduleAtFixedRate(command, initialDelay, period, unit);
	}
}
