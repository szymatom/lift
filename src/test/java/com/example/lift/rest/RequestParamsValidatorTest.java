package com.example.lift.rest;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class RequestParamsValidatorTest {

  RequestParamsValidator underTest = new RequestParamsValidator(3);

  @ParameterizedTest(name = "{index} => Validation result for floor ''{0}'' should be ''{1}''")
  @MethodSource("floor")
  void shouldValidateFloor(String floor, boolean expectedResult) {
    //then
    assertThat(underTest.isValidFloor(floor)).isEqualTo(expectedResult);
  }

  private static Stream<Arguments> floor() {
    return Stream.of(
        Arguments.of("0", true),
        Arguments.of("1", true),
        Arguments.of("2", true),
        Arguments.of("3", false),
        Arguments.of("non_integer", false),
        Arguments.of("-1", false)
    );
  }
}
