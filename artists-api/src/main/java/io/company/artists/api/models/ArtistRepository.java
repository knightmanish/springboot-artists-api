package io.company.artists.api.models;

import io.company.artists.api.models.db.ArtistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<ArtistEntity, String> {
    ArtistEntity findByUsername(String username);
}
