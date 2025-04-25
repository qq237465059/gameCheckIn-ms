package com.byxfd.game.checkin;


import com.byxfd.context.utils.applicationConstext.SpringContextHolder;
import com.byxfd.core.config.WebAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.ConfigurableApplicationContext;

@EnableConfigurationProperties
@SpringBootApplication(exclude = {
        DataSourceAutoConfiguration.class,
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class})
@ComponentScan(basePackages = {"com.byxfd"},
        excludeFilters = {@ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        value = WebAutoConfiguration.class)}
)
public class GameApplication {

    public static void main(String[] args) {
        try {
            ConfigurableApplicationContext ctx = SpringApplication.run(GameApplication.class, args);
            System.out.println("Application is success!");
        } catch(Throwable t) {
            t.printStackTrace();
        }
    }
    
    @Bean
    public SpringContextHolder springContextHolder() {
        return new SpringContextHolder();
    }
}
