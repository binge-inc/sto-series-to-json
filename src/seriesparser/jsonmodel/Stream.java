package seriesparser.jsonmodel;

public class Stream {
    private int redirect;
    private String hoster;

    public Stream (final int redirect, final String hoster) {
        this.redirect = redirect;
        this.hoster = hoster;
    }

    public int getRedirect() {
        return redirect;
    }

    public void setRedirect(final int redirect) {
        this.redirect = redirect;
    }

    public String getHoster() {
        return hoster;
    }

    public void setHoster(final String hoster) {
        this.hoster = hoster;
    }
}
