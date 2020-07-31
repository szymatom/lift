package com.example.lift.buttonpanel;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import lombok.extern.slf4j.Slf4j;

import static com.example.lift.buttonpanel.ButtonState.*;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;

@Slf4j
public class ButtonsPanel {

  private final List<ButtonState> floorButtons;
  private final String designation;

  public ButtonsPanel(int numberOfFloors, String designation) {
    this.floorButtons = IntStream.range(0, numberOfFloors)
        .mapToObj(floorButton -> INACTIVE)
        .collect(collectingAndThen(toList(), CopyOnWriteArrayList::new));
    this.designation = designation;
  }

  void activate(int floor) {
    floorButtons.set(floor, ACTIVE);
    log.info("{} button activated, floor: {}", designation, floor);
  }

  void deactivate(int floor) {
    floorButtons.set(floor, INACTIVE);
    log.info("{} button deactivated, floor: {}", designation, floor);
  }

  boolean isActive(int floor) {
    return ACTIVE.equals(floorButtons.get(floor));
  }
}
