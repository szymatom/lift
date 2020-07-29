package com.example.lift.rest;

import com.example.lift.event.CallButtonPressedEvent;
import com.example.lift.event.CarButtonPressedEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.context.ApplicationEventPublisher;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

class ButtonPushEndpointTest {

  @Captor
  private ArgumentCaptor<CarButtonPressedEvent> carEventCaptor;

  @Captor
  private ArgumentCaptor<CallButtonPressedEvent> callEventCaptor;

  private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
  ButtonPushEndpoint buttonPushEndpoint = new ButtonPushEndpoint(applicationEventPublisher, new RequestParamsValidator(10));

  @BeforeEach
  void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  void shouldSuccessfullyPressInsideButton() {
    given()
        .standaloneSetup(buttonPushEndpoint)
        .param("floor", "3")
        .when()
        .get("/inside")
        .then()
        .statusCode(200);

    verify(applicationEventPublisher, Mockito.times(1)).publishEvent(carEventCaptor.capture());
    assertEquals(3, carEventCaptor.getAllValues().get(0).getFloor());
  }

  @Test
  void shouldFailWithBadRequestForInside() {
    given()
        .standaloneSetup(buttonPushEndpoint)
        .param("floor", "g")
        .when()
        .get("/inside")
        .then()
        .statusCode(400);

    verify(applicationEventPublisher, never()).publishEvent(any());
  }

  @Test
  void shouldSuccessfullyPressOutsideButton() {
    given()
        .standaloneSetup(buttonPushEndpoint)
        .param("floor", "3")
        .when()
        .get("/outside")
        .then()
        .statusCode(200);

    verify(applicationEventPublisher, Mockito.times(1)).publishEvent(callEventCaptor.capture());
    assertEquals(3, callEventCaptor.getAllValues().get(0).getFloor());
  }

  @Test
  void shouldFailWithBadRequestForOutside() {
    given()
        .standaloneSetup(buttonPushEndpoint)
        .param("floor", "0")
        .when()
        .get("/outside")
        .then()
        .statusCode(400);

    verify(applicationEventPublisher, never()).publishEvent(any());
  }
}
