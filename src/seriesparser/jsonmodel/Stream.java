package seriesparser.jsonmodel;

public class Stream {
    private String path;
    private String hoster;

    public Stream (final String path, final String hoster) {
        this.path = path;
        this.hoster = hoster;
    }

    public String getPath() {
        return path;
    }

    public void setPath(final String path) {
        this.path = path;
    }

    public String getHoster() {
        return hoster;
    }

    public void setHoster(final String hoster) {
        this.hoster = hoster;
    }
}
