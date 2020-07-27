package com.example.lift.buttonpanel;

import com.example.lift.common.Direction;
import com.example.lift.event.CallButtonActivatedEvent;
import com.example.lift.event.CallButtonPressedEvent;
import com.example.lift.event.CarButtonActivatedEvent;
import com.example.lift.event.CarButtonPressedEvent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PanelController {

  private final CarButtonsPanel carButtonsPanel;
  private final CallButtonsPanel callButtonsPanel;
  private final ApplicationEventPublisher applicationEventPublisher;

  public PanelController(CarButtonsPanel carButtonsPanel,
                         CallButtonsPanel callButtonsPanel,
                         ApplicationEventPublisher applicationEventPublisher) {
    this.carButtonsPanel = carButtonsPanel;
    this.callButtonsPanel = callButtonsPanel;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @EventListener
  public void handleCallButtonPressedEvent(CallButtonPressedEvent event) {
    final int floor = event.getFloor();
    final Direction direction = event.getDirection();

    if (callButtonsPanel.isInactive(floor, direction)) {
      log.info("Activating call button, floor {}, direction {}", floor, direction);
      callButtonsPanel.activate(floor, direction);
      applicationEventPublisher.publishEvent(new CallButtonActivatedEvent(this, floor, direction));
    } else {
      log.info("Call button already active, floor {}, direction {}", floor, direction);
    }
  }

  @EventListener
  public void handleCarButtonPressedEvent(CarButtonPressedEvent event) {
    final int floor = event.getFloor();

    if (carButtonsPanel.isInactive(floor)) {
      log.info("Activating car button, floor {}", floor);
      carButtonsPanel.activate(floor);
      applicationEventPublisher.publishEvent(new CarButtonActivatedEvent(this, floor));
    } else {
      log.info("Car button already active, floor {}", floor);
    }
  }
}
