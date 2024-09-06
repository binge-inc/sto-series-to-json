package seriesparser.jsonmodel;

public class Version {
    private String versionName;
    private Stream[] streams;

    public Version(final String versionName, final Stream[] streams) {
        this.versionName = versionName;
        this.streams = streams;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(final String versionName) {
        this.versionName = versionName;
    }

    public Stream[] getStreams() {
        return streams;
    }

    public void setStreams(final Stream[] streams) {
        this.streams = streams;
    }
}
