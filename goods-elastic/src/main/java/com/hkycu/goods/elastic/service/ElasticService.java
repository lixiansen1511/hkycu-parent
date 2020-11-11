package com.hkycu.goods.elastic.service;

import com.hkycu.goods.elastic.domain.Goods;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface ElasticService {
    
    /** 查询集群索引的状态 */
    Map catIndices(String indexName) throws IOException;

    Integer insertDocument(String elasticsearchGoodsIndex, Goods goods, XContentType json, String id);

    <T> T getDocument(String elasticsearchGoodsIndex, String s, Class<Goods> goodsClass);

    /** 批量添加文档 */
    boolean batchInsertDocument(String index, List<?> list, XContentType dataType);

    List<Map<String, Object>> searchDocumentPage(String index, Integer pageNum, Integer pageSize, String column, String keyword);
}