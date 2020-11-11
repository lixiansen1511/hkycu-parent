package com.hkycu.goods.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * oms_order_item
 * @author 
 */
public class OmsOrderItem implements Serializable {
    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderSn;

    private Long productId;

    private String productPic;

    private String productName;

    private String productBrand;

    private String productSn;

    /**
     * 销售价格
     */
    private Double productPrice;

    private Double totalPrice;

    /**
     * 购买数量
     */
    private Integer productQuantity;

    /**
     * 商品sku编号
     */
    private Long productSkuId;

    /**
     * 商品sku条码
     */
    private String productSkuCode;

    /**
     * 商品分类id
     */
    private Long productCategoryId;

    /**
     * 商品促销名称
     */
    private String promotionName;

    /**
     * 商品促销分解金额
     */
    private BigDecimal promotionAmount;

    /**
     * 优惠券优惠分解金额
     */
    private BigDecimal couponAmount;

    /**
     * 积分优惠分解金额
     */
    private BigDecimal integrationAmount;

    /**
     * 该商品经过优惠后的分解金额
     */
    private BigDecimal realAmount;

    private Integer giftIntegration;

    private Integer giftGrowth;

    /**
     * 商品销售属性:[{"key":"颜色","value":"颜色"},{"key":"容量","value":"4G"}]
     */
    private String productAttr;

    /**
     * 供应商名称
     */
    private String produceName;

    /**
     * 供应商id
     */
    private Integer produceId;

    private OmsOrder omsOrder;

    private static final long serialVersionUID = 1L;

    public OmsOrder getOmsOrder() {
        return omsOrder;
    }

    public void setOmsOrder(OmsOrder omsOrder) {
        this.omsOrder = omsOrder;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

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

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Integer getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(Integer productQuantity) {
        this.productQuantity = productQuantity;
    }

    public Long getProductSkuId() {
        return productSkuId;
    }

    public void setProductSkuId(Long productSkuId) {
        this.productSkuId = productSkuId;
    }

    public String getProductSkuCode() {
        return productSkuCode;
    }

    public void setProductSkuCode(String productSkuCode) {
        this.productSkuCode = productSkuCode;
    }

    public Long getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getPromotionName() {
        return promotionName;
    }

    public void setPromotionName(String promotionName) {
        this.promotionName = promotionName;
    }

    public BigDecimal getPromotionAmount() {
        return promotionAmount;
    }

    public void setPromotionAmount(BigDecimal promotionAmount) {
        this.promotionAmount = promotionAmount;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public BigDecimal getIntegrationAmount() {
        return integrationAmount;
    }

    public void setIntegrationAmount(BigDecimal integrationAmount) {
        this.integrationAmount = integrationAmount;
    }

    public BigDecimal getRealAmount() {
        return realAmount;
    }

    public void setRealAmount(BigDecimal realAmount) {
        this.realAmount = realAmount;
    }

    public Integer getGiftIntegration() {
        return giftIntegration;
    }

    public void setGiftIntegration(Integer giftIntegration) {
        this.giftIntegration = giftIntegration;
    }

    public Integer getGiftGrowth() {
        return giftGrowth;
    }

    public void setGiftGrowth(Integer giftGrowth) {
        this.giftGrowth = giftGrowth;
    }

    public String getProductAttr() {
        return productAttr;
    }

    public void setProductAttr(String productAttr) {
        this.productAttr = productAttr;
    }

    public String getProduceName() {
        return produceName;
    }

    public void setProduceName(String produceName) {
        this.produceName = produceName;
    }

    public Integer getProduceId() {
        return produceId;
    }

    public void setProduceId(Integer produceId) {
        this.produceId = produceId;
    }
}