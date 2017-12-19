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

import java.util.List;

public class Permission {
	
	//alias
	public static final String TABLE_ALIAS = "Permission";
	public static final String ALIAS_ID = "id";
	public static final String ALIAS_PARENT_ID = "父节点id";
	public static final String ALIAS_NAME = "权限名称";
	public static final String ALIAS_CODE = "权限代码";
	public static final String ALIAS_ICON = "菜单前面的图标css";
	public static final String ALIAS_URL = "资源链接地址 - 生成菜单时使用";
	public static final String ALIAS_TARGET = "链接打开方式: 0内容区域打开 1新页面打开";
	public static final String ALIAS_MENU = "是否展示位菜单项";
	public static final String ALIAS_STATUS = "是否启用: 0禁用 1启用";
	public static final String ALIAS_CREATE_TIME = "创建时间";
	public static final String ALIAS_DESCRIPTION = "权限描述";
	
	
	//columns START
	/** id   db_column: id */ 	
	private Integer id;
	/** 父节点id   db_column: parent_id */ 	
	private Integer parentId;
	/** 权限名称   db_column: name */ 	
	private String name;
	/** 权限代码   db_column: code */ 	
	private String code;
	/** 菜单前面的图标css   db_column: icon */ 	
	private String icon;
	/** 资源链接地址 - 生成菜单时使用   db_column: url */ 	
	private String url;
	/** 链接打开方式: 0内容区域打开 1新页面打开   db_column: target */ 	
	private Integer target;
	/** 是否展示位菜单项   db_column: menu */ 	
	private Integer menu;
	/** 是否启用: 0禁用 1启用   db_column: status */ 	
	private Integer status;
	/** 创建时间   db_column: create_time */ 	
	private java.util.Date createTime;
	/** 权限描述   db_column: description */ 	
	private String description;
	//columns END

	private List<Permission> permissionList;

	public Permission(){
	}

	public Permission(
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
	public void setParentId(Integer value) {
		this.parentId = value;
	}
	public Integer getParentId() {
		return this.parentId;
	}
	public void setName(String value) {
		this.name = value;
	}
	public String getName() {
		return this.name;
	}
	public void setCode(String value) {
		this.code = value;
	}
	public String getCode() {
		return this.code;
	}
	public void setIcon(String value) {
		this.icon = value;
	}
	public String getIcon() {
		return this.icon;
	}
	public void setUrl(String value) {
		this.url = value;
	}
	public String getUrl() {
		return this.url;
	}
	public void setTarget(Integer value) {
		this.target = value;
	}
	public Integer getTarget() {
		return this.target;
	}
	public void setMenu(Integer value) {
		this.menu = value;
	}
	public Integer getMenu() {
		return this.menu;
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
	public void setDescription(String value) {
		this.description = value;
	}
	public String getDescription() {
		return this.description;
	}

	public List<Permission> getPermissionList() {
		return permissionList;
	}

	public void setPermissionList(List<Permission> permissionList) {
		this.permissionList = permissionList;
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
		if(obj instanceof Permission == false) return false;
		if(this == obj) return true;
		Permission other = (Permission)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}
}

