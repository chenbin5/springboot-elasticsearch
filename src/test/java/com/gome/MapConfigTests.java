package com.gome;

import com.gome.config.MapConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

/**
 * @author chenbin78
 * @version 1.0
 * @create_date 2021/4/7 17:06
 */
@SpringBootTest
public class MapConfigTests {

    @Autowired
    private MapConfig mapConfig;

    @Test
    public void testMapConfig() {
        Map<String, Integer> limitSizeMap = mapConfig.getVideoMap();
        if (limitSizeMap == null || limitSizeMap.size() <= 0) {
            System.out.println("limitSizeMap读取失败");
        } else {
            System.out.println("limitSizeMap读取成功，数据如下：");
            for (String key : limitSizeMap.keySet()) {
                System.out.println("key: " + key + ", value: " + limitSizeMap.get(key));
            }
        }

    }
}
