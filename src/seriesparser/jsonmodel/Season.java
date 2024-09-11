package seriesparser.jsonmodel;

public class Season {
    private String seasonId;
    private Episode[] episodes;

    public Season(final String seasonId, final Episode[] episodes) {
        this.seasonId = seasonId;
        this.episodes = episodes;
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
