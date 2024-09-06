package seriesparser.jsonmodel;

public class Season {
    private String path;
    private String seasonId;
    private Episode[] episodes;

    public Season(final String path, final String seasonId, final Episode[] episodes) {
        this.path = path;
        this.seasonId = seasonId;
        this.episodes = episodes;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public String getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(final String seasonId) {
        this.seasonId = seasonId;
    }

    public Episode[] getEpisodes() {
        return episodes;
    }

    public void setEpisodes(final Episode[] episodes) {
        this.episodes = episodes;
    }
}
