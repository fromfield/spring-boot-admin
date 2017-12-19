/*
 * ............................................. 
 * 
 * 				    _ooOoo_ 
 * 		  	       o8888888o 
 * 	  	  	       88" . "88 
 *                 (| -_- |) 
 *                  O\ = /O 
 *              ____/`---*\____ 
 *               . * \\| |// `. 
 *             / \\||| : |||// \ 
 *           / _||||| -:- |||||- \ 
 *             | | \\\ - /// | | 
 *            | \_| **\---/** | | 
 *           \  .-\__ `-` ___/-. / 
 *            ___`. .* /--.--\ `. . __ 
 *        ."" *< `.___\_<|>_/___.* >*"". 
 *      | | : `- \`.;`\ _ /`;.`/ - ` : | | 
 *         \ \ `-. \_ __\ /__ _/ .-` / / 
 *======`-.____`-.___\_____/___.-`____.-*====== 
 * 
 * ............................................. 
 *              佛祖保佑 永无BUG 
 *
 * 佛曰: 
 * 写字楼里写字间，写字间里程序员； 
 * 程序人员写程序，又拿程序换酒钱。 
 * 酒醒只在网上坐，酒醉还来网下眠； 
 * 酒醉酒醒日复日，网上网下年复年。 
 * 但愿老死电脑间，不愿鞠躬老板前； 
 * 奔驰宝马贵者趣，公交自行程序员。 
 * 别人笑我忒疯癫，我笑自己命太贱； 
 * 不见满街漂亮妹，哪个归得程序员？
 *
 * 北纬30.√  154518484@qq.com
 */
package com.github.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class User implements Serializable {

	//alias
	public static final String TABLE_ALIAS = "User";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_USER_NAME = "用户名";
	public static final String ALIAS_SALT = "盐";
	public static final String ALIAS_PASSWORD = "密码";
	public static final String ALIAS_NICK_NAME = "昵称";
	public static final String ALIAS_REAL_NAME = "真实姓名";
	public static final String ALIAS_PHONE = "手机号码";
	public static final String ALIAS_LAST_LOGIN_TIME = "最后登录时间";
	public static final String ALIAS_LOGIN_TIMES = "登录次数";
	public static final String ALIAS_STATUS = "状态 1可用，0禁用";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_UPDATE_TIME = "修改时间";
	private static final long serialVersionUID = 7692382079992482707L;

	//columns START
	/** id   db_column: id */ 	
	private Integer id;
	private Integer fid;
	private Integer uid;
	/** 用户名   db_column: user_name */
	private String userName;
	/** 盐   db_column: salt */ 	
	private String salt;
	/** 密码   db_column: password */ 	
	private String password;
	/** 昵称   db_column: nick_name */ 	
	private String nickName;
	/** 真实姓名   db_column: real_name */ 	
	private String realName;
	/** 手机号码   db_column: phone */ 	
	private String phone;
	/** 最后登录时间   db_column: last_login_time */ 	
	private java.util.Date lastLoginTime;
	/** 登录次数   db_column: login_times */ 	
	private Integer loginTimes;
	/** 状态 1可用，0禁用   db_column: status */ 	
	private Integer status;
	/** 创建时间   db_column: create_time */ 	
	private java.util.Date createTime;
	/** 修改时间   db_column: update_time */ 	
	private java.util.Date updateTime;
	//columns END

	public User(){
	}

	public User(
		Integer id
	){
		this.id = id;
	}

	public void setId(Integer value) {
		this.id = value;
	}
	public Integer getId() {
		return this.id;
	}
	public void setUserName(String value) {
		this.userName = value;
	}
	public String getUserName() {
		return this.userName;
	}
	public void setSalt(String value) {
		this.salt = value;
	}
	public String getSalt() {
		return this.salt;
	}
	public void setPassword(String value) {
		this.password = value;
	}
	public String getPassword() {
		return this.password;
	}
	public void setNickName(String value) {
		this.nickName = value;
	}
	public String getNickName() {
		return this.nickName;
	}
	public void setRealName(String value) {
		this.realName = value;
	}
	public String getRealName() {
		return this.realName;
	}
	public void setPhone(String value) {
		this.phone = value;
	}
	public String getPhone() {
		return this.phone;
	}
	public void setLastLoginTime(java.util.Date value) {
		this.lastLoginTime = value;
	}
	public java.util.Date getLastLoginTime() {
		return this.lastLoginTime;
	}
	public void setLoginTimes(Integer value) {
		this.loginTimes = value;
	}
	public Integer getLoginTimes() {
		return this.loginTimes;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	public Integer getStatus() {
		return this.status;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public Integer getFid() {
		return fid;
	}

	public void setFid(Integer fid) {
		this.fid = fid;
	}

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof User == false) return false;
		if(this == obj) return true;
		User other = (User)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

