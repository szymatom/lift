package com.example.lift.event;

import com.example.lift.common.Direction;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class CallButtonActivatedEvent extends ApplicationEvent {

  private final int floor;
  private final Direction direction;

  public CallButtonActivatedEvent(Object source, int floor, Direction direction) {
    super(source);
    this.floor = floor;
    this.direction = direction;
  }
}
