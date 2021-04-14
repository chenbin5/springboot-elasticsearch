package com.gome.aspect;

import com.alibaba.fastjson.JSON;
import com.gome.pojo.LoggerEntry;
import com.gome.utils.ElasticSearchTools;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * @author chenbin78
 * @version 1.0
 * @create_date 2021/4/8 17:04
 */
@Slf4j
@Aspect
@Component
public class LogAspect {


    @Value("${spring.application.name}")
    private String applicationName;

    private static final String THREW_EXCEPTION = "rethrow";
    private static final String RESOPMSE = "response";

    @Autowired
    private ElasticSearchTools elasticSearchTools;


    @Pointcut("execution(* com.gome.controller..*(..))")
    public void pointcut() {

    }

    @AfterReturning(returning = "data", pointcut = "pointcut()")
    public void after(JoinPoint joinPoint, Object data) {

        LoggerEntry loggerEntry = new LoggerEntry();
        //获取类名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = joinPoint.getSignature().getName();
        loggerEntry.setApplicationName(applicationName);
        loggerEntry.setClassName(className);
        // 获取方法名
        loggerEntry.setMethodName(methodName);
        // 所有的参数
        Object[] args = joinPoint.getArgs();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        HttpServletRequest request = null;
        if (methodName.toLowerCase().equals(THREW_EXCEPTION)) {
            request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                    .getRequestAttributes())).getRequest();
            request = (HttpServletRequest) ((HttpServletRequestWrapper) request).getRequest();
        } else {
            request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder
                    .getRequestAttributes())).getRequest();
        }
        String url = request.getRequestURI();
        String ip = request.getRemoteAddr();
        String methodType = request.getMethod();
        loggerEntry.setIp(ip);
        loggerEntry.setUrl(url);
        loggerEntry.setMethodType(methodType);
        String[] paramName = signature.getParameterNames();
        Object[] paramValue = joinPoint.getArgs();
        List<Object> objectsList = new ArrayList<>();
        for (int i = 0; i < paramValue.length; i++) {
            if ((paramValue[i] instanceof MultipartFile)) {
                MultipartFile multipartFile = (MultipartFile) paramValue[i];
                paramValue[i] = multipartFile.getOriginalFilename();
            }
        }

        int paramCnt = paramName.length;
        StringBuilder logMessage = new StringBuilder();
        logMessage.append("\n请求：")
                .append(request.getRequestURI()).append(",")
                .append(request.getMethod()).append(",")
                .append(request.getRemoteAddr()).append("\n");

        //请求参数
        List<Map<String, Object>> paramsList = new ArrayList<>();
        for (int i = 0; i < paramCnt; i++) {
            Map<String, Object> getParams = new HashMap<>(30);
            if (!paramName[i].equals(RESOPMSE) && !paramName[i].equals(THREW_EXCEPTION)) {
                getParams.put(paramName[i], paramValue[i]);
                paramsList.add(getParams);
            }
        }

        if (!methodName.toLowerCase().equals(THREW_EXCEPTION)) {
            logMessage.append("参数：").append(JSON.toJSONString(paramsList)).append("\n");
        }
        loggerEntry.setParamsList(paramsList);
        log.info(JSON.toJSONString(loggerEntry));
        try {
            elasticSearchTools.addLogElasticSearch(loggerEntry);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

