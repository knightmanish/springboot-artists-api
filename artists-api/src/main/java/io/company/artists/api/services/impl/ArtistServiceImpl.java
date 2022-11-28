package io.company.artists.api.services.impl;

import io.company.artists.api.lib.Artist;
import io.company.artists.api.mapper.ArtistMapper;
import io.company.artists.api.models.ArtistRepository;
import io.company.artists.api.models.db.ArtistEntity;
import io.company.artists.api.services.ArtistService;
import io.company.artists.api.services.exceptions.DuplicateException;
import io.company.artists.api.services.exceptions.EmptyPayloadException;
import io.company.artists.api.services.exceptions.InvalidAttributeException;
import io.company.artists.api.services.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArtistServiceImpl implements ArtistService {

    private final ArtistRepository artistRepository;

    @Value("${artists.not.allowed.genre}")
    private String notAllowedGenre;

    @Autowired
    public ArtistServiceImpl(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    @Override
    public List<Artist> findArtists(Pageable pageable) {
        List<ArtistEntity> artistEntities = artistRepository.findAll(pageable).getContent();
        return artistEntities.stream().map(ArtistMapper::toArtist).collect(Collectors.toList());
    }

    @Override
    public Long findArtistsCount() {
        return artistRepository.count();
    }

    @Override
    public List<Artist> createArtists(List<Artist> artists) {
        if (artists == null || artists.isEmpty()) {
            throw new EmptyPayloadException(Artist.class.getSimpleName());
        }

        for (Artist artist : artists) {
            if (artistRepository.findByUsername(artist.getUsername()) != null) {
                throw new DuplicateException(Artist.class.getSimpleName(), artist.getUsername());
            }
            if (artist.getArtistGenre().equalsIgnoreCase(notAllowedGenre)) {
                throw new InvalidAttributeException(Artist.class.getSimpleName(), artist.getArtistGenre(), "Genre");
            }
        }

        List<ArtistEntity> artistEntities = artists.stream().map(ArtistMapper::toArtistEntity)
                .collect(Collectors.toList());
        artistEntities = artistRepository.saveAll(artistEntities);
        return artistEntities.stream().map(ArtistMapper::toArtist).collect(Collectors.toList());
    }

    @Override
    public Artist findArtistByUsername(String username) {
        ArtistEntity artistEntity = artistRepository.findByUsername(username);
        if (artistEntity == null) {
            throw new ResourceNotFoundException(Artist.class.getSimpleName(), username);
        }
        return ArtistMapper.toArtist(artistEntity);
    }
}
