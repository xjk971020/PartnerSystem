package cn.dezhi.welfare.partner.service.impl;

import cn.dezhi.welfare.partner.response.CommonReturnType;
import cn.dezhi.welfare.partner.response.error.BussinessException;
import cn.dezhi.welfare.partner.response.error.EmBusinessError;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.HighlightOptions;
import org.springframework.data.solr.core.query.HighlightQuery;
import org.springframework.data.solr.core.query.SimpleHighlightQuery;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xjk
 * @date 2019/4/26 -  20:22
 * solr搜索
 **/
@Service
public class SolrService {

    @Autowired
    private SolrClient solrClient;

    /**
     * solr搜索
     * @param keyword 搜索关键字
     * @param current 起始查询的记录
     * @param size    每页的记录数
     * @return
     */
    public CommonReturnType searchGoods(String keyword,Integer current,Integer size) {
        if (StringUtils.isEmpty(keyword)) {
            throw new BussinessException(EmBusinessError.PARMETER_VALIDATION_ERROR,"请输入查询内容");
        }
        //设置solr中定义的域
        String[] fields = {"goods_name", "brand_name", "type_name", "goods_id","goods_attr","goods_barcode",
                "type_id","brand_id","check_status","shelf_status","add_time","goods_pic","supplier_sku","goods_sku",
                "short_name","net_weight","gross_weight","short_description","big_pic","goods_price","goods_description",
                "goods_channel","score","goods_link", "brand_name","type_name"};
        //高亮配置
        SolrQuery query = new SolrQuery();
        String[] lightNames = {"goods_name", "brand_name", "type_name"};
        //设置高亮选项
        query.setParam("hl", "true");
        query.setParam("hl.fl", lightNames);
        query.setHighlightSimplePre("<em  style='color: red'>");
        query.setHighlightSimplePost("</em>");

        if (size <= 0 || size == null) {
            size = 10;
        }
        //默认第一页
        if (current <= 1 || current == null) {
            current = 1;
        } else {
            current = (current - 1) * size;
        }
        query.setStart(current);
        query.setRows(size);
        query.set("wt","json");

        /**
         * 设置查询关键字的域
         * 一般对应solr中的复制域(<copyFiled>)。
         * 因为用户查询的数据不确定是什么，定义在复制域中的字段，Solr会自动进行多字段查询匹配
         * 在Solr中查询语句：/select?q=keyword:xxx
         */
        query.set("q", "keyword:*" + keyword + "*");

        QueryResponse response;
        try {
            response = solrClient.query(query);
            //获取被高亮的数据集合，其中的数据结构类似：[{id: "123", field: "<em>xxx</em>"}]
            Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
            //获取匹配结果
            SolrDocumentList documents = response.getResults();
            long numFound = documents.getNumFound();
            //获取匹配的数据个数
            if (numFound != 0) {
                List<Object> entityList = new ArrayList<>();
                for (SolrDocument document : documents) {
                    //documents中存放了匹配的所有数据（未高亮），而highlighting中存放了高亮匹配的数据（高亮）
                    //通过id主键获取到id值，在highlighting中通过id值获取对应的高亮数据
                    Map<String, List<String>> listMap = highlighting.get(document.getFieldValue("goods_id").toString());
                    for (int i = 0; i < lightNames.length; i++) {
                        if (listMap.get(lightNames[i]) != null) {
                            //根据设置的高亮域，将documents中未高亮的域的值替换为高亮的值
                            document.setField(lightNames[i], listMap.get(lightNames[i]).get(0));
                        }
                    }
                    Map<String, Object> fieldMap = new HashMap<>();
                    for (int i = 0; i < fields.length; i++) {
                        fieldMap.put(fields[i], String.valueOf(document.getFieldValue(fields[i])));
                    }
                    entityList.add(fieldMap);
                }
                Map<String,Object> data = new HashMap<>();
                data.put("size",numFound);
//                data.put("data",entityList);
                data.put("data",highlighting);
                return CommonReturnType.create(data);
            } else {
                return CommonReturnType.create("未搜索到任何结果");
            }
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
