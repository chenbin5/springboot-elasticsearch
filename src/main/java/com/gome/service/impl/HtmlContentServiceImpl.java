package com.gome.service.impl;

import com.alibaba.fastjson.JSON;
import com.gome.pojo.HtmlContent;
import com.gome.service.HtmlContentService;
import com.gome.utils.ParsingHtmlUtils;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * @author chenbin78
 * @version 1.0
 * @create_date 2021/4/7 13:44
 */
@Service
public class HtmlContentServiceImpl implements HtmlContentService {

    @Autowired
    @Qualifier("restHighLevelClient")
    private RestHighLevelClient client;

    @Autowired
    private ParsingHtmlUtils parsingHtmlUtils;

    @Override
    public Boolean parsingContent(String keywords) {
        List<HtmlContent> list = parsingHtmlUtils.parsingJD(keywords);

        BulkRequest bulkRequest = new BulkRequest();
        bulkRequest.timeout("2s");
        BulkResponse bulk = null;
        if (null != list && list.size() > 0) {
           list.forEach(htmlContent -> {
               bulkRequest.add(new IndexRequest("jd_goods")
               .source(JSON.toJSONString(htmlContent), XContentType.JSON));
           });
           try {
               bulk = client.bulk(bulkRequest, RequestOptions.DEFAULT);
           } catch (IOException e) {
               e.printStackTrace();
           }
           return !bulk.hasFailures();
       }
        return false;
    }
}
