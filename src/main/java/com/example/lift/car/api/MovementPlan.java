package com.example.lift.car.api;

import com.example.lift.common.Movement;

public interface MovementPlan {
  void addFloor(int floor, Movement cabinMovement, Position cabinPosition);
  int getNextStop();
  void removeFirst();
}
