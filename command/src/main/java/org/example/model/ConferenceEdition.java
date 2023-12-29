package org.example.model;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.EntityId;

import java.time.LocalDate;

import static org.example.Helper.getAggregatePhaseIcon;
import static org.example.api.Events.*;
import static org.example.api.Commands.*;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@Builder
public class ConferenceEdition {
  @EntityId
  private String editionId;
  private LocalDate startDate;

  @CommandHandler
  public String handle(ScheduleConferenceEditionCommand command) {
    log.info("⬇️ AddConferenceCommand Recieved : {}", command);

    //validate
    if(command.startDate().equals(startDate)) return command.editionId();

    //trigger event
    apply(ConferenceScheduleEditionEvent.builder()
      .conferenceId(command.conferenceId())
      .editionId(command.editionId())
      .startDate(command.startDate())
      .build());

    return command.editionId();
  }

  @EventSourcingHandler
  public void handle(ConferenceScheduleEditionEvent event) {
    log.info("{} Processing ConferenceScheduleEditionEvent", getAggregatePhaseIcon());

    this.startDate = event.startDate();
  }
}
