package com.example.lift.buttonpanel;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static com.example.lift.common.ButtonState.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ButtonsPanelTest {
  private final int numberOfFloors = 10;

  @Test
  void shouldActivateButton(){
    //given
    ButtonsPanel underTest = new ButtonsPanel(numberOfFloors);

    //when
    underTest.activate(5);

    //then
    assertThat(IntStream.range(0, numberOfFloors).filter(underTest::isInactive).count())
        .isEqualTo(numberOfFloors - 1);
    assertEquals(ACTIVE, underTest.getButtonState(5));

    //teardown
    underTest.deactivate(5);
  }

  @Test
  void shouldDeactivateButton(){
    //given
    ButtonsPanel underTest = new ButtonsPanel(numberOfFloors);

    //when
    underTest.activate(5);
    underTest.deactivate(5);

    //then
    assertThat(IntStream.range(0, 10).filter(underTest::isInactive).count())
        .isEqualTo(numberOfFloors);
  }
}
