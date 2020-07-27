package com.example.lift.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class CarButtonPressedEvent extends ApplicationEvent {

  private final int floor;

  public CarButtonPressedEvent(Object source, int floor) {
    super(source);
    this.floor = floor;
  }
}
