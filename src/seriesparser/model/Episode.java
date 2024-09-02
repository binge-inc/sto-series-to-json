package seriesparser.model;

public class Episode {
    private String path;
    private String name;
    private String episodeId;
    private Stream[] streams;

    public Episode(final String path, final String name, final String episodeId, final Stream[] streams) {
        this.path = path;
        this.name = name;
        this.episodeId = episodeId;
        this.streams = streams;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getEpisodeId() {
        return episodeId;
    }

    public void setEpisodeId(final String episodeId) {
        this.episodeId = episodeId;
    }

    public Stream[] getStreams() {
        return streams;
    }

    public void setStreams(final Stream[] streams) {
        this.streams = streams;
    }
}
