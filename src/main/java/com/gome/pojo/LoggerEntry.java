package com.gome.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * @author chenbin78
 * @version 1.0
 * @create_date 2021/4/8 17:35
 */
@Data
@ToString
public class LoggerEntry {

    private String applicationName;
    private String ip;
    private String url;
    private String methodType;
    private String env;
    private String className;
    private String methodName;
    private List<Map<String, Object>> paramsList;
    private StringBuilder result;
}
