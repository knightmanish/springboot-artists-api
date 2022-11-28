package io.company.artists.api.lib;

import java.util.Objects;
import javax.validation.constraints.*;

public class Artist {
    @NotEmpty
    private String artistName = null;

    @NotEmpty
    private String artistGenre = null;

    @NotEmpty
    private Integer albumsRecorded = null;

    @NotEmpty
    private String username = null;

    public Artist artistName(String artistName) {
        this.artistName = artistName;
        return this;
    }

    /**
     * Get artistName
     * @return artistName
     **/
    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Artist artistGenre(String artistGenre) {
        this.artistGenre = artistGenre;
        return this;
    }

    /**
     * Get artistGenre
     * @return artistGenre
     **/
    public String getArtistGenre() {
        return artistGenre;
    }

    public void setArtistGenre(String artistGenre) {
        this.artistGenre = artistGenre;
    }

    public Artist albumsRecorded(Integer albumsRecorded) {
        this.albumsRecorded = albumsRecorded;
        return this;
    }

    /**
     * Get albumsRecorded
     * @return albumsRecorded
     **/
    public Integer getAlbumsRecorded() {
        return albumsRecorded;
    }

    public void setAlbumsRecorded(Integer albumsRecorded) {
        this.albumsRecorded = albumsRecorded;
    }

    public Artist username(String username) {
        this.username = username;
        return this;
    }

    /**
     * Get username
     * @return username
     **/
    @NotNull
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    @Override
    public boolean equals(java.lang.Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Artist artist = (Artist) o;
        return Objects.equals(this.artistName, artist.artistName) &&
                Objects.equals(this.artistGenre, artist.artistGenre) &&
                Objects.equals(this.albumsRecorded, artist.albumsRecorded) &&
                Objects.equals(this.username, artist.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(artistName, artistGenre, albumsRecorded, username);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("class Artist {\n");

        sb.append("    artistName: ").append(toIndentedString(artistName)).append("\n");
        sb.append("    artistGenre: ").append(toIndentedString(artistGenre)).append("\n");
        sb.append("    albumsRecorded: ").append(toIndentedString(albumsRecorded)).append("\n");
        sb.append("    username: ").append(toIndentedString(username)).append("\n");
        sb.append("}");
        return sb.toString();
    }

    /**
     * Convert the given object to string with each line indented by 4 spaces
     * (except the first line).
     */
    private String toIndentedString(java.lang.Object o) {
        if (o == null) {
            return "null";
        }
        return o.toString().replace("\n", "\n    ");
    }
}
