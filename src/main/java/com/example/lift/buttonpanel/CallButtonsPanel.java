package com.example.lift.buttonpanel;

import com.example.lift.common.ButtonState;
import com.example.lift.common.Direction;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

import static com.example.lift.common.ButtonState.*;
import static com.example.lift.common.Direction.*;
import static java.util.Objects.isNull;

@Slf4j
public final class CallButtonsPanel {
  private final List<Map<Direction, ButtonState>> floorButtons;
  private static CallButtonsPanel panel;

  private CallButtonsPanel(int numberOfFloors) {
    this.floorButtons = IntStream.range(0, numberOfFloors)
        .mapToObj(current -> getButtons(current, numberOfFloors))
        .collect(Collectors.toList());
  }

  private Map<Direction, ButtonState> getButtons(int currentFloor, int numberOfFloors) {
    Map<Direction, ButtonState> result = new ConcurrentHashMap<>();
    if (currentFloor == 0)
      result.put(UP, INACTIVE);
    else if (currentFloor == numberOfFloors - 1)
      result.put(DOWN, INACTIVE);
    else {
      result.put(UP, INACTIVE);
      result.put(DOWN, INACTIVE);
    }

    return result;
  }

  public static CallButtonsPanel getInstance(int numberOfFloors) {
    if (isNull(panel))
      panel = new CallButtonsPanel(numberOfFloors);

    if(numberOfFloors != panel.floorButtons.size())
      throw new IllegalArgumentException("Cannot create new CallButtons panel instance");

    return panel;
  }

  void activate(int floor, Direction direction) {
    panel.floorButtons.get(floor).put(direction, ACTIVE);
    log.info("Call button activated, floor: {}, direction {}", floor, direction);
  }

  void deactivate(int floor, Direction direction) {
    panel.floorButtons.get(floor).put(direction, INACTIVE);
    log.info("Car button deactivated, floor: {}, direction: {}", floor, direction);
  }

  ButtonState getButtonState(int floor, Direction direction) {
    return panel.floorButtons.get(floor).get(direction);
  }

  boolean isInactive(int floor, Direction direction) {
    return INACTIVE.equals(panel.floorButtons.get(floor).get(direction));
  }
}
