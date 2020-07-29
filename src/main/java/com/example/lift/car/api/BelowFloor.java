package com.example.lift.car.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BelowFloor implements Position {
  private int floor;

  @Override
  public String toString() {
    return String.format("below floor %d", floor);
  }
}
