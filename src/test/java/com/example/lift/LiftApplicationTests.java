package com.example.lift;

import com.example.lift.buttonpanel.PanelConfig;
import com.example.lift.buttonpanel.PanelController;
import com.example.lift.config.ApplicationConfig;
import com.example.lift.rest.ButtonPushEndpoint;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {ApplicationConfig.class, ButtonPushEndpoint.class, PanelConfig.class, PanelController.class})
class LiftApplicationTests {

  @Test
  void contextLoads() {
  }

}
