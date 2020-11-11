package com.hkycu.goodsseckill.service.Impl;

import com.hkycu.goodsseckill.dao.PmsProductDao;
import com.hkycu.goodsseckill.model.PmsProduct;
import com.hkycu.goodsseckill.service.ProductSeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class ProductSeckillServiceImpl implements ProductSeckillService {
    @Autowired
    private PmsProductDao pmsProductDao;
    @Override
    public List<PmsProduct> querySeckillProduct() {
        return pmsProductDao.querySeckillProduct();
    }

    @Override
    public PmsProduct selectSeckillDetailById(Long id) {
        return pmsProductDao.selectByPrimaryKey(id);
    }
}
