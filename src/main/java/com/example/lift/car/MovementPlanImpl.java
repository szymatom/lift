package com.example.lift.car;

import com.example.lift.car.api.MovementPlan;
import com.example.lift.car.api.Position;

import java.util.LinkedList;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MovementPlanImpl implements MovementPlan {

  private final LinkedList<Integer> nextStops;

  @Override
  public void addFloor(int floor, Position cabinPosition) {
    if (nextStops.contains(floor))
      return;

    if (nextStops.isEmpty() || isBetween(cabinPosition.getFloor(), floor, nextStops.getFirst()))
      nextStops.push(floor);
    else
      findPlaceFor(floor);
  }

  @Override
  public int getNextStop() {
    return nextStops.getFirst();
  }

  @Override
  public int removeFirst() {
    return nextStops.removeFirst();
  }

  private boolean isBetween(int position1, int floor, int position2) {
    return (floor >= position1 && floor < position2) ||
        (floor > position2 && floor <= position1);
  }

  private void findPlaceFor(int floor) {
    for (int i = 0; i < nextStops.size() - 1; i++)
      if (isBetween(nextStops.get(i), floor, nextStops.get(i + 1))) {
        nextStops.add(i + 1, floor);
        return;
      }

    nextStops.add(floor);
  }
}
