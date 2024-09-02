package seriesparser.model;

public class Series {
    private String path;
    private String name;
    private Season[] seasons;

    public Series(final String path, final String name, final Season[] seasons) {
        this.path = path;
        this.name = name;
        this.seasons = seasons;
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

    public Season[] getSeasons() {
        return seasons;
    }

    public void setSeasons(final Season[] seasons) {
        this.seasons = seasons;
    }
}
