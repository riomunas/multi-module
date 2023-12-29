package org.example.api;

import lombok.Builder;
import java.time.LocalDate;

public class Events {

  @Builder public record ConferenceAddedEvent(
    String conferenceId,
    String name,
    String website,
    ConferenceLocation location,
    String family,
    String ownerEmail
  ) {}

  @Builder public record ConferenceEditionAddedEvent(
    String conferenceId,
    String editionId,
    Integer year,
    LocalDate startDate,
    VenueLocation venue
  ) {}

  @Builder public record ConferenceScheduleEditionEvent(
    String conferenceId,
    String editionId,
    LocalDate startDate
  ) {}

  @Builder public record ConferenceLocation(
    String country,
    String city,
    Boolean isOnline
  ) {}

  @Builder public record VenueLocation(
    String name,
    String address
  ) {}
}
