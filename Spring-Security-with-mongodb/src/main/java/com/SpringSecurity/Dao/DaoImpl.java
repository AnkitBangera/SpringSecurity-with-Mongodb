package com.SpringSecurity.Dao;

import org.jongo.Jongo;
import org.springframework.stereotype.Repository;

import com.SpringSecurity.Pojo.UserDetail;
import com.SpringSecurity.Util.MongoConnectionUtil;
@Repository
public class DaoImpl implements Dao{

	public UserDetail getUser(String userName) {
		UserDetail userDetail=new Jongo(MongoConnectionUtil.getDB()).getCollection("userDetails").findOne("{firstName:#}",userName).as(UserDetail.class);
		return userDetail;
	}

}
