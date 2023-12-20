package org.example.model;

import lombok.NoArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateMember;
import org.axonframework.modelling.command.ForwardMatchingInstances;
import org.axonframework.spring.stereotype.Aggregate;
import org.example.api.Events;

import java.util.*;

import static org.example.api.Commands.*;
import static org.example.api.Events.*;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@NoArgsConstructor
@Aggregate
public class Conference {
  @AggregateIdentifier
  private String conferenceId;

  @AggregateMember(eventForwardingMode = ForwardMatchingInstances.class)
  private final Map<String, ConferenceEdition> editions = new HashMap<>();
//  @AggregateMember
//  List<ConferenceEdition> editions = new ArrayList<>();

  @CommandHandler
  public Conference(AddConferenceCommand command) {
    apply(ConferenceAddedEvent.builder()
      .conferenceId(UUID.randomUUID().toString())
      .name(command.name())
      .website(command.website())
      .location(Events.ConferenceLocation.builder()
        .country(command.location().country())
        .city(command.location().city())
        .isOnline(command.location().isOnline())
        .build())
      .family(command.family())
      .build());
  }

  @CommandHandler
  public String on(AddConferenceEditionCommand command) {
    //validation

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
  public void on(ConferenceAddedEvent event) {
    this.conferenceId = event.conferenceId();
  }


  @EventSourcingHandler
  public void on(ConferenceEditionAddedEvent event) {
    this.editions.put(event.editionId(), new ConferenceEdition(event.editionId(), event.startDate()));
  }

//  @EventSourcingHandler
//  public void on(ConferenceEditionAddedEvent event) {
//    this.editions.add(new ConferenceEdition(event.editionId(), event.startDate()));
//  }

}
