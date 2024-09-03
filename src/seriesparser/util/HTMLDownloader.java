package seriesparser.util;

/**
 * This is the class seriesparser.util.HTMLDownloader.
 * Not to be confused with parser.util.HTMLDownloader, which is used internally.
 */
public class HTMLDownloader {
    private final String ip;
    private String fullHtml;
    private String lastPath;

    public HTMLDownloader(final String ip) {
        this.ip = ip;
        fullHtml = null;
        lastPath = null;
    }

    public String downloadHTML(final String path) {
        if (path == null) {
            System.err.println("HTMLDownloader.downloadHTML(String): path may not be null.");
            return null;
        }
        if (lastPath == null) {
            fullHtml = parser.util.HTMLDownloader.getHTML("http://" + ip + path, false);
        } else {
            if (!path.equals(lastPath)) {
                fullHtml = parser.util.HTMLDownloader.getHTML("http://" + ip + path, false);
            }
        }
        lastPath = path;
        return fullHtml;
    }

    public String downloadSeasonsHTML(final String path) {
        String currentSeriesHTML = downloadHTML(path);
        String startSeasonsPattern = "<div class=\"hosterSiteDirectNav\" id=\"stream\">";
        String endSeasonsPattern = "</ul>";
        currentSeriesHTML = currentSeriesHTML.substring(currentSeriesHTML.indexOf(startSeasonsPattern) + startSeasonsPattern.length());
        currentSeriesHTML = currentSeriesHTML.substring(0, currentSeriesHTML.indexOf(endSeasonsPattern));
        return currentSeriesHTML;
    }

    public String downloadEpisodesHTML(final String path) {
        String currentEpisodesHTML = downloadHTML(path);
        String episodesStartPattern = "</thead>";
        String episodesEndPattern = "</tbody>";
        currentEpisodesHTML = currentEpisodesHTML.substring((currentEpisodesHTML.indexOf(episodesStartPattern) + episodesStartPattern.length()));
        currentEpisodesHTML = currentEpisodesHTML.substring(0, currentEpisodesHTML.indexOf(episodesEndPattern));
        return currentEpisodesHTML;
    }

    public String getIp() {
        return ip;
    }

    public String downloadStreamsHTML(final String path) {
        String streamsHTML = downloadHTML(path);
        String streamsStartPattern = "<div class=\"hosterSiteVideo\">";
        String streamsEndPattern = "<div class=\"cf\"></div>";
        streamsHTML = streamsHTML.substring((streamsHTML.indexOf(streamsStartPattern) + streamsStartPattern.length()));
        streamsHTML = streamsHTML.substring(0, streamsHTML.indexOf(streamsEndPattern));
        return streamsHTML;
    }

    public String downloadEpisodeDescriptionHTML(final String path) {
        String descriptionHTML = downloadHTML(path);
        String descrStartPattern = "<p class=\"descriptionSpoiler\" itemprop=\"description\">";
        String descrEndPattern = "</p>";
        if (!descriptionHTML.contains(descrStartPattern)) return "";
        descriptionHTML = descriptionHTML.substring((descriptionHTML.indexOf(descrStartPattern) + descrStartPattern.length()));
        descriptionHTML = descriptionHTML.substring(0, descriptionHTML.indexOf(descrEndPattern));
        return descriptionHTML;
    }

    public String downloadSeriesDescriptionHTML(final String path) {
        String descriptionHTML = downloadHTML(path);
        String descrStartPattern = "data-description-type=\"review\" data-full-description=\"";
        String descrEndPattern = "\">";
        descriptionHTML = descriptionHTML.substring((descriptionHTML.indexOf(descrStartPattern) + descrStartPattern.length()));
        descriptionHTML = descriptionHTML.substring(0, descriptionHTML.indexOf(descrEndPattern));
        return descriptionHTML;
    }
}
