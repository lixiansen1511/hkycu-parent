package com.hkycu.goods.elastic.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Document(indexName = "hkycu_mall_goods")
public class Goods {
    @Id
    private Long id;                // 商品id

    @Field(name="title", type = FieldType.Text)
    private String title;           // 商品标题

    @Field(name="sell_point", type = FieldType.Text)
    private String sellPoint;       // 商品卖点

    @Field(type = FieldType.Long)
    private Long price;             // 商品价格

    @Field(type = FieldType.Text)
    private String image;           // 商品图片

    @Field(type = FieldType.Text)
    private String categoryName;    // 商品分类名称

    public Goods() {
    }

    public Goods(Long id, String title, String sellPoint, Long price, String image, String categoryName) {
        this.id = id;
        this.title = title;
        this.sellPoint = sellPoint;
        this.price = price;
        this.image = image;
        this.categoryName = categoryName;
    }
}