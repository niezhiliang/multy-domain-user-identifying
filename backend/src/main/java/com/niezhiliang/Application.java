package com.niezhiliang;

import com.niezhiliang.config.CorsConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication(scanBasePackages = {"com.niezhiliang"})
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class);
  }

  @Bean
  public CorsConfig authenticationTokenFilterBean() throws Exception {
    return new CorsConfig();
  }

}