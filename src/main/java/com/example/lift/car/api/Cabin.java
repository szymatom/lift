package com.example.lift.car.api;

import com.example.lift.common.Movement;

public interface Cabin {
  void moveUp();
  void moveDown();
  void stop();
  Position getPosition();
  Movement getMovement();
}
