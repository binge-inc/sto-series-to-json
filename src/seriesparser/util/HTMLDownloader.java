package seriesparser.util;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * This is the class seriesparser.util.HTMLDownloader.
 * Not to be confused with parser.util.HTMLDownloader, which is used internally.
 */
public class HTMLDownloader {
    private final String ip;
    private String fullHtml;
    private String lastPath;
    private long bytesDownloaded;

    public HTMLDownloader(final String ip) {
        this.ip = ip;
        fullHtml = null;
        lastPath = null;
        bytesDownloaded = 0L;
    }

    public String downloadHTML(final String path) {
        if (path == null) {
            System.err.println("HTMLDownloader.downloadHTML(String): path may not be null.");
            return null;
        }
        if (!path.equals(lastPath)) {
            try {
                fullHtml = parser.util.HTMLDownloader.getHTML("http://" + ip + path, false);
                bytesDownloaded += fullHtml.getBytes(StandardCharsets.UTF_8).length;
            } catch (final RuntimeException e) {
                // ToDo: Handle the IOException directly in sto-series-list-to-json, where the HTMLDownloader.getHTML(String) function is implemented
                // This is just provisional.
                System.err.println("HTMLDownloader.downloadHTML(String): RuntimeException.\nProbably: java.io.IOException: Premature EOF");
                System.err.println("Skipping series.");
                // ToDo: remove whole block when getHTML is fixed.
                return null;
            }
        }
        lastPath = path;
        return fullHtml;
    }

    /**
     * Only used for FetchOneSeriesByExactId as it does not have access to the name field from the index json.
     * This should
     *
     * @param path
     * @return
     */
    public String downloadNiceName(final String path) {
        String currentSeriesHTML = downloadHTML(path);
        if (currentSeriesHTML == null) return null;
        String startPattern = "<div class=\"series-title\">";
        String endPattern = "</h1>";
        currentSeriesHTML = currentSeriesHTML.substring(currentSeriesHTML.indexOf(startPattern) + startPattern.length());
        currentSeriesHTML = currentSeriesHTML.substring(0, currentSeriesHTML.indexOf(endPattern));
        return currentSeriesHTML;
    }

    public String downloadSeasonsHTML(final String path) {
        String currentSeriesHTML = downloadHTML(path);
        if (currentSeriesHTML == null) return null;
        String startSeasonsPattern = "<div class=\"hosterSiteDirectNav\" id=\"stream\">";
        String endSeasonsPattern = "</ul>";
        currentSeriesHTML = currentSeriesHTML.substring(currentSeriesHTML.indexOf(startSeasonsPattern) + startSeasonsPattern.length());
        currentSeriesHTML = currentSeriesHTML.substring(0, currentSeriesHTML.indexOf(endSeasonsPattern));
        return currentSeriesHTML;
    }

    public String downloadEpisodesHTML(final String path) {
        String currentEpisodesHTML = downloadHTML(path);
        if (currentEpisodesHTML == null) return null;
        String episodesStartPattern = "</thead>";
        String episodesEndPattern = "</tbody>";
        currentEpisodesHTML = currentEpisodesHTML.substring(currentEpisodesHTML.indexOf(episodesStartPattern) + episodesStartPattern.length());
        currentEpisodesHTML = currentEpisodesHTML.substring(0, currentEpisodesHTML.indexOf(episodesEndPattern));
        return currentEpisodesHTML;
    }

    public String getIp() {
        return ip;
    }

    public String downloadEpisodeDescriptionHTML(final String path) {
        String descriptionHTML = downloadHTML(path);
        if (descriptionHTML == null) return null;
        String descrStartPattern = "<p class=\"descriptionSpoiler\" itemprop=\"description\">";
        String descrEndPattern = "</p>";
        if (!descriptionHTML.contains(descrStartPattern)) return "";
        descriptionHTML = descriptionHTML.substring(descriptionHTML.indexOf(descrStartPattern) + descrStartPattern.length());
        descriptionHTML = descriptionHTML.substring(0, descriptionHTML.indexOf(descrEndPattern));
        return descriptionHTML;
    }

    public String downloadSeriesDescriptionHTML(final String path) {
        String descriptionHTML = downloadHTML(path);
        if (descriptionHTML == null) return null;
        String descrStartPattern = "data-description-type=\"review\" data-full-description=\"";
        String descrEndPattern = "\">";
        return StringFunctions.cutFromTo(descriptionHTML, descrStartPattern, descrEndPattern);
    }

    public String[] downloadVersionHTMLs(final String path) {
        String versionsHTML = downloadHTML(path);
        if (versionsHTML == null) return null;
        String firstCutStart = "<ul class=\"row\">";
        String firstCutEnd = "</ul>";
        String spliterator = "<span>";
        String relevantHTML = StringFunctions.cutFromTo(versionsHTML, firstCutStart, firstCutEnd);
        if (relevantHTML == null || relevantHTML.isEmpty()) return null;
        String[] versionHTMLsDirty = relevantHTML.split(spliterator);
        String[] versionHTMLs = new String[versionHTMLsDirty.length - 1];
        System.arraycopy(versionHTMLsDirty, 0, versionHTMLs, 0, versionHTMLs.length);
        return versionHTMLs;
    }

    public long getBytesDownloaded() {
        return bytesDownloaded;
    }
}
