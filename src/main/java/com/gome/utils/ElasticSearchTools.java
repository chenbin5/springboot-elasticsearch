package com.gome.utils;

import com.alibaba.fastjson.JSON;
import com.gome.pojo.LoggerEntry;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author chenbin78
 * @version 1.0
 * @create_date 2021/4/9 17:16
 */
@Component
public class ElasticSearchTools {



    @Value("${elasticsearch.log.name}")
    private String logIndexName;

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;


    /**
     * 创建索引
     * @throws Exception
     */
    public void createIndex() throws Exception {
        if (!existsIndex()) {
            //1、创建索引请求
            CreateIndexRequest request = new CreateIndexRequest(logIndexName);
            //2、客户端执行请求 IndexdicesClient,请求后获得相应
            CreateIndexResponse createIndexResponse =
                    client.indices().create(request, RequestOptions.DEFAULT);
            System.out.println(createIndexResponse);
        }
    }

    /**
     * 判断索引是否存在
     * @return
     */
    private Boolean existsIndex() {
        //1、创建获取索引请求
        GetIndexRequest request = new GetIndexRequest(logIndexName);
        //2、客户端发送请求
        boolean exists = false;
        try {
            exists = client.indices().exists(request, RequestOptions.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }

    /**
     * 将日志写入es
     * @param loggerEntry
     */
    public void addLogElasticSearch(LoggerEntry loggerEntry) throws Exception {
        createIndex();
        //创建请求
        IndexRequest request = new IndexRequest(logIndexName);
        //设置文档规则
        request.timeout("1s");
        //将我们的数据放入到请求中
        request.source(JSON.toJSONString(loggerEntry), XContentType.JSON);
        //客户端发送请求，获取响应结果
        IndexResponse response = null;
        try {
            response = client.index(request, RequestOptions.DEFAULT);
            System.out.println(response.toString());
            //获取文档状态
            System.out.println(response.status());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
