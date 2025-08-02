package com.example.BackInteWeb.config;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudinaryConfig {
    @Bean
    public Cloudinary cloudinary() {
        return new Cloudinary(ObjectUtils.asMap(
             "cloud_name", "dpzbp6ila",
                "api_key", "587632253121175",
                "api_secret", "DkbrLvD0bIkf1GZ0TesEsfZKbIc",
                "secure", true
        ));
    }
}
