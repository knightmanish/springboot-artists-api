package io.company.artists.api.models.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@lombok.Data
@Entity
@Table(name = "artists")
public class ArtistEntity {

    @NotEmpty
    @Column(name = "name")
    private String artistName = null;

    @NotEmpty
    @Column(name = "genre")
    private String artistGenre = null;

    @NotNull
    @Column(name = "albumsRecorded")
    private Integer albumsRecorded = null;

    @Id
    @NotEmpty
    @Column(name = "username")
    private String username = null;
}
