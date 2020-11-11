package com.hkycu.goods.model;


public class Cart {
    private Long id;
    /** 货号：商品编号 */
    private String productSn;
    private String name; // 商品名称
    private String produce; // 发货地址
    private Integer produceId; //
    private Double price;  //
    /** 商品分类名称 */
    private String productCategoryName;
    /** 商品描述 */
    private String description;
    /** 库存 */
    private Integer stock;
    /** 销量 */
    private Integer sale;
    private String pic;  // 商品图片

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductSn() {
        return productSn;
    }

    public void setProductSn(String productSn) {
        this.productSn = productSn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProduce() {
        return produce;
    }

    public void setProduce(String produce) {
        this.produce = produce;
    }

    public Integer getProduceId() {
        return produceId;
    }

    public void setProduceId(Integer produceId) {
        this.produceId = produceId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProductCategoryName() {
        return productCategoryName;
    }

    public void setProductCategoryName(String productCategoryName) {
        this.productCategoryName = productCategoryName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getSale() {
        return sale;
    }

    public void setSale(Integer sale) {
        this.sale = sale;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}

