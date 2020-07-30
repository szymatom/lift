package com.example.lift.car;

import com.example.lift.car.api.CabinEngine;
import com.example.lift.event.EngineMovingDownEvent;
import com.example.lift.event.EngineMovingUpEvent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.Executors.newScheduledThreadPool;
import static com.example.lift.common.Movement.*;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.timeout;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class CabinEngineImplTest {

  private final ScheduledExecutorService scheduler = mock(ScheduledExecutorService.class);
  private final ApplicationEventPublisher applicationEventPublisher = mock(ApplicationEventPublisher.class);
  private final ScheduledFuture<?> future = mock(ScheduledFuture.class);
  private final int speed = 1000;

  @InjectMocks
  private final CabinEngine underTest = new CabinEngineImpl(scheduler, applicationEventPublisher, speed);

  @BeforeEach
  void setUp() {
    ((CabinEngineImpl) underTest).setEngineInMotion(false);
  }

  @Test
  void shouldThrowException() {
    //given
    ((CabinEngineImpl) underTest).setEngineInMotion(true);

    //then
    assertThrows(CabinEngineImpl.AttemptToStartAlreadyRunningEngineException.class, () -> underTest.move(UP));
  }

  @Test
  void shouldStartMoving() {
    //when
    underTest.move(UP);

    //then
    verify(scheduler, times(1)).scheduleWithFixedDelay(isA(Runnable.class), eq(0L), eq(Long.valueOf(speed)), eq(MILLISECONDS));
  }

  @Test
  void shouldStopMoving() {
    //when
    underTest.stop();

    //then
    verify(future, times(1)).cancel(eq(true));
  }

  @Test
  void shouldStopMovingIfDirectionIsNone() {
    //when
    underTest.move(NONE);

    //then
    verify(future, times(1)).cancel(eq(true));
  }

  @Test
  void shouldNotifyOfMovingUp() {
    //given
    final CabinEngine cabinEngine = new CabinEngineImpl(newScheduledThreadPool(1), applicationEventPublisher, speed);

    //when
    cabinEngine.move(UP);

    //then
    verify(applicationEventPublisher, timeout(speed)).publishEvent(isA(EngineMovingUpEvent.class));
  }

  @Test
  void shouldNotifyOfMovingDown() {
    //given
    final CabinEngine cabinEngine = new CabinEngineImpl(newScheduledThreadPool(1), applicationEventPublisher, speed);

    //when
    cabinEngine.move(DOWN);

    //then
    verify(applicationEventPublisher, timeout(speed)).publishEvent(isA(EngineMovingDownEvent.class));
  }
}
