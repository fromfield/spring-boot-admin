package com.github.shiro;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

@Configuration
public class ShiroConfig {

	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
        filterRegistration.setEnabled(true);
        filterRegistration.addUrlPatterns("/*"); 
        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
        return filterRegistration;
	}
	
	@Bean("shiroFilter")
	public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(securityManager);
		bean.setLoginUrl("/login");
		bean.setSuccessUrl("/index");
		bean.setUnauthorizedUrl("/unauthorize");
		
		Map<String, Filter> filters = bean.getFilters();
//		filters.put("perms", urlPermissionsFilter());
		bean.setFilters(filters);
		
		Map<String, String> chains = new LinkedHashMap<String, String>();
		chains.put("/assets/**", "anon");
		chains.put("/favicon.ico", "anon");
		chains.put("/t/**", "anon");
		chains.put("/upload/**", "anon");
		chains.put("/logout", "anon");
		chains.put("/login", "authc");
		chains.put("/login/sso", "anon");
		chains.put("/login/sso/callback", "anon");
		chains.put("/**", "authc");
		bean.setFilterChainDefinitionMap(chains);
		return bean;
	}

	@Bean("securityManager")
	public DefaultWebSecurityManager securityManager(CacheManager shiroCacheManager, AdminRealm adminRealm, SsoRealm ssoRealm) {

		Set<Realm> realmSet = new HashSet<Realm>();
		realmSet.add(adminRealm);
		realmSet.add(ssoRealm);

		DefaultWebSecurityManager manager = new DefaultWebSecurityManager(realmSet);
		manager.setCacheManager(shiroCacheManager);
		return manager;
	}
	
	@Bean
	@DependsOn("lifecycleBeanPostProcessor")
	public AdminRealm adminRealm(CacheManager shiroCacheManager, CredentialsMatcher credentialsMatcher) {
		AdminRealm adminRealm = new AdminRealm();
		adminRealm.setCacheManager(shiroCacheManager);
		adminRealm.setCredentialsMatcher(credentialsMatcher);
		return adminRealm;
	}

	
	@Bean
	public URLPermissionsFilter urlPermissionsFilter() {
		return new URLPermissionsFilter();
	}

	@Bean
	public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
		return new LifecycleBeanPostProcessor();
	}

	@Bean
	@DependsOn("cacheManager")
	public CacheManager shiroCacheManager(net.sf.ehcache.CacheManager cacheManager) {
		EhCacheManager ehCacheManager = new EhCacheManager();
		ehCacheManager.setCacheManager(cacheManager);
		return ehCacheManager;
	}
}