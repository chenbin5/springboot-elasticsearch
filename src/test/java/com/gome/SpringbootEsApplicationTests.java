package com.gome;

import com.alibaba.fastjson.JSON;
import com.gome.pojo.User;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.xcontent.XContentType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
class SpringbootEsApplicationTests {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    @Test
    public void createIndex() throws Exception {
        //1、创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("kuang_index");
        //2、客户端执行请求 IndexdicesClient,请求后获得相应
        CreateIndexResponse createIndexResponse =
                client.indices().create(request, RequestOptions.DEFAULT);
        System.out.println(createIndexResponse);
    }

    @Test
    public void testPutDocument() throws Exception {
        User user = new User(1,"张三", 18, new Date());
        //创建请求
        IndexRequest request = new IndexRequest("kuang_index");
        //设置文档规则
        request.id("1");
        request.timeout("1s");
        //将我们的数据放入到请求中
        request.source(JSON.toJSONString(user), XContentType.JSON);
        //客户端发送请求，获取响应结果
        IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        System.out.println(response.toString());
        //获取文档状态
        System.out.println(response.status());
    }

}
