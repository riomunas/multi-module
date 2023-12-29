package org.example.projection;

import lombok.RequiredArgsConstructor;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import static org.example.api.Events.*;
import static org.example.api.Queries.*;

import java.util.List;

@ProcessingGroup("queryEventHandler")
@RequiredArgsConstructor
@Component
public class ConferenceListProjection {
  private final ConferenceModelRepository repository;

  @EventHandler
  public void on(ConferenceAddedEvent event) {
    repository.save(ConferenceModel.builder()
        .conferenceId(event.conferenceId())
        .website(event.website())
        .name(event.name())

      .build());
  }

  @QueryHandler
  public List<String> on(AllConferenceQuery query) {
    return repository.findAll().stream().map(ConferenceModel::getName).toList();
  }
}
