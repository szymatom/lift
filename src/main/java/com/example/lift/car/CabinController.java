package com.example.lift.car;

import com.example.lift.car.api.AtFloor;
import com.example.lift.car.api.Cabin;
import com.example.lift.car.api.CabinEngine;
import com.example.lift.car.api.MovementPlan;
import com.example.lift.common.Movement;
import com.example.lift.event.ButtonDeactivatedEvent;
import com.example.lift.event.CallButtonActivatedEvent;
import com.example.lift.event.CarButtonActivatedEvent;
import com.example.lift.event.EngineMovingDownEvent;
import com.example.lift.event.EngineMovingUpEvent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import lombok.RequiredArgsConstructor;

import static com.example.lift.common.Movement.*;

@RequiredArgsConstructor
public class CabinController {

  private final Cabin cabin;
  private final CabinEngine engine;
  private final MovementPlan movementPlan;
  private final ApplicationEventPublisher applicationEventPublisher;

  private volatile int nextStop;

  @EventListener
  public void handleCallButtonActivated(CallButtonActivatedEvent event) {
    movementPlan.addFloor(event.getFloor(), cabin.getPosition());
    nextMove();
  }

  @EventListener
  public void handleCarButtonActivated(CarButtonActivatedEvent event) {
    movementPlan.addFloor(event.getFloor(), cabin.getPosition());
    nextMove();
  }

  @EventListener
  public void handleMovingUpEvent(@SuppressWarnings("unused") EngineMovingUpEvent event) {
    cabin.moveUp();
    handleStop();
  }

  @EventListener
  public void handleMovingDownEvent(@SuppressWarnings("unused") EngineMovingDownEvent event) {
    cabin.moveDown();
    handleStop();
  }

  private void handleStop() {
    if(nextStop == cabin.getPosition().getFloor()) {
      engine.stop();
      cabin.stop();
      applicationEventPublisher.publishEvent(new ButtonDeactivatedEvent(this, nextStop));
      nextMove();
    }
  }

  private void nextMove() {
    nextStop = movementPlan.getNextStop();
    if (isStoppedAtTheFloor(nextStop))
      applicationEventPublisher.publishEvent(new ButtonDeactivatedEvent(this, nextStop));

    if (NONE.equals(cabin.getMovement())) {
      engine.move(getMoveDirection(nextStop));
    }
  }

  private Movement getMoveDirection(int nextStop) {
    if (nextStop > cabin.getPosition().getFloor())
      return UP;
    else if (nextStop < cabin.getPosition().getFloor())
      return DOWN;

    return NONE;
  }

  private boolean isStoppedAtTheFloor(int nextStop) {
    return NONE.equals(cabin.getMovement()) && isAtTheFloor(nextStop);
  }

  private boolean isAtTheFloor(int floor) {
    return cabin.getPosition() instanceof AtFloor && cabin.getPosition().getFloor() == floor;
  }
}
