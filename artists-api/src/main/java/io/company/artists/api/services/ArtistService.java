package io.company.artists.api.services;

import io.company.artists.api.lib.Artist;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArtistService {

    List<Artist> findArtists(Pageable pageable);

    Long findArtistsCount();

    List<Artist> createArtists(List<Artist> artists);

    Artist findArtistByUsername(String username);
}
