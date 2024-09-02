package seriesparser.util;

import seriesparser.model.Episode;
import seriesparser.model.Season;
import seriesparser.model.Stream;

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
        String shortenedHTML = listHtml.substring(listHtml.indexOf(linkStartPattern) + linkStartPattern.length());
        String spliterator = "<meta itemprop=\"episodeNumber\"";
        String[] episodeHTMLs = shortenedHTML.split(spliterator);
        Episode[] episodes = new Episode[episodeHTMLs.length];
        String currentHTML, episodeLink, episodeId, name;
        for (int i = 0; i < episodeHTMLs.length; i++) {
            currentHTML = episodeHTMLs[i].substring(episodeHTMLs[i].indexOf(linkStartPattern) + linkStartPattern.length());
            episodeLink = currentHTML.substring(0, currentHTML.indexOf(linkEndPattern));
            episodeId = currentHTML;
            if (episodeId.contains(episodeNameEndPattern)) {
                episodeId = episodeId.substring(episodeId.indexOf(episodeNameEndPattern) + episodeNameEndPattern.length());
                episodeId = episodeId.substring(0, episodeId.indexOf(episodeNumberEndPattern));
            }
            if (episodeId.contains(spanStartPattern)) {
                episodeId = episodeId.substring(episodeId.indexOf(spanStartPattern) + spanStartPattern.length());
                episodeId = episodeId.substring(0, episodeId.indexOf(spanEndPattern));
            }
            while (episodeId.startsWith(" ")) {
                episodeId = episodeId.substring(1);
            }
            while (episodeId.endsWith(" ")) {
                episodeId = episodeId.substring(0, episodeId.length() - 1);
            }
            name = currentHTML.substring(currentHTML.indexOf(episodeNameStartPattern) + episodeNameStartPattern.length(), currentHTML.indexOf(episodeNameEndPattern));
            Episode e = new Episode(episodeLink, name, (i + 1), episodeId, null, null); // ToDo epNo
            episodes[i] = e;
        }
        return episodes;
    }

    /*
        This works because the downloader prepares a perfectly cut snippet by chance.
    */
    public static String parseEpisodeDescription(final String episodeDescriptionHtml) {
        return episodeDescriptionHtml;
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

    /*
        This works because the downloader prepares a perfectly cut snippet by chance.
    */
    public static String parseSeriesDescription(final String seriesDescriptionHTML) {
        return seriesDescriptionHTML;
    }
}
