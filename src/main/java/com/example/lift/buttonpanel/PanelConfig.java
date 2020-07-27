package com.example.lift.buttonpanel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PanelConfig {

  @Value("${numberOfFloors:10}")
  int numberOfFloors;

  @Bean
  public PanelController panelController(ApplicationEventPublisher applicationEventPublisher) {
    return new PanelController(CarButtonsPanel.getInstance(numberOfFloors), CallButtonsPanel.getInstance(numberOfFloors), applicationEventPublisher);
  }
}
