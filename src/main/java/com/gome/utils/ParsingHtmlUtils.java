package com.gome.utils;

import com.gome.pojo.HtmlContent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenbin78
 * @version 1.0
 * @create_date 2021/4/7 11:29
 */
@Component
public class ParsingHtmlUtils {


    public List<HtmlContent> parsingJD(String keywords) {
        String url = "https://search.jd.com/Search?keyword=" + keywords;
        Document document = null;
        try {
            document = Jsoup.parse(new URL(url), 30000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Element element = document.getElementById("J_goodsList");
        //System.out.println(element.html());
        List<HtmlContent> resultList = new ArrayList<>();
        //获取所有的li元素
        Elements elements = element.getElementsByTag("li");
        for (Element el : elements) {
            String img = el.getElementsByTag("img").eq(0).attr("data-lazy-img");
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            HtmlContent htmlContent = new HtmlContent();
            htmlContent.setTitle(title);
            htmlContent.setPrice(price);
            htmlContent.setImg(img);
            resultList.add(htmlContent);
        }
        return resultList;
    }
}
