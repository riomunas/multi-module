package org.example.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.time.LocalDate;

public class Commands {
  @Builder
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record AddConferenceCommand(
    String name,
    String website,
    ConferenceLocation location,
    String family
  ) {}


  @Builder
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record AddConferenceEditionCommand(
    @TargetAggregateIdentifier
    String conferenceId,
    Integer year,
    LocalDate startDate,
    VenueLocation venue
  ) {}

  @Builder
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record ScheduleConferenceEditionCommand(
    @TargetAggregateIdentifier
    String conferenceId,
    String editionId,
    LocalDate startDate
  ) {}

  @Builder
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record VenueLocation(String name, String address) {}

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record ConferenceLocation(String country, String city, Boolean isOnline) {}
}
