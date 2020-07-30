package com.example.lift.car;

import com.example.lift.car.api.CabinEngine;
import com.example.lift.common.Movement;
import com.example.lift.event.EngineMovingDownEvent;
import com.example.lift.event.EngineMovingUpEvent;

import org.springframework.context.ApplicationEventPublisher;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static com.example.lift.common.Movement.NONE;
import static com.example.lift.common.Movement.UP;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static lombok.AccessLevel.PACKAGE;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CabinEngineImpl implements CabinEngine {

  private final ScheduledExecutorService scheduler;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final int speed;
  private ScheduledFuture<?> future;

  @Setter(value = PACKAGE)
  private boolean engineInMotion;

  @Override
  public void move(Movement direction) {
    if(engineInMotion)
      throw new AttemptToStartAlreadyRunningEngineException();

    if(NONE.equals(direction)) {
      stop();
      return;
    }

    future = scheduler.scheduleWithFixedDelay(getTask(direction), 0, speed, MILLISECONDS);
    engineInMotion = true;
  }

  private Runnable getTask(Movement direction) {
    return UP.equals(direction) ?
        new MoveUpTimer():
        new MoveDownTimer();
  }

  @Override
  public void stop() {
    future.cancel(true);
    engineInMotion = false;
    log.info("Engine was stopped");
  }

  private class MoveUpTimer implements Runnable {
    @Override
    public void run() {
      log.info("Engine moving up");
      applicationEventPublisher.publishEvent(new EngineMovingUpEvent(this));
    }
  }

  private class MoveDownTimer implements Runnable {
    @Override
    public void run() {
      log.info("Engine moving down");
      applicationEventPublisher.publishEvent(new EngineMovingDownEvent(this));
    }
  }

  static class AttemptToStartAlreadyRunningEngineException extends RuntimeException{}
}
