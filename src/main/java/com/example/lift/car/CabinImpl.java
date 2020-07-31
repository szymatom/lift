package com.example.lift.car;

import com.example.lift.car.api.AboveFloor;
import com.example.lift.car.api.AtFloor;
import com.example.lift.car.api.BelowFloor;
import com.example.lift.car.api.Cabin;
import com.example.lift.car.api.Position;

import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CabinImpl implements Cabin {

  private static final String REPORT_POSITION = "Current cabin position: {}";

  @Getter
  private Position position;

  public CabinImpl(Position position) {
    this.position = position;
    log.info("Lift's cabin is waiting {}", position);
  }

  @Override
  public void moveUp() {
    position = new BelowFloor(position.getFloor() + 1);
    log.info(REPORT_POSITION, position);
  }

  @Override
  public void moveDown() {
    position = new AboveFloor(position.getFloor() - 1);
    log.info(REPORT_POSITION, position);
  }

  @Override
  public void stop() {
    position = new AtFloor(position.getFloor());
    log.info(REPORT_POSITION, position);
  }
}
