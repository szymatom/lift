package com.example.lift.config;

import com.example.lift.car.CabinController;
import com.example.lift.car.CabinEngineImpl;
import com.example.lift.car.CabinImpl;
import com.example.lift.car.MovementPlanImpl;
import com.example.lift.car.api.AtFloor;
import com.example.lift.car.api.Cabin;
import com.example.lift.car.api.CabinEngine;
import com.example.lift.car.api.MovementPlan;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedList;

import static java.util.concurrent.Executors.newScheduledThreadPool;

@Configuration
public class CarConfig {

  @Value("${speed:4000}")
  private int speed;

  @Bean
  public Cabin cabin() {
    return new CabinImpl(new AtFloor(0));
  }

  @Bean
  public CabinEngine engine(ApplicationEventPublisher applicationEventPublisher) {
    return new CabinEngineImpl(newScheduledThreadPool(1), applicationEventPublisher, speed);
  }

  @Bean
  public MovementPlan movementPlan() {
    return new MovementPlanImpl(new LinkedList<>());
  }

  @Bean
  public CabinController cabinController(ApplicationEventPublisher applicationEventPublisher) {
    return new CabinController(cabin(), engine(applicationEventPublisher), movementPlan(), applicationEventPublisher);
  }
}
