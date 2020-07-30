package com.example.lift;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class LiftApplicationTests {

  @Autowired
  ApplicationContext context;

  @Test
  void contextLoads() {
    assertTrue(Stream.of(
        "panelController",
        "requestParamsValidator",
        "cabin",
        "engine",
        "movementPlan",
        "cabinController",
        "carButtonsPanel",
        "callButtonsPanel")
        .allMatch(context::containsBean));
  }
}
