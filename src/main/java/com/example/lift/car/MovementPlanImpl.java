package com.example.lift.car;

import com.example.lift.car.api.MovementPlan;
import com.example.lift.car.api.Position;

import java.util.LinkedList;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
public class MovementPlanImpl implements MovementPlan {

  private final LinkedList<Integer> nextStops;
  private static final String UPDATED_PLAN = "Updated plan: {}";

  @Override
  public void addFloor(int floor, Position cabinPosition) {
    log.info("Adding floor {} to plan of lift stops", floor);
    if (nextStops.contains(floor)) {
      log.info("Floor {} is already in the plan", floor);
      log.info("Plan content has not changed: {}", nextStops);
      return;
    }

    if (nextStops.isEmpty() || isBetween(cabinPosition.getFloor(), floor, nextStops.getFirst())) {
      log.info("Placing floor {} as the first in plan", floor);
      nextStops.push(floor);
      log.info(UPDATED_PLAN, nextStops);
    }
    else
      findPlaceFor(floor);
  }

  @Override
  public int getNextStop() {
    final int next = nextStops.getFirst();
    log.info("Next stop is: {}", next);
    return next;
  }

  @Override
  public int removeFirst() {
    final int removed = nextStops.removeFirst();
    log.info("Removing floor {} from plan", removed);
    return removed;
  }

  private boolean isBetween(int position1, int floor, int position2) {
    return (floor >= position1 && floor < position2) ||
        (floor > position2 && floor <= position1);
  }

  private void findPlaceFor(int floor) {
    for (int i = 0; i < nextStops.size() - 1; i++)
      if (isBetween(nextStops.get(i), floor, nextStops.get(i + 1))) {
        log.info("Placing floor {} in the plan", floor);
        nextStops.add(i + 1, floor);
        log.info(UPDATED_PLAN, nextStops);
        return;
      }

    log.info("Adding floor {} to the end of plan", floor);
    nextStops.add(floor);
    log.info(UPDATED_PLAN, nextStops);
  }
}
