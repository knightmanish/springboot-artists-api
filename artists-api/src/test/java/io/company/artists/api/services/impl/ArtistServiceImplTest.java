package io.company.artists.api.services.impl;

import io.company.artists.api.lib.Artist;
import io.company.artists.api.models.ArtistRepository;
import io.company.artists.api.models.db.ArtistEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ArtistServiceImplTest {

    @InjectMocks
    private ArtistServiceImpl service;
    @Mock
    private ArtistRepository repository;

    @Test
    void findArtists() {
        ArtistEntity lpark = createArtistEntity("lpark");
        ArtistEntity avril = createArtistEntity("avril");
        List<ArtistEntity> artists = new ArrayList<>();
        artists.add(lpark);
        artists.add(avril);

        when(repository.findAll(any(Pageable.class))).thenReturn(new PageImpl<>(artists));

        List<Artist> result = service.findArtists(PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "artistName")));
        assertTrue(result.stream().anyMatch(a -> a.getUsername().equals("lpark")));
        assertTrue(result.stream().anyMatch(a -> a.getUsername().equals("avril")));
    }

    @Test
    void findArtistsCount() {
        ArtistEntity lpark = createArtistEntity("lpark");
        ArtistEntity avril = createArtistEntity("avril");
        List<ArtistEntity> artists = new ArrayList<>();
        artists.add(lpark);
        artists.add(avril);

        when(repository.count()).thenReturn((long) artists.size());
        assertEquals(service.findArtistsCount(), ((long)artists.size()));
    }

    @Test
    void findArtistByUsername() {
        String username = "lpark";
        ArtistEntity artistEntity = createArtistEntity(username);
        when(repository.findByUsername(username)).thenReturn(artistEntity);

        Artist artist = service.findArtistByUsername(username);
        assertEquals(artist.getUsername(), username);
    }

    private ArtistEntity createArtistEntity(String username) {
        ArtistEntity artistEntity = new ArtistEntity();
        if (username.equals("lpark")) {
            artistEntity.setArtistName("Linkin Park");
            artistEntity.setArtistGenre("Rock");
            artistEntity.setAlbumsRecorded(10);
        } else if (username.equals("avril")) {
            artistEntity.setArtistName("Avril Lavigne");
            artistEntity.setArtistGenre("Soft Rock");
            artistEntity.setAlbumsRecorded(15);
        }
        artistEntity.setUsername(username);
        return artistEntity;
    }
}