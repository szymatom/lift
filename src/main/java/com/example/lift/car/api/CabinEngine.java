package com.example.lift.car.api;

import com.example.lift.common.Movement;

public interface CabinEngine {
  void move(Movement direction);
  void stop();
}
