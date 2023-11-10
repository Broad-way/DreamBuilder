package com.mingguang.dreambuilder.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.session.jdbc.config.annotation.web.http.EnableJdbcHttpSession;

import javax.sql.DataSource;

@Configuration
@EnableJdbcHttpSession
public class JdbcSessionConfig {
    @Bean
    public DataSourceInitializer dataSourceInitializer(DataSource dataSource){
        DataSourceInitializer initializer=new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        return initializer;
    }
}
