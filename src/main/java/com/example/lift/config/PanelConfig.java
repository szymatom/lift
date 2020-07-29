package com.example.lift.config;

import com.example.lift.buttonpanel.ButtonsPanel;
import com.example.lift.buttonpanel.PanelController;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class PanelConfig {

  @Value("${numberOfFloors:10}")
  private int numberOfFloors;

  @Bean
  @Scope("singleton")
  public ButtonsPanel carButtonsPanel() {
    return new ButtonsPanel(numberOfFloors);
  }

  @Bean
  @Scope("singleton")
  public ButtonsPanel callButtonsPanel() {
    return new ButtonsPanel(numberOfFloors);
  }

  @Bean
  public PanelController panelController(ApplicationEventPublisher applicationEventPublisher) {
    return new PanelController(carButtonsPanel(), callButtonsPanel(), applicationEventPublisher);
  }
}
