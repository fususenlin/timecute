package com.matrixloop.timecute.common.threadpool;

import java.util.concurrent.ThreadFactory;

public class DaemonThreadFactory implements ThreadFactory{
	
	private String threadFactoryName;
	
	public DaemonThreadFactory(String threadFactoryName){
		this.threadFactoryName = threadFactoryName;
	}
	
	public String getThreadFactoryName() {
		return threadFactoryName;
	}

	public Thread newThread(Runnable r) {
		Thread t = new Thread(r);
		t.setDaemon(true);
		t.setName(this.getThreadFactoryName()+"-"+t.getId());
		return t;
	}
	
	@Override
	public String toString() {
		return this.getClass().getName() + ":" + this.getThreadFactoryName() + "-" ;
	}
	
}
