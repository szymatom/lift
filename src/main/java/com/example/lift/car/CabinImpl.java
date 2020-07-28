package com.example.lift.car;

import com.example.lift.car.api.Cabin;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CabinImpl implements Cabin {

  private Movement movement;
  private Position position = new AtFloor(0);
  private int nextStop;

  @Override
  public void moveUp() {

  }
}
