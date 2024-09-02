package seriesparser.model;

public class Episode {
    private String path;
    private String name; // usually German episode title
    private int epNo; // episode number
    private String alt; // Alternative name, usually original title or "Episode 1"
    private String descr; // description
    private Stream[] streams;

    public Episode(final String path, final String name, final int epNo, final String alt, final String descr, final Stream[] streams) {
        this.path = path;
        this.name = name;
        this.epNo = epNo;
        this.alt = alt;
        this.descr = descr;
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

    public String getAlt() {
        return alt;
    }

    public void setAlt(final String alt) {
        this.alt = alt;
    }

    public Stream[] getStreams() {
        return streams;
    }

    public void setStreams(final Stream[] streams) {
        this.streams = streams;
    }

    public int getEpNo() {
        return epNo;
    }

    public void setEpNo(final int epNo) {
        this.epNo = epNo;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(final String descr) {
        this.descr = descr;
    }
}
