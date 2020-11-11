package com.hkycu.goodsseckill;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.hkycu.goodsseckill.dao")
public class GoodsSeckillApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoodsSeckillApplication.class, args);
	}

}
