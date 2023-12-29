package org.example.projection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface ConferenceModelRepository extends JpaRepository<ConferenceModel, String> {
  ConferenceModel findByName(String name);
}
