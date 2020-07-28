package com.example.lift.car;

import com.example.lift.car.api.CabinEngine;
import com.example.lift.event.MovingDownEvent;
import com.example.lift.event.MovingUpEvent;

import org.springframework.context.ApplicationEventPublisher;

import java.util.Timer;
import java.util.TimerTask;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class CabinEngineImpl implements CabinEngine {

  private final ApplicationEventPublisher applicationEventPublisher;
  private Timer timer;

  @Override
  public void moveUp() {
    timer = new Timer();
    timer.scheduleAtFixedRate(new MoveUpTimer(), 2000, 4000);
  }

  @Override
  public void moveDown() {
    timer = new Timer();
    timer.scheduleAtFixedRate(new MoveDownTimer(), 2000, 4000);
  }

  @Override
  public void stop() {
    timer.cancel();
    log.info("Cabin was stopped");
  }

  private class MoveUpTimer extends TimerTask {
    @Override
    public void run() {
      log.info("Cabin is moving up");
      applicationEventPublisher.publishEvent(new MovingUpEvent(this));
    }
  }

  private class MoveDownTimer extends TimerTask {
    @Override
    public void run() {
      log.info("Cabin is moving down");
      applicationEventPublisher.publishEvent(new MovingDownEvent(this));
    }
  }
}
