package com.github.shiro;

import org.apache.shiro.authc.AuthenticationToken;


public class SsoToken implements AuthenticationToken {

	private Integer fid;
	private Integer uid;
	private static final long serialVersionUID = 808966623691691921L;

	public SsoToken(){
	}

	public SsoToken(Integer fid, Integer uid) {
		this.fid = fid;
		this.uid = uid;
	}

	@Override
	public Object getPrincipal() {
		return this.uid;
	}

	@Override
	public Object getCredentials() {
		return this.fid;
	}

	

}
