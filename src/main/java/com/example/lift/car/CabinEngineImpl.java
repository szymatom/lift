package com.example.lift.car;

import com.example.lift.car.api.CabinEngine;
import com.example.lift.common.Movement;
import com.example.lift.event.EngineMovingDownEvent;
import com.example.lift.event.EngineMovingUpEvent;

import org.springframework.context.ApplicationEventPublisher;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.lift.common.Movement.UP;
import static java.util.Objects.nonNull;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CabinEngineImpl implements CabinEngine {

  private final ApplicationEventPublisher applicationEventPublisher;
  private final int speed;
  private Timer timer;

  @Override
  public void move(Movement direction) {
    if(nonNull(timer))
      throw new AttemptToStartAlreadyRunningEngineException();

    timer = new Timer();
    timer.scheduleAtFixedRate(getTask(direction), 0, speed);
  }

  private TimerTask getTask(Movement direction) {
    return UP.equals(direction) ?
        new MoveUpTimer():
        new MoveDownTimer();
  }

  @Override
  public void stop() {
    timer.cancel();
    timer = null;
    log.info("Engine was stopped");
  }

  private class MoveUpTimer extends TimerTask {
    @Override
    public void run() {
      log.info("Engine moving up");
      applicationEventPublisher.publishEvent(new EngineMovingUpEvent(this));
    }
  }

  private class MoveDownTimer extends TimerTask {
    @Override
    public void run() {
      log.info("Engine moving down");
      applicationEventPublisher.publishEvent(new EngineMovingDownEvent(this));
    }
  }

  private static class AttemptToStartAlreadyRunningEngineException extends RuntimeException{}
}
