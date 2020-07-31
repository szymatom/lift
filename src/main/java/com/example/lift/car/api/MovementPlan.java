package com.example.lift.car.api;

public interface MovementPlan {
  void addFloor(int floor, Position cabinPosition);
  int getNextStop();
  int removeFirst();
  boolean hasNext();
}
