package com.example.jooq.config

import org.springframework.boot.autoconfigure.jooq.DefaultConfigurationCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JooqConfig {
    @Bean
    fun jooqDefaultConfigurationCustomizer(): DefaultConfigurationCustomizer {
        // 로그에서 필드를 찍을 때 테이블 스키마명이 보이지 않도록 설정
        return DefaultConfigurationCustomizer { c -> c.settings().withRenderSchema(false) }
    }
}
