package org.example.projection;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "conference")
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Data
public class ConferenceModel {
  @Id
  private String conferenceId;
  private String name;
  private String website;
}

