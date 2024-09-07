package seriesparser.jsonmodel;

public class Version {
    private String languageKey;
    private Stream[] streams;

    public Version(final String languageKey, final Stream[] streams) {
        this.languageKey = languageKey;
        this.streams = streams;
    }

    public String getLanguageKey() {
        return languageKey;
    }

    public void setLanguageKey(final String languageKey) {
        this.languageKey = languageKey;
    }

    public Stream[] getStreams() {
        return streams;
    }

    public void setStreams(final Stream[] streams) {
        this.streams = streams;
    }

    public String getVersionName() {
        switch (languageKey) {
            case "1":
                return "Deutsch";
            case "2":
                return "Unknown 2";
            case "3":
                return "mit deutschen Untertiteln";
            case "4":
                return "Unknown 4";
        }
        return "Unknown languageKey.";
    }
}
