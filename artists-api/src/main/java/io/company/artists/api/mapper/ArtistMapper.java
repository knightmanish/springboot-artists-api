package io.company.artists.api.mapper;

import io.company.artists.api.lib.Artist;
import io.company.artists.api.models.db.ArtistEntity;

public class ArtistMapper {

    public static Artist toArtist(ArtistEntity entity) {
        if (entity == null) return null;

        Artist artist = new Artist();
        artist.setArtistName(entity.getArtistName());
        artist.setArtistGenre(entity.getArtistGenre());
        artist.setAlbumsRecorded(entity.getAlbumsRecorded());
        artist.setUsername(entity.getUsername());

        return artist;
    }

    public static ArtistEntity toArtistEntity(Artist artist) {
        if (artist == null) return null;

        ArtistEntity entity = new ArtistEntity();
        entity.setArtistName(artist.getArtistName());
        entity.setArtistGenre(artist.getArtistGenre());
        entity.setAlbumsRecorded(artist.getAlbumsRecorded());
        entity.setUsername(artist.getUsername());

        return entity;
    }
}
