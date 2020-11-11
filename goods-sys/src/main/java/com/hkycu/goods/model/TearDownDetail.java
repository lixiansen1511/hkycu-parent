package com.hkycu.goods.model;

import java.io.Serializable;

/**
 * tear_down_detail
 * @author 
 */
public class TearDownDetail implements Serializable {
    private Long id;

    private Long orderId;

    private Integer produceId;

    private String produceName;

    private String productName;

    private Long num;

    private Integer productId;

    private static final long serialVersionUID = 1L;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Integer getProduceId() {
        return produceId;
    }

    public void setProduceId(Integer produceId) {
        this.produceId = produceId;
    }

    public String getProduceName() {
        return produceName;
    }

    public void setProduceName(String produceName) {
        this.produceName = produceName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Long getNum() {
        return num;
    }

    public void setNum(Long num) {
        this.num = num;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public TearDownDetail() {
    }

    public TearDownDetail(Long orderId,  String productName, Long num, Integer productId) {
        this.orderId = orderId;
        this.productName = productName;
        this.num = num;
        this.productId = productId;
    }
}