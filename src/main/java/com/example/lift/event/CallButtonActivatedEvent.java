package com.example.lift.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class CallButtonActivatedEvent extends ApplicationEvent {

  private final int floor;

  public CallButtonActivatedEvent(Object source, int floor) {
    super(source);
    this.floor = floor;
  }
}
