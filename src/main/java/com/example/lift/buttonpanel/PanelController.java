package com.example.lift.buttonpanel;

import com.example.lift.event.ButtonDeactivatedEvent;
import com.example.lift.event.CallButtonPressedEvent;
import com.example.lift.event.CarButtonActivatedEvent;
import com.example.lift.event.CarButtonPressedEvent;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PanelController {

  private final ButtonsPanel carButtonsPanel;
  private final ButtonsPanel callButtonsPanel;
  private final ApplicationEventPublisher applicationEventPublisher;

  public PanelController(ButtonsPanel carButtonsPanel,
                         ButtonsPanel callButtonsPanel,
                         ApplicationEventPublisher applicationEventPublisher) {
    this.carButtonsPanel = carButtonsPanel;
    this.callButtonsPanel = callButtonsPanel;
    this.applicationEventPublisher = applicationEventPublisher;
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

  @EventListener
  public void handleCallButtonPressedEvent(CallButtonPressedEvent event) {
    final int floor = event.getFloor();

    if (callButtonsPanel.isInactive(floor)) {
      log.info("Activating call button, floor {}", floor);
      callButtonsPanel.activate(floor);
      applicationEventPublisher.publishEvent(new CarButtonActivatedEvent(this, floor));
    } else {
      log.info("Call button already active, floor {}", floor);
    }
  }

  @EventListener
  public void handleButtonDeactivatedEvent(ButtonDeactivatedEvent event) {
    final int floor = event.getFloor();
    deactivate(callButtonsPanel, floor, "call");
    deactivate(carButtonsPanel, floor, "car");
  }

  private void deactivate(ButtonsPanel panel, int floor, String description) {
    if(panel.isActive(floor)) {
      log.info("Deactivating {} button, floor {}", description, floor);
      panel.deactivate(floor);
    }
  }
}
