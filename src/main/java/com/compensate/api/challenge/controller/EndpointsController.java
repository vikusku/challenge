package com.compensate.api.challenge.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import io.swagger.v3.oas.annotations.Hidden;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Hidden
@RestController
@RequestMapping(path = "/api", produces = "application/json")
public class EndpointsController {

  @Autowired
  private RequestMappingHandlerMapping requestMappingHandlerMapping;

  @GetMapping(name = "api_entry_point")
  public ResponseEntity<String> getEndpoints(HttpServletRequest req) {
    final ObjectMapper mapper = new ObjectMapper();

    List<String> endpoints = requestMappingHandlerMapping
        .getHandlerMethods()
        .keySet()
        .stream()
        .filter(m -> !Strings.isNullOrEmpty(m.getName()) && m.getPatternsCondition() != null)
        .map(m -> {
          final String endpoint = String.format("%s://%s:%d%s%s", req.getScheme(),
              req.getServerName(), req.getServerPort(), req.getContextPath(),
              m.getPatternsCondition().getPatterns().toArray()[0]);

          return String.format("%s : { %s %s }",
              m.getName(),
              m.getMethodsCondition().getMethods().toArray()[0],
              endpoint);
        })
        .collect(Collectors.toList());

    try {
      return ResponseEntity.ok(mapper.writeValueAsString(endpoints));
    } catch (JsonProcessingException e) {
      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }
  }
}
