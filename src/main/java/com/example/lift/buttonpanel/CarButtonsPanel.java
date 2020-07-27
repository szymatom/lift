package com.example.lift.buttonpanel;

import com.example.lift.common.ButtonState;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

import static com.example.lift.common.ButtonState.*;
import static java.util.Objects.isNull;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Slf4j
public final class CarButtonsPanel {
  private final List<ButtonState> floorButtons;
  private static CarButtonsPanel panel;

  private CarButtonsPanel(int numberOfFloors) {
    this.floorButtons = IntStream.range(0, numberOfFloors)
        .mapToObj(floorButton -> INACTIVE)
        .collect(collectingAndThen(toList(), CopyOnWriteArrayList::new));
  }

  public static CarButtonsPanel getInstance(int numberOfFloors) {
    if (isNull(panel))
      panel = new CarButtonsPanel(numberOfFloors);

    if (numberOfFloors != panel.floorButtons.size())
      throw new IllegalArgumentException("Cannot create new CarButtons panel instance");

    return panel;
  }

  void activate(int floor) {
    panel.floorButtons.set(floor, ACTIVE);
    log.info("Car button activated, floor: {}", floor);
  }

  void deactivate(int floor) {
    panel.floorButtons.set(floor, INACTIVE);
    log.info("Car button deactivated, floor: {}", floor);
  }

  ButtonState getButtonState(int floor) {
    return panel.floorButtons.get(floor);
  }

  boolean isInactive(int floor) {
    return INACTIVE.equals(panel.floorButtons.get(floor));
  }
}
