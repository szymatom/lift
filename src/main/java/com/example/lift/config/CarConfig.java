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
import org.springframework.context.annotation.Scope;

import java.util.LinkedList;

import static com.example.lift.common.Movement.NONE;

@Configuration
public class CarConfig {

  @Value("speed")
  private int speed;

  @Bean
  @Scope("singleton")
  public Cabin cabin() {
    return new CabinImpl(NONE, new AtFloor(0));
  }

  @Bean
  @Scope("singleton")
  public CabinEngine engine(ApplicationEventPublisher applicationEventPublisher) {
    return new CabinEngineImpl(applicationEventPublisher, speed);
  }

  @Bean
  @Scope("singleton")
  public MovementPlan movementPlan() {
    return new MovementPlanImpl(new LinkedList<>());
  }

  @Bean
  public CabinController cabinController(ApplicationEventPublisher applicationEventPublisher) {
    return new CabinController(cabin(), engine(applicationEventPublisher), movementPlan(), applicationEventPublisher);
  }
}
