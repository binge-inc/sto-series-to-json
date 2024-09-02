package seriesparser;

import com.google.gson.Gson;
import seriesparser.model.Episode;
import seriesparser.model.Season;
import seriesparser.model.Series;
import seriesparser.util.*;

import java.io.File;

public class Main {
    public static final String DEFAULT_IP = "186.2.175.5";

    public static void main(final String[] args) {
        String ip = IPHelper.getIP(args);
        String listDirectory = "./list-json/";
        HTMLDownloader hd = new HTMLDownloader(ip);
        // SeriesListDownloader.loadListJSONs(hd, listDirectory); // ToDo: Uncomment to get fresh list
        File seriesListFile = new File(listDirectory + "series.json");
        Series[] series = null;
        if (seriesListFile.exists()) {
            String listContent = FileLoader.load(listDirectory + "series.json");
            parser.model.Series[] allSeries = new Gson().fromJson(listContent, parser.model.Series[].class);
            series = new Series[allSeries.length];
            String seasonsHTML, episodesHTML;
            for (int i = 0; i < allSeries.length; i++) { // iterate over all series
                seasonsHTML = hd.downloadSeasonsHTML(allSeries[i].getUrl());
                Season[] seasons = SeriesParser.parseSeasons(seasonsHTML);
                for (int j = 0; j < seasons.length; j++) {
                    episodesHTML = hd.downloadEpisodesHTML(seasons[j].getPath());
                    Episode[] episodes = SeriesParser.parseEpisodes(episodesHTML);
                    // ToDo: Streams
                    seasons[j].setEpisodes(episodes);
                }
                series[i] = new Series(allSeries[i].getUrl(), allSeries[i].getName(), seasons);
                System.out.println("ok");
            }
        }
        if (series != null) {
            // ToDo: Save to json.
        }
    }
}