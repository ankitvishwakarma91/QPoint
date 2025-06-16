package com.softworkshub.qpoint.config;


import com.cloudinary.Cloudinary;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.HashMap;

@Configuration
public class CloudinaryConfig {


    @Bean
    public Cloudinary cloudinary(){
        Map<String,Object> config = new HashMap<>();
        config.put("cloud_name", "dapishwrv");
        config.put("api_key","334246496381434");
        config.put("api_secret", "FicLn2csEkJBbkHqEplwLsAsoKE");
        return new Cloudinary(config);
    }
}
