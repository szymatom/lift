package com.example.lift.car;

import com.example.lift.car.api.AboveFloor;
import com.example.lift.car.api.AtFloor;
import com.example.lift.car.api.BelowFloor;
import com.example.lift.car.api.Cabin;
import com.example.lift.car.api.Position;
import com.example.lift.common.Movement;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static com.example.lift.common.Movement.*;

@Slf4j
@AllArgsConstructor
public class CabinImpl implements Cabin {

  private static final String REPORT_POSITION = "Current cabin position: {}";

  @Getter
  @Setter
  private Movement movement;
  @Getter
  private Position position;

  @Override
  public void moveUp() {
    position = new BelowFloor(position.getFloor() + 1);
    movement = UP;
    log.info(REPORT_POSITION, position);
  }

  @Override
  public void moveDown() {
    position = new AboveFloor(position.getFloor() - 1);
    movement = DOWN;
    log.info(REPORT_POSITION, position);
  }

  @Override
  public void stop() {
    position = new AtFloor(position.getFloor());
    movement = NONE;
    log.info(REPORT_POSITION, position);
  }
}
