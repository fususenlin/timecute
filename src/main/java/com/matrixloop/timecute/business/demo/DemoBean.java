package com.matrixloop.timecute.business.demo;



import org.json.JSONObject;
import org.msgpack.annotation.Message;

@Message
public class DemoBean {
	
	private long id;
	private String name;
	
	public DemoBean(){
		
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public JSONObject toJson(){
		JSONObject jo = new JSONObject();
		jo.put("id", this.getId());
		jo.put("name", this.getName());
		return jo;
	}
}
