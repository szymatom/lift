package com.example.lift.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class ButtonDeactivatedEvent extends ApplicationEvent {

  private final int floor;

  public ButtonDeactivatedEvent(Object source, int floor) {
    super(source);
    this.floor = floor;
  }
}
