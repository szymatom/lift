package com.example.lift.event;

import org.springframework.context.ApplicationEvent;

public class EngineMovingDownEvent extends ApplicationEvent {
  public EngineMovingDownEvent(Object source) {
    super(source);
  }
}
