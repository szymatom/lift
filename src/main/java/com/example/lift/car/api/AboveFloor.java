package com.example.lift.car.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AboveFloor implements Position {
  private int floor;

  @Override
  public String toString() {
    return String.format("above floor %d", floor);
  }
}
