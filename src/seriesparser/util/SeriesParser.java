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
            String epId = episodeLink.substring(episodeLink.lastIndexOf("/") + 1);
            Episode e = new Episode(epId, name, (i + 1), StringFunctions.htmlEntitiesToASCII(alt), null, null); // ToDo epNo continous?
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
        String relevantPart = seasonHTML.substring(seasonHTML.indexOf(linkStartPattern) + linkStartPattern.length());
        String link = relevantPart.substring(0, relevantPart.indexOf(linkEndPattern));
        String seasonId = link.substring(link.lastIndexOf("/") + 1);
        return new Season(seasonId, null);
    }

    public static String parseEpisodeDescription(final String episodeDescriptionHtml) {
        return StringFunctions.htmlEntitiesToASCII(episodeDescriptionHtml);
    }

    public static String parseSeriesDescription(final String seriesDescriptionHTML) {
        return StringFunctions.htmlEntitiesToASCII(seriesDescriptionHTML);
    }

    public static Version[] parseVersions(final String[] versionHTMLs) {
        String versionIdStart = "data-lang-key=\"";
        String pathStartPattern = "href=\"";
        String hosterStartPattern = "<h4>";
        String valueEndPattern = "\"";
        String hosterEndPattern = "</h4>";
        ArrayList<Integer> uniqueDataLangKeys = new ArrayList<>();
        int[] languageKeys = new int[versionHTMLs.length];
        int[] redirects = new int[versionHTMLs.length];
        String[] hosters = new String[versionHTMLs.length];
        for (int i = 0; i < versionHTMLs.length; i++) {
            String langKeyString = StringFunctions.cutFromTo(versionHTMLs[i], versionIdStart, valueEndPattern);
            if (langKeyString == null) continue;
            languageKeys[i] = Integer.parseInt(langKeyString);
            if (!uniqueDataLangKeys.contains(languageKeys[i])) {
                uniqueDataLangKeys.add(languageKeys[i]);
            }
            String redirectString = StringFunctions.cutFromTo(versionHTMLs[i], pathStartPattern, valueEndPattern);
            if (redirectString == null) continue;
            redirectString = redirectString.substring(redirectString.lastIndexOf("/") + 1);
            redirects[i] = Integer.parseInt(redirectString);
            hosters[i] = StringFunctions.cutFromTo(versionHTMLs[i], hosterStartPattern, hosterEndPattern);
        }
        Version[] versions = new Version[uniqueDataLangKeys.size()];
        for (int i = 0; i < versions.length; i++) {
            Stream[] streams;
            int versionsCounter = 0;
            for (final int languageKey : languageKeys) {
                if (uniqueDataLangKeys.get(i).equals(languageKey)) versionsCounter++;
            }
            streams = new Stream[versionsCounter];
            int streamsCounter = 0;
            for (int j = 0; j < languageKeys.length; j++) {
                if (uniqueDataLangKeys.get(i).equals(languageKeys[j])) {
                    streams[streamsCounter] = new Stream(redirects[j], hosters[j]);
                    streamsCounter++;
                }
            }
            versions[i] = new Version(uniqueDataLangKeys.get(i), streams);
        }
        return versions;
    }
}
