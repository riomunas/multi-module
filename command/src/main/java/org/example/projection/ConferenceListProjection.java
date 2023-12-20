package org.example.projection;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;
import static org.example.api.Events.*;
import static org.example.api.Queries.*;

import java.util.ArrayList;
import java.util.List;

@ProcessingGroup("eventProcessor")
@Component
public class ConferenceListProjection {
  private List<String> conferences = new ArrayList<>();

  @EventHandler
  public void on(ConferenceAddedEvent event) {
    conferences.add(event.name());
  }

  @QueryHandler
  public List<String> on(AllConferenceQuery query) {
    return conferences;
  }
}
