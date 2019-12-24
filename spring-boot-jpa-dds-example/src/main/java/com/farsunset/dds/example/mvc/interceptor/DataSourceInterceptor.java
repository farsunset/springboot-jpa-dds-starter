
package com.farsunset.dds.example.mvc.interceptor;

import com.farsunset.boot.dds.jpa.thread.SourceThreadLocal;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class DataSourceInterceptor extends HandlerInterceptorAdapter {

    private static final String DATA_SOURCE_KEY = "DS_KEY";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2)  {

        String key = request.getHeader(DATA_SOURCE_KEY);
        SourceThreadLocal.setSourceKey(key);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex){
        SourceThreadLocal.clearSourceKey();
    }

}
