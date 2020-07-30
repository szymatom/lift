package com.example.lift.car.api;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class AtFloor implements Position {
  private final int floor;

  @Override
  public String toString() {
    return String.format("at the floor %d", floor);
  }
}
