package com.example.lift.event;

import org.springframework.context.ApplicationEvent;

public class MovingUpEvent extends ApplicationEvent {
  public MovingUpEvent(Object source) {
    super(source);
  }
}
