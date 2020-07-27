package com.example.lift.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class CarButtonActivatedEvent extends ApplicationEvent {

  private final int floor;

  public CarButtonActivatedEvent(Object source, int floor) {
    super(source);
    this.floor = floor;
  }
}
