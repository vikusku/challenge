package com.compensate.api.challenge.controller;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api", produces = "application/json")
public class EndpointsController {
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @GetMapping(name = "api_entry_point")
    public ResponseEntity<String> getEndpoints(HttpServletRequest req) {

        List<String> endpoints = requestMappingHandlerMapping
                .getHandlerMethods()
                .keySet()
                .stream()
                .filter(m -> m.getPatternsCondition() != null)
                .map(m -> {
                    final String endpoint = String.format("%s://%s:%d%s%s", req.getScheme(),
                            req.getServerName(), req.getServerPort(), req.getContextPath(), m.getPatternsCondition().getPatterns().toArray()[0]);

                    return String.format("%s : { %s %s }",
                            m.getName(),
                            m.getMethodsCondition().getMethods().toArray()[0],
                            endpoint);
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(new Gson().toJson(endpoints));
    }
}
