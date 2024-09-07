package seriesparser.util;

import java.util.ArrayList;

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
        currentEpisodesHTML = currentEpisodesHTML.substring(currentEpisodesHTML.indexOf(episodesStartPattern) + episodesStartPattern.length());
        currentEpisodesHTML = currentEpisodesHTML.substring(0, currentEpisodesHTML.indexOf(episodesEndPattern));
        return currentEpisodesHTML;
    }

    public String getIp() {
        return ip;
    }

    public String downloadEpisodeDescriptionHTML(final String path) {
        String descriptionHTML = downloadHTML(path);
        String descrStartPattern = "<p class=\"descriptionSpoiler\" itemprop=\"description\">";
        String descrEndPattern = "</p>";
        if (!descriptionHTML.contains(descrStartPattern)) return "";
        descriptionHTML = descriptionHTML.substring(descriptionHTML.indexOf(descrStartPattern) + descrStartPattern.length());
        descriptionHTML = descriptionHTML.substring(0, descriptionHTML.indexOf(descrEndPattern));
        return descriptionHTML;
    }

    public String downloadSeriesDescriptionHTML(final String path) {
        String descriptionHTML = downloadHTML(path);
        String descrStartPattern = "data-description-type=\"review\" data-full-description=\"";
        String descrEndPattern = "\">";
        return StringFunctions.cutFromTo(descriptionHTML, descrStartPattern, descrEndPattern);
    }

    public String[] downloadVersionHTMLs(final String path) {
        String versionsHTML = downloadHTML(path);
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
}
