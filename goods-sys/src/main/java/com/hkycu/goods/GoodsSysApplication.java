package com.hkycu.goods;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hkycu.goods.dao")
public class GoodsSysApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoodsSysApplication.class, args);
	}

}
