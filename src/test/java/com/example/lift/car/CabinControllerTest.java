package com.example.lift.car;

import com.example.lift.car.api.AboveFloor;
import com.example.lift.car.api.AtFloor;
import com.example.lift.car.api.BelowFloor;
import com.example.lift.car.api.Cabin;
import com.example.lift.car.api.CabinEngine;
import com.example.lift.car.api.MovementPlan;
import com.example.lift.car.api.Position;
import com.example.lift.event.ButtonDeactivatedEvent;
import com.example.lift.event.CallButtonActivatedEvent;
import com.example.lift.event.CarButtonActivatedEvent;
import com.example.lift.event.EngineMovingDownEvent;
import com.example.lift.event.EngineMovingUpEvent;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.isA;

@ExtendWith(MockitoExtension.class)
class CabinControllerTest {

  @Captor
  private ArgumentCaptor<ButtonDeactivatedEvent> eventCaptor;

  @Mock
  private Cabin cabin;

  @Mock
  private CabinEngine engine;

  @Mock
  private MovementPlan movementPlan;

  @Mock
  private ApplicationEventPublisher applicationEventPublisher;

  @InjectMocks
  private CabinController underTest;

  @Test
  void shouldHandleCallButtonActivatedWhileStationary() {
    //given
    Position position = new AtFloor(0);
    when(cabin.getPosition()).thenReturn(position);
    when(movementPlan.getNextStop()).thenReturn(5);

    //when
    underTest.handleCallButtonActivated(new CallButtonActivatedEvent(this, 5));

    //then
    verify(movementPlan, times(1)).addFloor(eq(5), eq(position));
    verify(applicationEventPublisher, never()).publishEvent(isA(ButtonDeactivatedEvent.class));
    verify(engine, times(1)).moveUp();
  }

  @Test
  void shouldHandleCallButtonActivatedWhileAtTheSameFloor() {
    //given
    Position position = new AtFloor(0);
    when(cabin.getPosition()).thenReturn(position);

    //when
    underTest.handleCallButtonActivated(new CallButtonActivatedEvent(this, 0));

    //then
    verify(movementPlan, never()).addFloor(any(Integer.class), any(Position.class));
    verify(applicationEventPublisher, times(1)).publishEvent(isA(ButtonDeactivatedEvent.class));
    verify(engine, never()).moveUp();
  }

  @Test
  void shouldHandleCallButtonActivatedWhileMovingUp() {
    //given
    Position position = new BelowFloor(5);
    when(cabin.getPosition()).thenReturn(position);
    when(movementPlan.getNextStop()).thenReturn(7);

    //when
    underTest.handleCallButtonActivated(new CallButtonActivatedEvent(this, 6));

    //then
    verify(movementPlan, times(1)).addFloor(eq(6), eq(position));
    verify(applicationEventPublisher, never()).publishEvent(isA(ButtonDeactivatedEvent.class));
    verify(engine, never()).moveUp();
  }

  @Test
  void shouldHandleCarButtonActivatedWhileStationary() {
    //given
    Position position = new AtFloor(0);
    when(cabin.getPosition()).thenReturn(position);
    when(movementPlan.getNextStop()).thenReturn(5);

    //when
    underTest.handleCarButtonActivated(new CarButtonActivatedEvent(this, 5));

    //then
    verify(movementPlan, times(1)).addFloor(eq(5), eq(position));
    verify(applicationEventPublisher, never()).publishEvent(isA(ButtonDeactivatedEvent.class));
    verify(engine, times(1)).moveUp();
  }

  @Test
  void shouldHandleCarButtonActivatedWhileAtTheSameFloor() {
    //given
    Position position = new AtFloor(0);
    when(cabin.getPosition()).thenReturn(position);

    //when
    underTest.handleCarButtonActivated(new CarButtonActivatedEvent(this, 0));

    //then
    verify(movementPlan, never()).addFloor(any(Integer.class), any(Position.class));
    verify(applicationEventPublisher, times(1)).publishEvent(isA(ButtonDeactivatedEvent.class));
    verify(engine, never()).moveUp();
  }

  @Test
  void shouldHandleCarButtonActivatedWhileMovingUp() {
    //given
    Position position = new BelowFloor(6);
    when(cabin.getPosition()).thenReturn(position, position, position, new AtFloor(6));
    when(movementPlan.getNextStop()).thenReturn(6, 7);
    when(movementPlan.hasNext()).thenReturn(true, false);
    underTest.setNextStop(7);

    //when
    underTest.handleCarButtonActivated(new CarButtonActivatedEvent(this, 6));

    //then
    verify(movementPlan, times(1)).addFloor(eq(6), eq(position));
    verify(applicationEventPublisher, times(1)).publishEvent(isA(ButtonDeactivatedEvent.class));
    verify(cabin, times(1)).stop();
    verify(engine, times(1)).stop();
    verify(engine, times(1)).moveUp();
  }

  @Test
  void shouldHandleMovingUpWithoutStopping() {
    //given
    Position position = new BelowFloor(5);
    when(cabin.getPosition()).thenReturn(position);
    underTest.setNextStop(6);

    //when
    underTest.handleMovingUpEvent(new EngineMovingUpEvent(this));

    //then
    verify(cabin, times(1)).moveUp();
    verify(engine, never()).stop();
    verify(cabin, never()).stop();
    verify(applicationEventPublisher, never()).publishEvent(isA(ButtonDeactivatedEvent.class));
    verify(movementPlan, never()).removeFirst();
    verify(movementPlan, never()).getNextStop();
  }

  @Test
  void shouldHandleMovingUpAndStopping() {
    //given
    Position position = new BelowFloor(5);
    when(cabin.getPosition()).thenReturn(position);
    when(movementPlan.hasNext()).thenReturn(true);
    underTest.setNextStop(5);

    //when
    underTest.handleMovingUpEvent(new EngineMovingUpEvent(this));

    //then
    verify(cabin, times(1)).moveUp();
    verify(engine, times(1)).stop();
    verify(cabin, times(1)).stop();
    verify(applicationEventPublisher, times(1)).publishEvent(eventCaptor.capture());
    assertEquals(5, eventCaptor.getAllValues().get(0).getFloor());
    verify(movementPlan, times(1)).removeFirst();
    verify(movementPlan, times(1)).getNextStop();
  }

  @Test
  void shouldHandleMovingDownWithoutStopping() {
    //given
    Position position = new AboveFloor(6);
    when(cabin.getPosition()).thenReturn(position);
    underTest.setNextStop(3);

    //when
    underTest.handleMovingDownEvent(new EngineMovingDownEvent(this));

    //then
    verify(cabin, times(1)).moveDown();
    verify(engine, never()).stop();
    verify(cabin, never()).stop();
    verify(applicationEventPublisher, never()).publishEvent(isA(ButtonDeactivatedEvent.class));

    verify(movementPlan, never()).removeFirst();
    verify(movementPlan, never()).getNextStop();
  }

  @Test
  void shouldHandleMovingDownAndStopping() {
    //given
    Position position = new AboveFloor(3);
    when(cabin.getPosition()).thenReturn(position);
    when(movementPlan.hasNext()).thenReturn(true);
    underTest.setNextStop(3);

    //when
    underTest.handleMovingDownEvent(new EngineMovingDownEvent(this));

    //then
    verify(cabin, times(1)).moveDown();
    verify(engine, times(1)).stop();
    verify(cabin, times(1)).stop();
    verify(applicationEventPublisher, times(1)).publishEvent(eventCaptor.capture());
    assertEquals(3, eventCaptor.getAllValues().get(0).getFloor());
    verify(movementPlan, times(1)).removeFirst();
    verify(movementPlan, times(1)).getNextStop();
  }
}
