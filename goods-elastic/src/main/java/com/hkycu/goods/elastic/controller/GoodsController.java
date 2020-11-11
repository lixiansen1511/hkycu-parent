package com.hkycu.goods.elastic.controller;

import com.hkycu.goods.elastic.ElasticConts;
import com.hkycu.goods.elastic.Result.CodeMsg;
import com.hkycu.goods.elastic.Result.Result;
import com.hkycu.goods.elastic.domain.Goods;
import com.hkycu.goods.elastic.service.ElasticService;
import com.hkycu.goods.elastic.service.GoodsServiceDB;
import org.apache.lucene.search.TotalHits;
import org.apache.lucene.util.fst.Util;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Resource
    private GoodsServiceDB goodsServiceDB;

    @Resource
    private ElasticService elasticService;

    @PostMapping("/querybyid")
    @ResponseBody
    public Result<Goods> queryGoods(Long goodsId) {
        Goods goods = this.goodsServiceDB.getGoodsById(goodsId);
        return Result.success(goods);
    }

    @RequestMapping("/querylist")
    @ResponseBody
    public Result<List<Goods>> queryGoodsList() {
        List<Goods> goodsList = this.goodsServiceDB.getGoodsList();
        return Result.success(goodsList);
    }


    @PostMapping("/elastic/addbyid")
    @ResponseBody
    public Result<Integer> elasticAddGoods(@RequestParam("goodsId") Long goodsId) {
        Goods goods = this.goodsServiceDB.getGoodsById(goodsId);
        Integer count = this.elasticService.insertDocument(
                ElasticConts.ELASTICSEARCH_GOODS_INDEX,
                goods,
                XContentType.JSON,
                "" + goodsId);
        return Result.success(count);
    }

    @PostMapping("/elastic/querybyid")
    @ResponseBody
    public Result<Goods> elasticQueryGoods(@RequestParam("goodsId") Long goodsId) {
        Goods goods = (Goods)this.elasticService.getDocument(ElasticConts.ELASTICSEARCH_GOODS_INDEX,
                "" + goodsId, Goods.class);
        System.out.println("【====goods====】" + goods);
        return Result.success(goods);
    }

    @PostMapping("/elastic/batchadd")
    @ResponseBody
    public Result<CodeMsg> elasticBatchAddGoods() {
        boolean flag = this.elasticService.batchInsertDocument(
                ElasticConts.ELASTICSEARCH_GOODS_INDEX,
                this.goodsServiceDB.getGoodsList(),
                XContentType.JSON);
        return Result.success(CodeMsg.SUCESS);
    }

    @PostMapping("/elastic/querypagelike")
    @ResponseBody
    public Result<List<Map<String, Object>>> elasticQueryPageLike(
            Integer pageNum, Integer pageSize, String column, String keyword
    ) {
        List<Map<String, Object>> mapList = this.elasticService.
                searchDocumentPage(ElasticConts.ELASTICSEARCH_GOODS_INDEX,
                        pageNum, pageSize, column, keyword);
        return Result.success(mapList);
    }

}