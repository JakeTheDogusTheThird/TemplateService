package com.example.TemplateService;

import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.StorageOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
class StorageConfig {
    @Bean
    Bucket bucket(@Value("${storage.host}") final String host, @Value("${storage.bucket}") final String bucket) {
        return StorageOptions
                .newBuilder()
                .setHost(host)
                .build()
                .getService()
                .get(bucket);
    }
}
