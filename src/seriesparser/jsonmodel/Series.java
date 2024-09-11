package seriesparser.jsonmodel;

public class Series {
    private String seriesId;
    private String name;
    private String descr;
    private Season[] seasons;

    public Series(final String seriesId, final String name, final String descr, final Season[] seasons) {
        this.seriesId = seriesId;
        this.name = name;
        this.descr = descr;
        this.seasons = seasons;
    }

    public String getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(final String seriesId) {
        this.seriesId = seriesId;
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
