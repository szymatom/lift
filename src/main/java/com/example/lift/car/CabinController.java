package com.example.lift.car;

import com.example.lift.car.api.AtFloor;
import com.example.lift.car.api.Cabin;
import com.example.lift.car.api.CabinEngine;
import com.example.lift.car.api.MovementPlan;
import com.example.lift.event.ButtonDeactivatedEvent;
import com.example.lift.event.CallButtonActivatedEvent;
import com.example.lift.event.CarButtonActivatedEvent;
import com.example.lift.event.EngineMovingDownEvent;
import com.example.lift.event.EngineMovingUpEvent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import static lombok.AccessLevel.PACKAGE;

@RequiredArgsConstructor
@Slf4j
public class CabinController {

  private final Cabin cabin;
  private final CabinEngine engine;
  private final MovementPlan movementPlan;
  private final ApplicationEventPublisher applicationEventPublisher;

  @Setter(value = PACKAGE)
  private volatile int nextStop;

  @EventListener
  public void handleCallButtonActivated(CallButtonActivatedEvent event) {
    buttonActivatedHandler(event.getFloor());
  }

  @EventListener
  public void handleCarButtonActivated(CarButtonActivatedEvent event) {
    buttonActivatedHandler(event.getFloor());
  }

  @EventListener
  public void handleMovingUpEvent(@SuppressWarnings("unused") EngineMovingUpEvent event) {
    cabin.moveUp();
    if (shouldStop())
      handleStop();
  }

  @EventListener
  public void handleMovingDownEvent(@SuppressWarnings("unused") EngineMovingDownEvent event) {
    cabin.moveDown();
    if (shouldStop())
      handleStop();
  }

  private void buttonActivatedHandler(int floor) {
    if (isStoppedAtTheFloor(floor)) {
      log.info("Lift is already stopped at the floor {}", floor);
      applicationEventPublisher.publishEvent(new ButtonDeactivatedEvent(this, floor));
      return;
    }
    movementPlan.addFloor(floor, cabin.getPosition());
    nextMove();
  }

  private void nextMove() {
    nextStop = movementPlan.getNextStop();
    if (shouldStartMoving(cabin))
      startMoving(nextStop);
    else if (shouldStop())
      handleStop();
  }

  private boolean shouldStop() {
    return nextStop == cabin.getPosition().getFloor();
  }

  private void handleStop() {
    log.info("Lift arrived at next stop at {}", nextStop);
    log.info("Stopping engine and cabin");
    engine.stop();
    cabin.stop();
    applicationEventPublisher.publishEvent(new ButtonDeactivatedEvent(this, nextStop));
    movementPlan.removeFirst();
    if(movementPlan.hasNext())
      nextMove();
  }

  private boolean shouldStartMoving(Cabin cabin) {
    return cabin.getPosition() instanceof AtFloor;
  }

  private void startMoving(int nextStop) {
    if (nextStop > cabin.getPosition().getFloor())
      engine.moveUp();
    else if (nextStop < cabin.getPosition().getFloor())
      engine.moveDown();
  }

  private boolean isStoppedAtTheFloor(int floor) {
    return cabin.getPosition() instanceof AtFloor && cabin.getPosition().getFloor() == floor;
  }
}
