package org.example.model;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.modelling.command.ForwardMatchingInstances;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.Helper;
import org.example.api.Commands;
import org.example.api.Events;

import java.util.*;

import static org.example.Helper.getAggregatePhaseIcon;
import static org.example.api.Events.*;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@Slf4j
@NoArgsConstructor
@Aggregate
public class Conference {
  @AggregateIdentifier
  private String conferenceId;
  private String ownerEmail;

  @AggregateMember(eventForwardingMode = ForwardMatchingInstances.class)
  private final Map<String, ConferenceEdition> editions = new HashMap<>();

/*
  //alternatif way
  @AggregateMember
  List<ConferenceEdition> editions = new ArrayList<>();


  @EventSourcingHandler
  public void on(ConferenceEditionAddedEvent event) {
    this.editions.add(new ConferenceEdition(event.editionId(), event.startDate()));
  }
*/


  @CommandHandler
  public Conference(Commands.AddConferenceCommand command) {
    log.info("⬇️ AddConferenceCommand Recieved : {}", command);
    //validate

    //apply an event
    apply(ConferenceAddedEvent.builder()
      .conferenceId(UUID.randomUUID().toString())
      .name(command.name())
      .website(command.website())
      .location(Optional.ofNullable(command.location())
        .map(location -> ConferenceLocation.builder()
        .country(location.country())
        .city(location.city())
        .isOnline(location.isOnline())
        .build()).orElse(null))
      .family(command.family())
      .ownerEmail(command.ownerEmail())
      .build());
  }

  @CommandHandler
  public String handle(Commands.AddConferenceEditionCommand command) {
    log.info("⬇️ AddConferenceEditionCommand Recieved : {}", command);
    //validation
    if (!command.ownerEmail().equalsIgnoreCase(this.ownerEmail)) {
      log.info("Invalid owner email 🫢");
      throw new IllegalArgumentException("Invalid owner email ");
    }

    //apply an event
    String editionId = UUID.randomUUID().toString().split("-")[0];
    apply(ConferenceEditionAddedEvent.builder()
      .conferenceId(conferenceId)
      .editionId(editionId)
      .startDate(command.startDate())
      .venue(command.venue() == null ? null : Events.VenueLocation.builder()
        .name(command.venue().name())
        .address(command.venue().address())
        .build())
      .build());

    return editionId;
  }


  @EventSourcingHandler
  public void handle(ConferenceAddedEvent event) {
    log.info("{} Processing ConferenceAddedEvent", getAggregatePhaseIcon());

    this.conferenceId = event.conferenceId();
    this.ownerEmail = event.ownerEmail();
  }

  @EventSourcingHandler
  public void handle(ConferenceEditionAddedEvent event) {
    log.info("{} Processing ConferenceEditionAddedEvent", getAggregatePhaseIcon());
    this.editions.put(event.editionId(), new ConferenceEdition(event.editionId(), event.startDate()));
  }
}
