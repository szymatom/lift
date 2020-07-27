package com.example.lift.rest;

import com.example.lift.common.Direction;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.example.lift.common.Direction.*;
import static java.lang.Integer.parseInt;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.EnumUtils.getEnum;

@Slf4j
@RequiredArgsConstructor
public class RequestParamsValidator {
  final int numberOfFloors;

  public boolean isValidFloor(String floor) {
    int parameter;

    try {
      parameter = parseInt(floor);
    } catch(NumberFormatException e) {
      log.error("Parameter floor must be integer number");
      return false;
    }

    if(parameter < 0 || parameter > numberOfFloors - 1) {
      log.error("Parameter \"floor\" must have value between {} and {} inclusive", 0, numberOfFloors - 1);
      return false;
    }
    return true;
  }

  public boolean isValidFloorAndDirection(String floor, String direction) {
    if(!isValidFloor(floor))
      return false;

    final int floorNumber = parseInt(floor);

    if(isNull(direction) || isNull(getEnum(Direction.class, direction.toUpperCase()))) {
      log.error("Parameter \"direction\" must have {} or {} value", UP, DOWN);
      return false;
    } else if(floorNumber == 0 && DOWN.equals(getEnum(Direction.class, direction.toUpperCase()))) {
      log.error("Parameter \"direction\" at ground level can only be {}", UP);
      return false;
    } else if(floorNumber == numberOfFloors - 1 && UP.equals(getEnum(Direction.class, direction.toUpperCase()))) {
      log.error("Parameter \"direction\" at highest floor can only be {}", DOWN);
      return false;
    }
    return true;
  }
}
