package com.fh;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@MapperScan("com.fh.*.mapper")
public class SpringbootVueApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootVueApiApplication.class, args); }

        @EnableTransactionManagement
        @Configuration
        @MapperScan("com.fh.product.mapper.ProductMapper")
        public class  Config{
            /**
             * 分页
             * @return
             */
          @Bean
            public PaginationInterceptor paginationInterceptor(){
            return  new PaginationInterceptor();
        }
    }
}
