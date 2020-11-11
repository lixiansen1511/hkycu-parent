package com.hkycu.goods.elastic.service.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hkycu.goods.elastic.domain.Goods;
import com.hkycu.goods.elastic.service.ElasticService;
import io.netty.handler.codec.http.HttpMethod;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.apache.lucene.search.TotalHits;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.elasticsearch.core.internal.io.IOUtils.close;

@Service
public class ElasticSearchServiceImpl implements ElasticService {

    @Autowired
    private final RestHighLevelClient client;

    public ElasticSearchServiceImpl(RestHighLevelClient client) {
        this.client = client;
    }

    @Override
    public Map catIndices(String indexName) throws IOException {
        Request request =new Request(HttpMethod.GET.toString(), "/_cat/indices/" + indexName);
        request.addParameter("bytes", "b");
        request.addParameter("format", "json");
        Map<String, Long> map = new HashMap<>(4);

        try{
            Response response = client.getLowLevelClient().performRequest(request);
            if(response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String content = EntityUtils.toString(response.getEntity());
                JSONArray jsonArray = JSONArray.parseArray(content);
                // 遍历索引
                for(Object object: jsonArray) {
                    Map<String, String> indexInfoMap = (Map<String, String>) object;
                    System.err.println("【========index-map========】" + indexInfoMap);
                    long docsCount = Long.valueOf(indexInfoMap.get("docs.count"));
                    System.err.println("【========docsCount========】" + docsCount);
                }
            }
        } catch (Exception e) {
            System.err.println("获取索引信息失败");
            e.printStackTrace();
        } finally {
            close(client);
        }

        return map;
    }

    @Override
    public Integer insertDocument(String elasticsearchGoodsIndex, Goods goods, XContentType json, String id) {
        IndexRequest request = new IndexRequest(elasticsearchGoodsIndex);
        String dataString = JSONObject.toJSONString(goods);
        request.source(dataString, json);
        request.id(id);

        IndexResponse response = null;

        try{
            response = this.client.index(request, RequestOptions.DEFAULT);
        }catch (Exception e) {
            System.err.println("【====Elasticsearch创建文档异常：{}】" + e.getMessage());
        }finally {
            //close(client);
        }

        return response != null ? response.status().getStatus() : 400;
    }

    @Override
    public  <T> T getDocument(String elasticsearchGoodsIndex, String s, Class<Goods> goodsClass) {

        GetResponse response = null;

        try{
            if(elasticsearchGoodsIndex != null) { // 索引存在
                GetRequest request = new GetRequest(elasticsearchGoodsIndex, s);//索引和文档id
                response = this.client.get(request, RequestOptions.DEFAULT);
                String sourceAsString = response.getSourceAsString();

                if(StringUtils.isEmpty(sourceAsString)) { // 判读文档内容是否为空
                    return null;
                }

                T result = JSON.parseObject(sourceAsString, (Class<T>) goodsClass);
                return result;
            }
        }catch (Exception e) {
            System.err.println("【====Elasticsearch获取文档异常：{}】" + e.getMessage());
        }finally {
            //this.close(client);
        }

        return null;
    }

    private void close(RestHighLevelClient client) {
        try{
            client.close();
        }catch (Exception e) {
            System.err.println("【====Elasticsearch-关闭异常：{}】" + e.getMessage());
        }
    }

    @Override
    public boolean batchInsertDocument(String index, List<?> list, XContentType dataType) {
        BulkRequest request = new BulkRequest();
        BulkResponse response = null;
        for(Object object: list) {
            request.add(new IndexRequest(index).source(JSON.toJSONString(object), dataType));
        }

        try{
            response = this.client.bulk(request, RequestOptions.DEFAULT);
        }catch (Exception e) {
            System.err.println("【Elasticsearch批量添加文档信息异常：{}】"+ e.getMessage());
        }finally {
            //close(client);
        }

        return response != null && !response.hasFailures() ;
    }
    @Override
    public List<Map<String, Object>> searchDocumentPage(String index, Integer pageNum, Integer pageSize, String column, String keyword) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        SearchRequest request = new SearchRequest(index);
        SearchResponse response = null;

        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery(column, keyword);
        sourceBuilder.query(queryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        sourceBuilder.from((pageNum - 1) * pageSize);
        sourceBuilder.size(pageSize);
        sourceBuilder.sort("id", SortOrder.ASC);

        request.source(sourceBuilder);

        try{
            response = this.client.search(request, RequestOptions.DEFAULT);
            TotalHits totalHits = response.getHits().getTotalHits();
            long pageCounts = (totalHits.value + pageSize - 1) / pageSize;
            System.out.println("【====总页数====】" + pageCounts);

            for(SearchHit hit : response.getHits().getHits()) {
                mapList.add(hit.getSourceAsMap());
            }
        }catch (Exception e) {
            e.printStackTrace();
        }

        return mapList;
    }
}