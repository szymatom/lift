package com.example.lift.car;

import com.example.lift.car.api.AboveFloor;
import com.example.lift.car.api.AtFloor;
import com.example.lift.car.api.BelowFloor;
import com.example.lift.car.api.MovementPlan;
import com.example.lift.car.api.Position;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MovementPlanImplTest {

  @Test
  void shouldAddOneFloorToEmptyPlan() {
    //given
    final MovementPlan movementPlan = new MovementPlanImpl(new LinkedList<>());

    //when
    movementPlan.addFloor(4, new AtFloor(0));

    //then
    assertEquals(4, movementPlan.getNextStop());
  }

  @Test
  void shouldNotAddStopIfAlreadyInPlan() {
    //given
    final LinkedList<Integer> plan = new LinkedList<>();
    final MovementPlan movementPlan = new MovementPlanImpl(plan);

    //when
    movementPlan.addFloor(4, new AtFloor(0));
    movementPlan.addFloor(4, new AtFloor(0));

    //then
    assertThat(plan).hasSize(1);
  }

  @Test
  void shouldRemoveFirstStop() {
    //given
    final LinkedList<Integer> plan = new LinkedList<>();
    plan.add(2);
    final MovementPlan movementPlan = new MovementPlanImpl(plan);

    //then
    assertThat(movementPlan.removeFirst()).isEqualTo(2);
    assertThat(plan).isEmpty();
  }


  @ParameterizedTest(name = "{index} => expected order of stops in queue should be ''{3}'', ''{4}'' given position ''{0}'', "
      + "current next stop at ''{1}'' and requested stop at ''{2}'' ")
  @MethodSource("twoStops")
  void shouldAddSecondStopToPlan(Position cabinPosition,
                                 int currentFirstStop,
                                 int requestedStop,
                                 int expectedFirstStop,
                                 int expectedSecondStop){
    //given
    final LinkedList<Integer> plan = new LinkedList<>();
    plan.add(currentFirstStop);
    final MovementPlan movementPlan = new MovementPlanImpl(plan);

    //when
    movementPlan.addFloor(requestedStop, cabinPosition);

    //then
    assertThat(plan).containsExactly(expectedFirstStop, expectedSecondStop);
  }

  private static Stream<Arguments> twoStops() {
    return Stream.of(
        Arguments.of(new AtFloor(0), 4, 2, 2, 4),
        Arguments.of(new BelowFloor(1), 4, 1, 1, 4),
        Arguments.of(new BelowFloor(1), 1, 4, 1, 4),
        Arguments.of(new BelowFloor(5), 7, 3, 7, 3),
        Arguments.of(new AboveFloor(5), 2, 4, 4, 2)
    );
  }

  @ParameterizedTest(name = "{index} => expected order of stops in queue should be ''{3}'',  given position ''{0}'', "
      + "current stops at ''{1}'' and requested stop at ''{2}'' ")
  @MethodSource("multipleStops")
  void shouldAddStopToPlanContainingMultipleStops(Position cabinPosition,
                                 List<Integer> currentStops,
                                 int requestedStop,
                                 List<Integer> expectedStops){
    //given
    final LinkedList<Integer> plan = new LinkedList<>(currentStops);
    final MovementPlan movementPlan = new MovementPlanImpl(plan);

    //when
    movementPlan.addFloor(requestedStop, cabinPosition);

    //then
    assertThat(plan).containsExactly(expectedStops.toArray(new Integer[0]));
  }

  private static Stream<Arguments> multipleStops() {
    return Stream.of(
        Arguments.of(new AtFloor(0), asList(2, 4), 1, asList(1, 2, 4)),
        Arguments.of(new AboveFloor(0), asList(2, 4), 3, asList(2, 3, 4)),
        Arguments.of(new AtFloor(10), asList(8, 5, 3), 4, asList(8, 5, 4, 3)),
        Arguments.of(new AboveFloor(9), asList(8, 5, 3), 2, asList(8, 5, 3, 2)),
        Arguments.of(new BelowFloor(8), asList(8, 5), 6, asList(8, 6, 5)),
        Arguments.of(new AboveFloor(4), asList(3, 6), 4, asList(4, 3, 6)),
        Arguments.of(new AtFloor(7), asList(9, 4), 0, asList(9, 4, 0)),
        Arguments.of(new AtFloor(1), asList(2, 5, 7, 9, 0), 4, asList(2, 4, 5, 7, 9, 0))
    );
  }
}
