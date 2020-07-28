package com.example.lift.event;

import org.springframework.context.ApplicationEvent;

public class MovingDownEvent extends ApplicationEvent {
  public MovingDownEvent(Object source) {
    super(source);
  }
}
