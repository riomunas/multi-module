package org.example;

import org.axonframework.config.ConfigurerModule;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AxonConfig {
  // omitting other configuration methods...
  @Bean
  public ConfigurerModule processorDefaultConfigurerModule() {
    return configurer -> configurer.eventProcessing(EventProcessingConfigurer::usingTrackingEventProcessors);
  }
}