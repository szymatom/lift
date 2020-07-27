package com.example.lift.event;

import com.example.lift.common.Direction;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class CallButtonPressedEvent extends ApplicationEvent {

 private final int floor;
 private final Direction direction;

 public CallButtonPressedEvent(Object source, int floor, Direction direction) {
   super(source);
   this.floor = floor;
   this.direction = direction;
 }
}
