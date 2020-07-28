package com.example.lift.buttonpanel;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class PanelConfig {

  @Value("${numberOfFloors:10}")
  int numberOfFloors;

  @Bean
  public CarButtonsPanel carButtonsPanel() {
    return CarButtonsPanel.getInstance(numberOfFloors);
  }

  @Bean
  public CallButtonsPanel callButtonsPanel() {
    return CallButtonsPanel.getInstance(numberOfFloors);
  }

  @Bean
  @Scope("singleton")
  public PanelController panelController(ApplicationEventPublisher applicationEventPublisher) {
    return new PanelController(carButtonsPanel(), callButtonsPanel(), applicationEventPublisher);
  }
}
