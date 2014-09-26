package com.matrixloop.timecute.business.user.bean;

import org.json.JSONObject;

public class UserInfoBean {
	
	private long uid;
	private String nickname;
	private String desc;
	private String avatar;
	private int sex;
	private String email;
	private String birth;
	private String phone;
	private String address;
	private String background;
	private String extraInfo;
	
	public UserInfoBean(){
		
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}

	public String getExtraInfo() {
		return extraInfo;
	}

	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
	
	public JSONObject toJson(){
		JSONObject jo = new JSONObject();
		jo.put("uid", this.getUid());
		jo.put("nickname", this.getNickname());
		jo.put("desc", this.getDesc());
		jo.put("avatar", this.getAvatar());
		jo.put("email",this.getEmail());
		return jo;
	}
}
