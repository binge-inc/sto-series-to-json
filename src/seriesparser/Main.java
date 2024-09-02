package seriesparser;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import seriesparser.model.*;
import seriesparser.util.*;

import java.io.File;
import java.io.IOException;

public class Main {
    public static final String DEFAULT_IP = "186.2.175.5";

    public static void main(final String[] args) {
        String ip = IPHelper.getIP(args);
        String listDirectory = "./list-json/";
        String outputDirectory = "./json/";
        HTMLDownloader hd = new HTMLDownloader(ip);
        // SeriesListDownloader.loadListJSONs(hd, listDirectory); // ToDo: Uncomment to get fresh list. This will
        File seriesListFile = new File(listDirectory + "series.json");
        Series[] series = null;
        Gson gson = new Gson();
        if (seriesListFile.exists()) {
            String listContent = FileLoader.load(listDirectory + "series.json");
            parser.model.Series[] allSeries = gson.fromJson(listContent, parser.model.Series[].class);
            series = new Series[allSeries.length];
            int seriesAmountDigits = StringAnalyzer.getDigits(series.length);
            String seasonsHTML, episodesHTML;
            for (int i = 0; i < 11; i++) { // iterate over all series // ToDo: Change 1 back to series.length
                seasonsHTML = hd.downloadSeasonsHTML(allSeries[i].getUrl());
                Season[] seasons = SeriesParser.parseSeasons(seasonsHTML);
                System.out.println("Parsing series " + leftPadZero((i + 1), seriesAmountDigits) + "/" + series.length + ": \"" + allSeries[i].getName() + "\"");
                for (int j = 0; j < seasons.length; j++) {
                    episodesHTML = hd.downloadEpisodesHTML(seasons[j].getPath());
                    Episode[] episodes = SeriesParser.parseEpisodes(episodesHTML);
                    if (episodes == null) continue; // Skip if episodes could not be parsed for some reason
                    String streamsHTML;
                    for (int k = 0; k < episodes.length; k++) {
                        if (episodes[k] == null) continue; // Skip if streams could not be parsed for some reason
                        streamsHTML = hd.downloadStreamsHTML(episodes[k].getPath());
                        episodes[k].setStreams(SeriesParser.parseStreams(streamsHTML));
                    }
                    seasons[j].setEpisodes(episodes);
                }
                series[i] = new Series(allSeries[i].getUrl(), allSeries[i].getName(), seasons);
            }
        }
        if (series == null) {
            System.err.println("series may not be null. Aborting save."); // ToDo
            return;
        }
        File d = new File(outputDirectory);
        if (!d.exists()) d.mkdir();
        String currentJSON;
        for (final Series s : series) {
            if (s == null) continue;
            currentJSON = gson.toJson(s);
            File f = new File(outputDirectory + StringAnalyzer.getSeriesIdFromPath(s.getPath()) + ".json");
            try {
                FileUtils.writeStringToFile(f, currentJSON, "UTF-8");
            } catch (final IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static String leftPadZero(final int inputNumber, final int seriesAmountDigits) {
        StringBuilder output = new StringBuilder("" + inputNumber);
        while (output.length() < seriesAmountDigits) {
            output.insert(0, "0");
        }
        return output.toString();
    }
}
