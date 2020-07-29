package com.example.lift.car.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AtFloor implements Position {
  private int floor;

  @Override
  public String toString() {
    return String.format("at the floor %d", floor);
  }
}
