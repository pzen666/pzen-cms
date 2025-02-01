package com.pzen.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class PzenCmsApplication {

    public static void main(String[] args) {
        SpringApplication.run(PzenCmsApplication.class, args);
    }

}
