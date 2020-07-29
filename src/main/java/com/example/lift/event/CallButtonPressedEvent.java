package com.example.lift.event;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class CallButtonPressedEvent extends ApplicationEvent {

 private final int floor;

 public CallButtonPressedEvent(Object source, int floor) {
   super(source);
   this.floor = floor;
 }
}
