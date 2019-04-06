package com.cmcc.andedu.microservice.config.components;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * Created by gc on 2019/04/02.
 */
public class CommonInterceptor extends HandlerInterceptorAdapter {
    final Logger logger = LoggerFactory.getLogger(CommonInterceptor.class);

    private long preTime = 0l;
    private long postTime= 0l;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        preTime = System.currentTimeMillis();
        request.setAttribute("preTime", preTime);
        logger.debug("通用拦截器-控制层接口调用之前");
        logger.debug("contextPATH:{},servletPath:{},URI:{},URL:{},sessionID{}",new String[]{request.getContextPath(),request.getServletPath(),request.getRequestURI(),request.getRequestURL().toString(),request.getSession().getId()});
        String controllerType=handler.getClass().getName();
        logger.debug("通用拦截器-该请求调用的是处理器类型:"+controllerType);
        String requestPath=new UrlPathHelper().getLookupPathForRequest(request);
        logger.debug("通用拦截器-该请求调用的路径:{}",requestPath);
        if(handler.getClass().equals(HandlerMethod.class)){
            String invokeControllerClassName=((HandlerMethod)handler).getBeanType().toString();
            String invokeControllerMethodName=((HandlerMethod)handler).getMethod().getName();
            String invokeControllerFullMethodSign=handler.toString();
            logger.debug("通用拦截器-控制器名称:{},方法名称:{},完整的方法签名:{}.",new String[]{invokeControllerClassName,invokeControllerMethodName,invokeControllerFullMethodSign});
        }else{
            logger.debug("通用拦截器-匹配不到对应的控制器及方法");
        }
        //排除不需要拦截的URI
        String uri = request.getRequestURI();
        if(StringUtils.contains(uri,request.getContextPath()+"/demo")){
            logger.debug("通用拦截器-用户请求匹配/demo，系统判定为正常！");
            return true;
        }
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.debug("通用拦截器-控制层接口调用之后");
        postTime = System.currentTimeMillis();
        long temp = postTime - preTime;
        logger.debug("通用拦截器-控制层接口调用耗时{}毫秒", temp);
        request.setAttribute("postTime", postTime);
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //由于这个方法是在渲染视图后运行，因此在该方法中设置request属性没有效果
        logger.debug("通用拦截器-视图渲染之后");
        long completionTime = System.currentTimeMillis();
        Long preTime = (Long) request.getAttribute("preTime");
        Long postTime = (Long) request.getAttribute("postTime");
        if(preTime==null) preTime = -1L;
        if(postTime==null) postTime = -1L;
        long temp = completionTime - postTime;
        logger.debug("通用拦截器-视图渲染耗时{}毫秒", temp);
        super.afterCompletion(request, response, handler, ex);
    }
}