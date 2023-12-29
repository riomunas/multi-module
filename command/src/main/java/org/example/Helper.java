package org.example;

import lombok.Builder;
import org.example.api.Events;

import static org.axonframework.modelling.command.AggregateLifecycle.isLive;

public class Helper {


  public static String getAggregatePhaseIcon() {
    return "[event-sourcing] "+ (isLive() ? "✉️" : "⚙️");
  }

}
