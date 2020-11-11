package com.hkycu.goodsseckill.service;

import com.hkycu.goodsseckill.model.PmsProduct;

import java.util.Date;
import java.util.List;

public interface ProductSeckillService {
    List<PmsProduct> querySeckillProduct();
    PmsProduct selectSeckillDetailById(Long id);
}
