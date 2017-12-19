package com.github.util;

import com.github.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.annotation.Resource;

/** 解析SessionScope注解并赋值 */
public class SessionScopeMethodArgumentResolver implements HandlerMethodArgumentResolver {
	
	@Resource private UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (parameter.hasParameterAnnotation(SessionScope.class) || parameter.getMethodAnnotation(SessionScope.class) != null) {
            return true;
        }
        return false;
    }
    
    /** 解析参数, 返回参数值 */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        
    	//从session中取SessionScope注解里的value属性值的key的value
        String annotationValue = parameter.getParameterAnnotation(SessionScope.class).value();
        if (StringUtils.isEmpty(annotationValue)) {
			annotationValue = parameter.getMethodAnnotation(SessionScope.class).value();
		}
        
        if (StringUtils.isEmpty(annotationValue)) {
			throw new IllegalStateException("SessionScope Annotation must have a value.");
		}
        
        if (annotationValue.equals(Constants.LOGIN_USER)) {
        	// 兼容APP, token方式解析
        	String token = webRequest.getHeader("X-Auth-Token");
        	if (StringUtils.isNotEmpty(token)) {
//    			OauthToken oauthToken = oauthTokenService.getByToken(token);
//    			return userService.getByPhone(oauthToken.getUsername());
        	}
		}
        
        return webRequest.getAttribute(annotationValue, NativeWebRequest.SCOPE_SESSION);
    }
}

