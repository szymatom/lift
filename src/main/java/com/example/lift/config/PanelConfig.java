package com.example.lift.config;

import com.example.lift.buttonpanel.ButtonsPanel;
import com.example.lift.buttonpanel.PanelController;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PanelConfig {

  @Value("${numberOfFloors:10}")
  private int numberOfFloors;

  @Bean
  public ButtonsPanel carButtonsPanel() {
    return new ButtonsPanel(numberOfFloors, "Car");
  }

  @Bean
  public ButtonsPanel callButtonsPanel() {
    return new ButtonsPanel(numberOfFloors, "Call");
  }

  @Bean
  public PanelController panelController(ApplicationEventPublisher applicationEventPublisher) {
    return new PanelController(carButtonsPanel(), callButtonsPanel(), applicationEventPublisher);
  }
}
