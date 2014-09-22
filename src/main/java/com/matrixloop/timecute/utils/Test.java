package com.matrixloop.timecute.utils;

import java.util.ArrayList;
import java.util.List;

import com.matrixloop.timecute.utils.email.Email;

public class Test {
	
	public static void main(String [] args){
		List<String> to = new ArrayList<String>();
		to.add("287300953@qq.com");
		to.add("947836419@qq.com");
		to.add("532175714@qq.com");
		
		List<String> cc = new ArrayList<String>();
		cc.add("493421734@qq.com");
		if("SUCCESS".equals(Email.send(to, cc, "欢迎来到时光留书网", "这是一封测试邮件", null, true))){
			System.out.println("send email success");
		}
	}
	
}
