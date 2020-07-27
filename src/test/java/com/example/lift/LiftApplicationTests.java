package com.example.lift;

import com.example.lift.config.ApplicationConfig;
import com.example.lift.rest.ButtonPushEndpoint;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {ApplicationConfig.class, ButtonPushEndpoint.class})
class LiftApplicationTests {

  @Test
  void contextLoads() {
  }

}
