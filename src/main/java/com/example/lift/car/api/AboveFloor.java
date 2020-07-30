package com.example.lift.car.api;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class AboveFloor implements Position {
  private final int floor;

  @Override
  public String toString() {
    return String.format("above floor %d", floor);
  }
}
