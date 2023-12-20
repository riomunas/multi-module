package org.example.api;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class Events {

  @Builder public record ConferenceAddedEvent(String conferenceId, String name, String website, ConferenceLocation location, String family) {}
  @Builder public record ConferenceEditionAddedEvent(String conferenceId, String editionId, Integer year, LocalDate startDate, VenueLocation venue) {}
  @Builder public record ConferenceScheduleEditionEvent(String conferenceId, String editionId, LocalDate startDate) {}


  @Builder @Getter
  public static class ConferenceLocation {
    private final String country;
    private final String city;
    private final Boolean isOnline;
  }
//  @Builder public record ConferenceLocation(String country, String city, Boolean isOnline) {}

  @Builder @Getter
  public static class VenueLocation {
    private final String name;
    private final String address;
  }
//  @Builder public record VenueLocation(String name, String address) {}

}
