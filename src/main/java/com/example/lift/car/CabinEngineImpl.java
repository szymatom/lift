package com.example.lift.car;

import com.example.lift.car.api.CabinEngine;
import com.example.lift.event.EngineMovingDownEvent;
import com.example.lift.event.EngineMovingUpEvent;

import org.springframework.context.ApplicationEventPublisher;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

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
  public void moveUp() {
    if(engineInMotion)
      throw new AttemptToStartAlreadyRunningEngineException();

    future = scheduler.scheduleWithFixedDelay(new MoveUpTimer(), 0, speed, MILLISECONDS);
    engineInMotion = true;
  }

  @Override
  public void moveDown() {
    if(engineInMotion)
      throw new AttemptToStartAlreadyRunningEngineException();

    future = scheduler.scheduleWithFixedDelay(new MoveDownTimer(), 0, speed, MILLISECONDS);
    engineInMotion = true;
  }

  @Override
  public void stop() {
    if(!engineInMotion)
      return;
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
