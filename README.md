timecute
========

timecute


##说明：
这是一个maven web app  
1. mysql数据库需要有一个 `timecute.demo`表，列名：id（bigint(20),AI）,name(varchar(45))  
2. 数据库配置在 META-INF/context.xml ，src/main/resource/system*_config.properties ， 注意数据库的名字和用户名密码  
3. 如果报错: com.java.jdbc.Driver 之类的，需要将maven依赖包中 mysql-connector.jar 复制一份到 tomcat/lib 下面。