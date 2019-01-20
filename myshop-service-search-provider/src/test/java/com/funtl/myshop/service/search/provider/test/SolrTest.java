package com.funtl.myshop.service.search.provider.test;

import com.funtl.myshop.service.search.domain.TbItemResult;
import com.funtl.myshop.service.search.provider.MyShopServiceSearchProviderApplication;
import com.funtl.myshop.service.search.provider.mapper.TbItemResultMapper;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyShopServiceSearchProviderApplication.class)
public class SolrTest {

    @Autowired
    private SolrClient solrClient;

    @Autowired
    private TbItemResultMapper tbItemResultMapper;

    @Test
    public void testAddDocument() {
        SolrInputDocument document = new SolrInputDocument();
        document.addField("id", 562379);
        document.addField("tb_item_title", "new8- 三星 W999 黑色 电信3G手机 双卡双待双通");

        try {
            solrClient.add(document);
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeleteDocument() {
        try {
            solrClient.deleteByQuery("*:*");
            solrClient.commit();
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void initSolr() {
        List<TbItemResult> tbItemResults = tbItemResultMapper.selectAll();

        try {
            SolrInputDocument document = null;
            for (TbItemResult tbItemResult : tbItemResults) {
                document = new SolrInputDocument();
                document.addField("id", tbItemResult.getId());
                document.addField("tb_item_cid", tbItemResult.getTbItemCid());
                document.addField("tb_item_cname", tbItemResult.getTbItemCname());
                document.addField("tb_item_title", tbItemResult.getTbItemTitle());
                document.addField("tb_item_sell_point", tbItemResult.getTbItemSellPoint());
                document.addField("tb_item_desc", tbItemResult.getTbItemDesc());

                solrClient.add(document);
                solrClient.commit();
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSearch() {
        SolrQuery query = new SolrQuery();

        // 查询条件
        query.setQuery("手机");

        // 分页
        query.setStart(0);
        query.setRows(10);

        // 设置默认域
        query.set("df", "tb_item_keywords");

        // 设置高亮
        query.setHighlight(true);
        query.addHighlightField("tb_item_title");
        query.setHighlightSimplePre("<span style='color:red;'>");
        query.setHighlightSimplePost("</span>");

        // 开始查询
        try {
            QueryResponse queryResponse = solrClient.query(query);
            // 获取高亮
            Map<String, Map<String, List<String>>> highlighting = queryResponse.getHighlighting();
            SolrDocumentList results = queryResponse.getResults();
            for (SolrDocument result : results) {
                List<String> strings = highlighting.get(result.get("id")).get("tb_item_title");
                if (strings != null && strings.size() > 0) {
                    String title = strings.get(0);
                    System.out.println(title);
                }
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
