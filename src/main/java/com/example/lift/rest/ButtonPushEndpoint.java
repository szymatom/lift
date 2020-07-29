package com.example.lift.rest;

import com.example.lift.event.CarButtonPressedEvent;
import com.example.lift.event.CallButtonPressedEvent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import static java.lang.Integer.parseInt;

@RestController
@RequiredArgsConstructor
public class ButtonPushEndpoint {

  final ApplicationEventPublisher applicationEventPublisher;
  final RequestParamsValidator requestParamsValidator;

  @GetMapping("/outside")
  public ResponseEntity<String> pushDirectionButton(@RequestParam(name = "floor", defaultValue = "0") String floor) {

    if(!requestParamsValidator.isValidFloor(floor))
      return ResponseEntity.badRequest().build();

    applicationEventPublisher.publishEvent(new CallButtonPressedEvent(this, parseInt(floor)));
    return ResponseEntity.ok(String.format("Call button pressed: floor %s", floor));
  }

  @GetMapping("/inside")
  public ResponseEntity<String> pushFloorNumberButton(@RequestParam(name = "floor", defaultValue = "0") String floor) {

    if(!requestParamsValidator.isValidFloor(floor))
      return ResponseEntity.badRequest().build();

    applicationEventPublisher.publishEvent(new CarButtonPressedEvent(this, parseInt(floor)));
    return ResponseEntity.ok(String.format("Elevator car floor button pressed: %s", floor));
  }
}
