package seriesparser.jsonmodel;

public class Version {
    private int lang;
    private Stream[] streams;

    public Version(final int lang, final Stream[] streams) {
        this.lang = lang;
        this.streams = streams;
    }

    public int getLang() {
        return lang;
    }

    public void setLang(final int lang) {
        this.lang = lang;
    }

    public Stream[] getStreams() {
        return streams;
    }

    public void setStreams(final Stream[] streams) {
        this.streams = streams;
    }

    public String getVersionName() {
        switch (lang) {
            case 1:
                return "Deutsch";
            case 2:
                return "Englisch";
            case 3:
                return "mit deutschen Untertiteln";
            default:
                return "Unknown languageKey: " + lang;
        }
    }
}
