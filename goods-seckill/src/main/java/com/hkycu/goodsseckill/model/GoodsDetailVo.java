package com.hkycu.goodsseckill.model;

public class GoodsDetailVo {
    private String startDateStr;
    private String endDateStr;
    private int seckillStatus = 0;//秒杀状态：0未开始，1秒杀中，2：秒杀结束
    private int remainSeconds = 0;//倒计时：大于0：未开始，0：秒杀中，-1：秒杀结束
    private SeckillGoods seckillGoods;
    private UmsMember umsMember;

    public String getStartDateStr() {
        return startDateStr;
    }

    public void setStartDateStr(String startDateStr) {
        this.startDateStr = startDateStr;
    }

    public String getEndDateStr() {
        return endDateStr;
    }

    public void setEndDateStr(String endDateStr) {
        this.endDateStr = endDateStr;
    }

    public int getSeckillStatus() {
        return seckillStatus;
    }

    public void setSeckillStatus(int seckillStatus) {
        this.seckillStatus = seckillStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public SeckillGoods getSeckillGoods() {
        return seckillGoods;
    }

    public void setSeckillGoods(SeckillGoods seckillGoods) {
        this.seckillGoods = seckillGoods;
    }

    public UmsMember getUmsMember() {
        return umsMember;
    }

    public void setUmsMember(UmsMember umsMember) {
        this.umsMember = umsMember;
    }
}
