package org.example.api;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import java.time.LocalDate;

public class Commands {
  @Builder
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record AddConferenceCommand(
    String name,
    @NotNull String website,
    ConferenceLocation location,
    String family,
    @NotNull String ownerEmail
  ) {
    public AddConferenceCommand(AddConferenceCommand command) {
      this(command.name(), command.website(), command.location(), command.family(), command.ownerEmail());
    }
  }

  @Builder
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record AddConferenceEditionCommand(
    @TargetAggregateIdentifier
    String conferenceId,
    Integer year,
    LocalDate startDate,
    VenueLocation venue,
    @NotNull String ownerEmail
  ) {
    public AddConferenceEditionCommand(AddConferenceEditionCommand command) {
      this(command.conferenceId(), command.year(), command.startDate(), command.venue(), command.ownerEmail());
    }
  }

  @Builder
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record ScheduleConferenceEditionCommand(
    @TargetAggregateIdentifier
    String conferenceId,
    String editionId,
    LocalDate startDate
  ) {
    public ScheduleConferenceEditionCommand(ScheduleConferenceEditionCommand command) {
      this(command.conferenceId(), command.editionId(), command.startDate());
    }
  }

  @Builder
  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record VenueLocation(
    String name,
    String address
  ) {}

  @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
  public record ConferenceLocation(
    String country,
    String city,
    Boolean isOnline
  ) {}
}
