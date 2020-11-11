package com.hkycu.goodsseckill.model;

public class SeckillGoodsVo {
    private String createDateStr;
    private SeckillOrder seckillOrder;
    private SeckillOrderInfo seckillOrderInfo;
    private PmsProduct pmsProduct;

    public String getCreateDateStr() {
        return createDateStr;
    }

    public PmsProduct getPmsProduct() {
        return pmsProduct;
    }

    public void setPmsProduct(PmsProduct pmsProduct) {
        this.pmsProduct = pmsProduct;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

    public SeckillOrder getSeckillOrder() {
        return seckillOrder;
    }

    public void setSeckillOrder(SeckillOrder seckillOrder) {
        this.seckillOrder = seckillOrder;
    }

    public SeckillOrderInfo getSeckillOrderInfo() {
        return seckillOrderInfo;
    }

    public void setSeckillOrderInfo(SeckillOrderInfo seckillOrderInfo) {
        this.seckillOrderInfo = seckillOrderInfo;
    }
}
