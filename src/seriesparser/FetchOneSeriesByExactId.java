package seriesparser;

import com.google.gson.Gson;
import org.apache.commons.io.FileUtils;
import seriesparser.jsonmodel.*;
import seriesparser.util.*;

import java.io.File;
import java.io.IOException;

public class FetchOneSeriesByExactId {
    public static void main(final String[] args) {
        String ip = CLI.getIP(args);
        String outputDirectory = CLI.getSeriesJsonOutputDirectory(args);
        String seriesId = CLI.getSeriesId(args);
        if (seriesId == null) {
            System.err.println("seriesId must be specified.");
            return;
        }
        File d = new File(outputDirectory);
        FileFunctions.betterMkdir(d);
        System.out.println("Creating json for series \"" + seriesId + "\".");
        HTMLDownloader hd = new HTMLDownloader(ip);
        String nameHTML, name, seasonsHTML, seriesDescrHTML, descr, episodesHTML, episodeDescrHTML, path;
        String[] versionHTMLs;
        String basePath = "/serie/stream/";
        path = basePath + seriesId;
        nameHTML = hd.downloadNiceName(path);
        String startTitlePattern = "<span>";
        String endTitlePattern = "</span>";
        name = StringFunctions.cutFromTo(nameHTML, startTitlePattern, endTitlePattern);
        seasonsHTML = hd.downloadSeasonsHTML(path);
        Season[] seasons = SeriesParser.parseSeasons(seasonsHTML);
        seriesDescrHTML = hd.downloadSeriesDescriptionHTML(path);
        descr = SeriesParser.parseSeriesDescription(seriesDescrHTML);
        for (final Season season : seasons) {
            episodesHTML = hd.downloadEpisodesHTML(basePath + seriesId + "/" + season.getSeasonId());
            Episode[] episodes = SeriesParser.parseEpisodes(episodesHTML);
            if (episodes == null) continue; // Skip if episodes could not be parsed for some reason
            for (final Episode episode : episodes) {
                if (episode == null) continue; // Skip if episode could not be parsed for some reason
                episodeDescrHTML = hd.downloadEpisodeDescriptionHTML(basePath + seriesId + "/" + season.getSeasonId() + "/" + episode.getEpId());
                episode.setDescr(SeriesParser.parseEpisodeDescription(episodeDescrHTML));
                versionHTMLs = hd.downloadVersionHTMLs(basePath + seriesId + "/" + season.getSeasonId() + "/" + episode.getEpId());
                episode.setVersions(SeriesParser.parseVersions(versionHTMLs));
            }
            season.setEpisodes(episodes);
        }
        Series series = new Series(seriesId, name, descr, seasons);
        Gson gson = new Gson();
        String json = gson.toJson(series);
        File f = new File(outputDirectory + seriesId + ".json");
        try {
            FileUtils.writeStringToFile(f, json, "UTF-8");
        } catch (final IOException e) {
            System.err.println("Could not write file " + f.getPath());
            return;
        }
        System.out.println("Success!");
    }
}
