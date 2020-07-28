package com.example.lift.car;

import com.example.lift.car.api.Cabin;
import com.example.lift.car.api.CabinEngine;
import com.example.lift.event.CallButtonActivatedEvent;
import com.example.lift.event.CarButtonActivatedEvent;

import org.springframework.context.event.EventListener;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CabinController {

  private final Cabin cabinImpl;
  private final CabinEngine engine;

  @EventListener
  public void handleCallButtonActivated(CallButtonActivatedEvent event) {
    engine.stop();
  }

  @EventListener
  public void handleCarButtonActivated(CarButtonActivatedEvent event) {
    engine.moveUp();
  }
}
