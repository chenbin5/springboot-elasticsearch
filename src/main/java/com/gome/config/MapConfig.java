package com.gome.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenbin78
 * @version 1.0
 * @create_date 2021/4/7 17:04
 */
@Configuration
@ConfigurationProperties(prefix = "video")
@EnableConfigurationProperties(MapConfig.class)
public class MapConfig {

    /**
     * 从配置文件中读取的limitSizeMap开头的数据
     * 注意：名称必须与配置文件中保持一致
     */
    private Map<String, Integer> videoMap = new HashMap<>();

    public Map<String, Integer> getVideoMap() {
        return videoMap;
    }

    public void setVideoMap(Map<String, Integer> videoMap) {
        this.videoMap = videoMap;
    }
}
