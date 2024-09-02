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
            fullHtml = parser.util.HTMLDownloader.getHTML("http://" + ip + path);
        } else {
            if (!path.equals(lastPath)) {
                fullHtml = parser.util.HTMLDownloader.getHTML("http://" + ip + path);
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
        // ToDo
        String currentEpisodesHTML = downloadHTML(path);
        String episodesStartPattern = "</thead>";
        String episodesEndPattern = "</tbody>";
        currentEpisodesHTML = currentEpisodesHTML.substring((currentEpisodesHTML.indexOf(episodesStartPattern) + episodesStartPattern.length()), currentEpisodesHTML.indexOf(episodesEndPattern));
        return currentEpisodesHTML;
    }

    public String getIp() {
        return ip;
    }
}
