package seriesparser.model;

public class Series {
    private String path;
    private String name;
    private String descr;
    private Season[] seasons;

    public Series(final String path, final String name, final String descr, final Season[] seasons) {
        this.path = path;
        this.name = name;
        this.descr = descr;
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

    public String getDescr() {
        return descr;
    }

    public void setDescr(final String descr) {
        this.descr = descr;
    }

    public Season[] getSeasons() {
        return seasons;
    }

    public void setSeasons(final Season[] seasons) {
        this.seasons = seasons;
    }
}
