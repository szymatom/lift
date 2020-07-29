package com.example.lift.car;

import com.example.lift.car.api.MovementPlan;
import com.example.lift.car.api.Position;
import com.example.lift.common.Movement;

import java.util.LinkedList;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MovementPlanImpl implements MovementPlan {
  //TODO:make operations synchronized
  private final LinkedList<Integer> nextStops;

  @Override
  public void addFloor(int floor, Movement cabinMovement, Position cabinPosition) {
    nextStops.add(floor);
  }

  @Override
  public int getNextStop() {
    return nextStops.getFirst();
  }

  @Override
  public void removeFirst() {
    nextStops.removeFirst();
  }
}
