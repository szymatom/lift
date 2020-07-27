package com.example.lift.config;

import com.example.lift.buttonpanel.CallButtonsPanel;
import com.example.lift.buttonpanel.CarButtonsPanel;
import com.example.lift.buttonpanel.PanelController;
import com.example.lift.rest.RequestParamsValidator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.AbstractRequestLoggingFilter;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import static java.util.Objects.nonNull;

@Configuration
@Slf4j
public class ApplicationConfig {

  @Value("${numberOfFloors:10}")
  int numberOfFloors;

  @Bean
  public RequestParamsValidator requestParamsValidator() {
    return new RequestParamsValidator(numberOfFloors);
  }

  @Bean
  public AbstractRequestLoggingFilter requestLoggingFilter() {
    return new AbstractRequestLoggingFilter() {

      @Override
      protected void beforeRequest(HttpServletRequest request, String message) {
        log.info("Received request {}{}",
            request.getRequestURL(),
            nonNull(request.getQueryString()) ? "?" + request.getQueryString() : "");
      }

      @Override
      protected void afterRequest(HttpServletRequest request, String message) {
        //intentionally left empty
      }
    };
  }
}
