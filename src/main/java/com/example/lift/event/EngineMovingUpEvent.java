package com.example.lift.event;

import org.springframework.context.ApplicationEvent;

public class EngineMovingUpEvent extends ApplicationEvent {

  public EngineMovingUpEvent(Object source) {
    super(source);
  }
}
