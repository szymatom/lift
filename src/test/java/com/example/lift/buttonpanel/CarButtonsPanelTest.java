package com.example.lift.buttonpanel;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static com.example.lift.common.ButtonState.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CarButtonsPanelTest {

  private final CarButtonsPanel underTest = CarButtonsPanel.getInstance(10);

  @Test
  void shouldBeSingleton() {
    //given
    CarButtonsPanel objectB = CarButtonsPanel.getInstance(10);

    //then
    assertSame(underTest,objectB);
  }

  @Test
  void shouldThrowException() {
    //then
    assertThrows(IllegalArgumentException.class, () -> CarButtonsPanel.getInstance(4));
  }

  @Test
  void shouldActivateButton(){
    //when
    underTest.activate(5);

    //then
    assertThat(IntStream.range(0, 10).filter(underTest::isInactive).count())
        .isEqualTo(9);
    assertEquals(ACTIVE, underTest.getButtonState(5));

    //teardown
    underTest.deactivate(5);
  }

  @Test
  void shouldDeactivateButton(){
    //when
    underTest.activate(5);
    underTest.deactivate(5);

    //then
    assertThat(IntStream.range(0, 10).filter(underTest::isInactive).count())
        .isEqualTo(10);
  }
}
