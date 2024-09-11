package seriesparser;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import seriesparser.jsonmodel.*;
import seriesparser.util.*;

import java.io.File;
import java.io.IOException;

public class DownloadAllJSONs {
    public static void main(final String[] args) {
        long timeStart = System.currentTimeMillis();
        String ip = CLI.getIP(args);
        boolean showProgress = CLI.getShowProgress(args);
        boolean updateIndex = CLI.getUpdateIndex(args);
        String listDirectory = CLI.getSeriesListJsonOutputDirectory(args);
        String outputDirectory = CLI.getSeriesJsonOutputDirectory(args);
        File d = new File(outputDirectory);
        FileFunctions.betterMkdir(d);
        String currentJSON;
        String basePath = "/serie/stream/";
        File f;
        HTMLDownloader hd = new HTMLDownloader(ip);
        if (updateIndex) SeriesListDownloader.loadListJSONs(hd, listDirectory);
        File seriesListFile = new File(listDirectory + "series.json");
        Series series;
        Gson gson = new Gson();
        if (seriesListFile.exists()) {
            String listContent = FileFunctions.load(listDirectory + "series.json");
            parser.model.Series[] allSeries = gson.fromJson(listContent, parser.model.Series[].class);
            int seriesAmountDigits = StringAnalyzer.getDigits(allSeries.length);
            String seasonsHTML, descrHTML, descr;
            for (int i = 0; i < allSeries.length; i++) { // iterate over all series
                String url = allSeries[i].getUrl();
                String name = StringFunctions.htmlEntitiesToASCII(allSeries[i].getName());
                String seriesId = url.substring(url.lastIndexOf("/") + 1);
                seasonsHTML = hd.downloadSeasonsHTML(url);
                Season[] seasons = SeriesParser.parseSeasons(seasonsHTML);
                descrHTML = hd.downloadSeriesDescriptionHTML(url);
                descr = SeriesParser.parseSeriesDescription(descrHTML);
                if (showProgress) System.out.println("Parsing series " + StringFunctions.leftPadZero((i + 1), seriesAmountDigits) + "/" + allSeries.length + ": \"" + name + "\"");
                String episodesHTML;
                for (final Season season : seasons) {
                    episodesHTML = hd.downloadEpisodesHTML(basePath + seriesId + "/" + season.getSeasonId());
                    Episode[] episodes = SeriesParser.parseEpisodes(episodesHTML);
                    if (episodes == null) continue; // Skip if episodes could not be parsed for some reason
                    String descriptionHTML;
                    String[] versionHTMLs;
                    for (final Episode episode : episodes) {
                        if (episode == null) continue; // Skip if episode could not be parsed for some reason
                        versionHTMLs = hd.downloadVersionHTMLs(basePath + seriesId + "/" + season.getSeasonId() + "/" + episode.getEpId());
                        descriptionHTML = hd.downloadEpisodeDescriptionHTML(basePath + seriesId + "/" + season.getSeasonId() + "/" + episode.getEpId());
                        episode.setVersions(SeriesParser.parseVersions(versionHTMLs));
                        episode.setDescr(SeriesParser.parseEpisodeDescription(descriptionHTML));
                    }
                    season.setEpisodes(episodes);
                }
                series = new Series(seriesId, name, descr, seasons);
                currentJSON = gson.toJson(series);
                f = new File(outputDirectory + seriesId + ".json");
                try {
                    FileUtils.writeStringToFile(f, currentJSON, "UTF-8");
                } catch (final IOException e) {
                    System.err.println("Could not write file " + f.getPath());
                }
            }
            System.out.println("Success!");
        }
        long timeEnd = System.currentTimeMillis();
        long timeSpent = timeEnd - timeStart;
        System.out.println("The operation took " + timeSpent + "ms");
        System.out.println(hd.getBytesDownloaded() + "bytes were downloaded.");
    }
}
