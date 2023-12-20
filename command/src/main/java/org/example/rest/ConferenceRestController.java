package org.example.rest;

import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import static org.example.api.Queries.*;
import static org.example.api.Commands.*;
@RestController
@RequestMapping("/command/conference")
@RequiredArgsConstructor
public class ConferenceRestController {
  private final CommandGateway commandGateway;
  private final QueryGateway queryGateway;

  @PostMapping
  public CompletableFuture<String> postConference(@RequestBody AddConferenceCommand command) {
    return commandGateway.send(command);
  }

  @PostMapping("/edition")
  public CompletableFuture<String> postConferenceEdition(@RequestBody AddConferenceEditionCommand command) {
    return commandGateway.send(command);
  }

  @PostMapping("/edition/schedule")
  public CompletableFuture<String> postConferenceEditionSchedule(@RequestBody ScheduleConferenceEditionCommand command) {
    return commandGateway.send(command);
  }

  @GetMapping
  public CompletableFuture<List<String>> getConference() {
    return queryGateway.query(new AllConferenceQuery(), ResponseTypes.multipleInstancesOf(String.class));
  }
}
