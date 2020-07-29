package com.example.lift.buttonpanel;

import com.example.lift.common.ButtonState;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

import static com.example.lift.common.ButtonState.*;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Slf4j
public class ButtonsPanel {
  private final List<ButtonState> floorButtons;

  public ButtonsPanel(int numberOfFloors) {
    this.floorButtons = IntStream.range(0, numberOfFloors)
        .mapToObj(floorButton -> INACTIVE)
        .collect(collectingAndThen(toList(), CopyOnWriteArrayList::new));
  }

  void activate(int floor) {
    floorButtons.set(floor, ACTIVE);
    log.info("Car button activated, floor: {}", floor);
  }

  void deactivate(int floor) {
    floorButtons.set(floor, INACTIVE);
    log.info("Car button deactivated, floor: {}", floor);
  }

  ButtonState getButtonState(int floor) {
    return floorButtons.get(floor);
  }

  boolean isInactive(int floor) {
    return INACTIVE.equals(floorButtons.get(floor));
  }

  boolean isActive(int floor) {
    return ACTIVE.equals(floorButtons.get(floor));
  }
}
