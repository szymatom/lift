package com.example.lift.buttonpanel;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ButtonsPanelTest {
  private final int numberOfFloors = 10;

  @Test
  void shouldActivateButton(){
    //given
    ButtonsPanel underTest = new ButtonsPanel(numberOfFloors);

    //when
    underTest.activate(5);

    //then
    assertThat(IntStream.range(0, numberOfFloors).filter(underTest::isActive).count())
        .isEqualTo(1);
    assertTrue(underTest.isActive(5));

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
    assertThat(IntStream.range(0, numberOfFloors).filter(underTest::isActive).count()).isZero();
  }
}
