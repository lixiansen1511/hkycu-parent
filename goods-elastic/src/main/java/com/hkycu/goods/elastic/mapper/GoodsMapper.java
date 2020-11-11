package com.hkycu.goods.elastic.mapper;

import com.hkycu.goods.elastic.domain.Goods;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodsMapper {
    /** 查询所有的商品 */
    List<Goods> getGoodsList();
    /** 根据id查询商品 */
    Goods getGoodsById(Long id);
}