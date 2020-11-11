package com.hkycu.goods.elastic;

import com.hkycu.goods.elastic.mapper.GoodsMapper;
import com.hkycu.goods.elastic.service.ElasticService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
class GoodsElasticApplicationTests {
	@Autowired
	private ElasticService elasticService;
	@Test
	void contextLoads() {
	}


	@Resource
	private GoodsMapper goodsMapper;
	@Test
	void testGoodsById() {
		System.out.println(
				this.goodsMapper.getGoodsById(1001L)
		);
	}

	@Test
	void testGoodsList() {
		System.out.println(
				this.goodsMapper.getGoodsList()
		);
	}
}
