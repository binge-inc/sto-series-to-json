package seriesparser.util;

import seriesparser.jsonmodel.Episode;
import seriesparser.jsonmodel.Season;
import seriesparser.jsonmodel.Stream;
import seriesparser.jsonmodel.Version;

import java.util.ArrayList;
import java.util.Arrays;

public class SeriesParser {
    public static Episode[] parseEpisodes(final String listHtml) {
        if (listHtml == null) {
            System.err.println("SeriesParser.parseEpisodes(String): listHtml invalid");
            return null;
        }
        String linkStartPattern = "href=\"";
        String linkEndPattern = "\">";
        String episodeNumberEndPattern = "</a>";
        String episodeNameStartPattern = "<strong>";
        String episodeNameEndPattern = "</strong>";
        String spanStartPattern = "<span>";
        String spanEndPattern = "</span>";
        String[] episodeHTMLs = getEpisodeHTMLs(listHtml, linkStartPattern);
        Episode[] episodes = new Episode[episodeHTMLs.length];
        String currentHTML, episodeLink, alt, name;
        for (int i = 0; i < episodeHTMLs.length; i++) {
            currentHTML = episodeHTMLs[i].substring(episodeHTMLs[i].indexOf(linkStartPattern) + linkStartPattern.length());
            episodeLink = currentHTML.substring(0, currentHTML.indexOf(linkEndPattern));
            alt = currentHTML;
            if (alt.contains(episodeNameEndPattern)) {
                alt = alt.substring(alt.indexOf(episodeNameEndPattern) + episodeNameEndPattern.length());
                alt = alt.substring(0, alt.indexOf(episodeNumberEndPattern));
            }
            if (alt.contains(spanStartPattern)) {
                alt = alt.substring(alt.indexOf(spanStartPattern) + spanStartPattern.length());
                alt = alt.substring(0, alt.indexOf(spanEndPattern));
            }
            while (alt.startsWith(" ")) {
                alt = alt.substring(1);
            }
            while (alt.endsWith(" ")) {
                alt = alt.substring(0, alt.length() - 1);
            }
            name = currentHTML.substring(currentHTML.indexOf(episodeNameStartPattern) + episodeNameStartPattern.length(), currentHTML.indexOf(episodeNameEndPattern));
            Episode e = new Episode(episodeLink, name, (i + 1), StringFunctions.htmlEntitiesToASCII(alt), null, null); // ToDo epNo continous?
            episodes[i] = e;
        }
        return episodes;
    }

    /**
     * Auto-extracted from parseEpisodes(String)
     * // ToDo: Doc
     *
     * @param listHtml
     * @param linkStartPattern
     * @return
     */
    private static String[] getEpisodeHTMLs(final String listHtml, final String linkStartPattern) {
        String relevantHTML = listHtml.substring(listHtml.indexOf(linkStartPattern) + linkStartPattern.length());
        String spliteratorMainSeriesEpisodes = "itemprop=\"episodeNumber\"";
        String spliteratorMovies = "itemprop=\"episode\"";
        String[] episodeHTMLs;
        if (relevantHTML.contains(spliteratorMainSeriesEpisodes)) {
            episodeHTMLs = relevantHTML.split(spliteratorMainSeriesEpisodes);
        } else {
            episodeHTMLs = relevantHTML.split(spliteratorMovies);
        }
        return episodeHTMLs;
    }

    public static Season[] parseSeasons(final String seasonsHTML) {
        String spliterator = "<li>"; // ToDo
        String[] snippets = seasonsHTML.split(spliterator);
        ArrayList<String> snippetsList = new ArrayList<>(Arrays.asList(snippets));
        while (!snippetsList.get(0).contains("href=")) {
            snippetsList.remove(0);
        }
        String[] goodSnippets = new String[snippetsList.size()];
        for (int i = 0; i < snippetsList.size(); i++) {
            goodSnippets[i] = snippetsList.get(i);
        }
        Season[] seasons = new Season[goodSnippets.length];
        for (int i = 0; i < goodSnippets.length; i++) {
            seasons[i] = parseSeason(goodSnippets[i]);
        }
        return seasons;
    }

    private static Season parseSeason(final String seasonHTML) {
        String linkStartPattern = "href=\"";
        String linkEndPattern = "\"";
        String titleStartPattern = "title=\"";
        String titleEndPattern = "\">";
        String information = seasonHTML.substring(seasonHTML.indexOf(linkStartPattern) + linkStartPattern.length());
        String link = information.substring(0, information.indexOf(linkEndPattern));
        String seasonId = information.substring(information.indexOf(titleStartPattern) + titleStartPattern.length());
        seasonId = seasonId.substring(0, seasonId.indexOf(titleEndPattern));
        return new Season(link, seasonId, null);
    }

    public static Stream[] parseStreams(final String streamsHTML) {
        Stream[] streams;
        String spliterator = "<div class=\"generateInlinePlayer\">";
        String[] streamSnippetsDirty = streamsHTML.split(spliterator);
        ArrayList<String> snippetsList = new ArrayList<>(Arrays.asList(streamSnippetsDirty));
        snippetsList.remove(0);
        String[] streamSnippets = new String[snippetsList.size()];
        for (int i = 0; i < streamSnippets.length; i++) {
            streamSnippets[i] = snippetsList.get(i);
        }
        streams = new Stream[streamSnippets.length];
        String pathStartPattern = "href=\"";
        String pathEndPattern = "\"";
        String hosterStartPattern = "<h4>";
        String hosterEndPattern = "</h4>";
        String path, hoster;
        for (int i = 0; i < streams.length; i++) {
            path = streamSnippets[i].substring(streamSnippets[i].indexOf(pathStartPattern) + pathStartPattern.length());
            path = path.substring(0, path.indexOf(pathEndPattern));
            hoster = streamSnippets[i].substring(streamSnippets[i].indexOf(hosterStartPattern) + hosterStartPattern.length());
            hoster = hoster.substring(0, hoster.indexOf(hosterEndPattern));
            streams[i] = new Stream(path, hoster);
        }
        return streams;
    }

    public static String parseEpisodeDescription(final String episodeDescriptionHtml) {
        return StringFunctions.htmlEntitiesToASCII(episodeDescriptionHtml);
    }

    public static String parseSeriesDescription(final String seriesDescriptionHTML) {
        return StringFunctions.htmlEntitiesToASCII(seriesDescriptionHTML);
    }

    public static Version[] parseVersions(final String[] versionHTMLs) {
        // ToDo: Implement for real.
        // This is "pls just keep working for the default version while we implement this" code.
        Version[] versions = new Version[versionHTMLs.length];
        Stream[] streams;
        for (int i = 0; i < versionHTMLs.length; i++) {
            if (versionHTMLs[i] == null || versionHTMLs[i].isEmpty()) {
                versions[i] = null;
            } else {
                streams = parseStreams(versionHTMLs[i]); // versionsHTMLs[i] is streamsHTML
                versions[i] = new Version("default", streams); // ToDo: Change name
            }
        }
        return versions;
    }
}
