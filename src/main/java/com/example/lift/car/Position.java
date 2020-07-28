package com.example.lift.car;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface Position {
  int getFloor();
}

@Getter
@AllArgsConstructor
class BelowFloor implements Position {
  private int floor;

  @Override
  public String toString() {
    return String.format("below floor %d", floor);
  }
}

@Getter
@AllArgsConstructor
class AtFloor implements Position {
  private int floor;

  @Override
  public String toString() {
    return String.format("at the floor %d", floor);
  }
}