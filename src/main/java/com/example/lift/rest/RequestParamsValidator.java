package com.example.lift.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static java.lang.Integer.parseInt;

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
}
