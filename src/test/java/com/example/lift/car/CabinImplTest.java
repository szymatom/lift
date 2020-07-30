package com.example.lift.car;

import com.example.lift.car.api.AboveFloor;
import com.example.lift.car.api.AtFloor;
import com.example.lift.car.api.BelowFloor;
import com.example.lift.car.api.Cabin;

import org.junit.jupiter.api.Test;

import static com.example.lift.common.Movement.*;
import static org.assertj.core.api.Assertions.assertThat;

class CabinImplTest {

  @Test
  void shouldMoveUp() {
    //given
    final Cabin underTest = new CabinImpl(NONE, new AtFloor(0));

    //when
    underTest.moveUp();

    //then
    assertThat(underTest.getPosition()).isEqualTo(new BelowFloor(1));
    assertThat(underTest.getMovement()).isEqualTo(UP);
  }

  @Test
  void shouldMoveDown() {
    //given
    final Cabin underTest = new CabinImpl(NONE, new AtFloor(4));

    //when
    underTest.moveDown();

    //then
    assertThat(underTest.getPosition()).isEqualTo(new AboveFloor(3));
    assertThat(underTest.getMovement()).isEqualTo(DOWN);
  }

  @Test
  void shouldStopWhileMovingUp() {
    //given
    final Cabin underTest = new CabinImpl(UP, new BelowFloor(4));

    //when
    underTest.stop();

    //then
    assertThat(underTest.getPosition()).isEqualTo(new AtFloor(4));
    assertThat(underTest.getMovement()).isEqualTo(NONE);
  }

  @Test
  void shouldStopWhileMovingDown() {
    //given
    final Cabin underTest = new CabinImpl(DOWN, new AboveFloor(4));

    //when
    underTest.stop();

    //then
    assertThat(underTest.getPosition()).isEqualTo(new AtFloor(4));
    assertThat(underTest.getMovement()).isEqualTo(NONE);
  }
}
