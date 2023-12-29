package org.example.rest;

import lombok.RequiredArgsConstructor;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.example.api.Queries.AllConferenceQuery;

@RestController
@RequestMapping("/query/conference")
@RequiredArgsConstructor
public class QueryConferenceRestController {
  private final QueryGateway queryGateway;

  @GetMapping
  public CompletableFuture<List<String>> getConference() {
    return queryGateway.query(new AllConferenceQuery(), ResponseTypes.multipleInstancesOf(String.class));
  }
}
