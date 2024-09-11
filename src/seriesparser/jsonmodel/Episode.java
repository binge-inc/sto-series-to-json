package seriesparser.jsonmodel;

public class Episode {
    private String epId;
    private String name; // usually German episode title
    private int epNo; // episode number
    private String alt; // Alternative name, usually original title or "Episode 1"
    private String descr; // description
    private Version[] versions;

    public Episode(final String epId, final String name, final int epNo, final String alt, final String descr, final Version[] versions) {
        this.epId = epId;
        this.name = name;
        this.epNo = epNo;
        this.alt = alt;
        this.descr = descr;
        this.versions = versions;
    }

    public String getEpId() {
        return epId;
    }

    public void setEpId(final String epId) {
        this.epId = epId;
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

    public Version[] getVersions() {
        return versions;
    }

    public void setVersions(final Version[] versions) {
        this.versions = versions;
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
