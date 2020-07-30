package com.example.lift.buttonpanel;

import com.example.lift.event.ButtonDeactivatedEvent;
import com.example.lift.event.CallButtonActivatedEvent;
import com.example.lift.event.CallButtonPressedEvent;
import com.example.lift.event.CarButtonActivatedEvent;
import com.example.lift.event.CarButtonPressedEvent;

import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PanelControllerTest {

  private final int floor = 3;

  private final ButtonsPanel callButtonsPanel = mock(ButtonsPanel.class);
  private final ButtonsPanel carButtonsPanel = mock(ButtonsPanel.class);
  private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
  private final PanelController underTest = new PanelController(carButtonsPanel, callButtonsPanel, applicationEventPublisher);

  @Test
  void shouldHandleCarButtonPushedEventWhileButtonNotActive() {
    //given
    when(carButtonsPanel.isActive(floor)).thenReturn(false);

    //when
    underTest.handleCarButtonPressedEvent(new CarButtonPressedEvent(this, floor));

    //then
    verify(carButtonsPanel, times(1)).activate(floor);
    verify(applicationEventPublisher, times(1)).publishEvent(isA(CarButtonActivatedEvent.class));
  }

  @Test
  void shouldHandleCarButtonPushedEventWhileButtonIsActive() {
    //given
    when(carButtonsPanel.isActive(floor)).thenReturn(true);

    //when
    underTest.handleCarButtonPressedEvent(new CarButtonPressedEvent(this, floor));

    //then
    verify(carButtonsPanel, never()).activate(floor);
    verify(applicationEventPublisher, never()).publishEvent(any());
  }

  @Test
  void shouldHandleCallButtonPushedEventWhileButtonNotActive() {
    //given
    when(callButtonsPanel.isActive(floor)).thenReturn(false);

    //when
    underTest.handleCallButtonPressedEvent(new CallButtonPressedEvent(this, floor));

    //then
    verify(callButtonsPanel, times(1)).activate(floor);
    verify(applicationEventPublisher, times(1)).publishEvent(isA(CallButtonActivatedEvent.class));
  }

  @Test
  void shouldHandleCallButtonPushedEventWhileButtonIsActive() {
    //given
    when(callButtonsPanel.isActive(floor)).thenReturn(true);

    //when
    underTest.handleCallButtonPressedEvent(new CallButtonPressedEvent(this, floor));

    //then
    verify(callButtonsPanel, never()).activate(floor);
    verify(applicationEventPublisher, never()).publishEvent(any());
  }

  @Test
  void shouldHandleDeactivatedButtonEvent() {
    //given
    when(carButtonsPanel.isActive(floor)).thenReturn(true);
    when(callButtonsPanel.isActive(floor)).thenReturn(false);

    //when
    underTest.handleButtonDeactivatedEvent(new ButtonDeactivatedEvent(this, floor));

    //then
    verify(carButtonsPanel, times(1)).deactivate(floor);
    verify(callButtonsPanel, never()).deactivate(floor);
  }
}
