package seriesparser;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import seriesparser.model.*;
import seriesparser.util.*;

import java.io.File;
import java.io.IOException;

public class Main {public static void main(final String[] args) {
        String ip = CLI.getIP(args);
        boolean showProgress = CLI.getShowProgress(args);
        String listDirectory = "./list-json/";
        String outputDirectory = "./json/";
        File d = new File(outputDirectory);
        FileFunctions.betterMkdir(d);
        String currentJSON;
        File f;
        HTMLDownloader hd = new HTMLDownloader(ip);
        SeriesListDownloader.loadListJSONs(hd, listDirectory);
        File seriesListFile = new File(listDirectory + "series.json");
        Series series;
        Gson gson = new Gson();
        if (seriesListFile.exists()) {
            String listContent = FileFunctions.load(listDirectory + "series.json");
            parser.model.Series[] allSeries = gson.fromJson(listContent, parser.model.Series[].class);
            int seriesAmountDigits = StringAnalyzer.getDigits(allSeries.length);
            String seasonsHTML, descrHTML, descr;
            for (int i = 0; i < allSeries.length; i++) { // iterate over all series
                seasonsHTML = hd.downloadSeasonsHTML(allSeries[i].getUrl());
                Season[] seasons = SeriesParser.parseSeasons(seasonsHTML);
                descrHTML = hd.downloadSeriesDescriptionHTML(allSeries[i].getUrl());
                descr = SeriesParser.parseSeriesDescription(descrHTML);
                if (showProgress) System.out.println("Parsing series " + StringFunctions.leftPadZero((i + 1), seriesAmountDigits) + "/" + allSeries.length + ": \"" + allSeries[i].getName() + "\"");
                String episodesHTML;
                for (final Season season : seasons) {
                    episodesHTML = hd.downloadEpisodesHTML(season.getPath());
                    Episode[] episodes = SeriesParser.parseEpisodes(episodesHTML);
                    if (episodes == null) continue; // Skip if episodes could not be parsed for some reason
                    String streamsHTML, descriptionHTML;
                    for (final Episode episode : episodes) {
                        if (episode == null) continue; // Skip if episode could not be parsed for some reason
                        streamsHTML = hd.downloadStreamsHTML(episode.getPath());
                        descriptionHTML = hd.downloadEpisodeDescriptionHTML(episode.getPath());
                        episode.setStreams(SeriesParser.parseStreams(streamsHTML));
                        episode.setDescr(SeriesParser.parseEpisodeDescription(descriptionHTML));
                    }
                    season.setEpisodes(episodes);
                }
                series = new Series(allSeries[i].getUrl(), StringFunctions.sanitize(allSeries[i].getName()), descr, seasons);
                currentJSON = gson.toJson(series);
                f = new File(outputDirectory + StringAnalyzer.getSeriesIdFromPath(series.getPath()) + ".json");
                try {
                    FileUtils.writeStringToFile(f, currentJSON, "UTF-8");
                } catch (final IOException e) {
                    System.err.println("Could not write file " + f.getPath());
                }
            }
        }
    }
}
