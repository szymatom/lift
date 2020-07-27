package com.example.lift.buttonpanel;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static com.example.lift.common.ButtonState.*;
import static com.example.lift.common.Direction.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CallButtonsPanelTest {
  private final int numberOfFloors = 10;
  private final CallButtonsPanel underTest = CallButtonsPanel.getInstance(numberOfFloors);

  @Test
  void shouldBeSingleton() {
    //given
    CallButtonsPanel objectB = CallButtonsPanel.getInstance(numberOfFloors);

    //then
    assertSame(underTest,objectB);
  }

  @Test
  void shouldThrowException() {
    //then
    assertThrows(IllegalArgumentException.class, () -> CallButtonsPanel.getInstance(4));
  }

  @Test
  void shouldActivateButton(){
    //when
    underTest.activate(5, UP);

    //then
    assertThat(
        IntStream.range(0, numberOfFloors).filter(this::floorButtonsInactive).count())
        .isEqualTo(numberOfFloors - 1);
    assertEquals(ACTIVE, underTest.getButtonState(5, UP));

    //teardown
    underTest.deactivate(5, UP);
  }

  @Test
  void shouldDeactivateButton(){
    //given
    CarButtonsPanel panel = CarButtonsPanel.getInstance(10);

    //when
    panel.activate(5);
    panel.deactivate(5);

    //then
    assertThat(IntStream.range(0, numberOfFloors).filter(this::floorButtonsInactive).count())
        .isEqualTo(numberOfFloors);
  }

  private boolean floorButtonsInactive(int floor) {
    if(floor == 0)
      return underTest.isInactive(floor, UP);
    else if(floor == numberOfFloors - 1)
      return underTest.isInactive(floor, DOWN);

    return underTest.isInactive(floor, DOWN) && underTest.isInactive(floor, UP);
  }
}
