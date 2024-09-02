package seriesparser.util;

import parser.model.*;
import parser.util.*;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class SeriesListDownloader {
    public static void loadListJSONs(final HTMLDownloader hd, final String directory) {
        String url = "http://" + hd.getIp() + "/serien";
        String html = hd.downloadHTML("/serien");
        if (html == null) {
            System.err.println("SeriesListDownloader.loadListJSONs(String, String): html is null");
            System.err.println("SeriesListDownloader.loadListJSONs(String, String): Aborting...");
        } else {
            String importantHtml = SeriesListCutter.cutUnimportantStuff(html);
            ArrayList<String> categories = SeriesListCutter.getCategories(importantHtml);
            Category[] parsedCategories = new Category[categories.size()];
            SeriesListParser.parseCategoryList(categories, parsedCategories);
            String jsonRawCategorized = ConvertToJSON.categoriesToJSON(parsedCategories, false);
            SaveFiles.saveSeriesListJSON(directory, jsonRawCategorized, false, true);
            String jsonFormattedCategorized = ConvertToJSON.categoriesToJSON(parsedCategories, true);
            SaveFiles.saveSeriesListJSON(directory, jsonFormattedCategorized, true, true);
            ArrayList<Series> series = Repack.packSeriesArrayFromCategoryArray(parsedCategories);
            Series[] seriesArray = SeriesListToArray.convert(series);
            String jsonRaw = ConvertToJSON.categoriesToJSON(seriesArray, false);
            SaveFiles.saveSeriesListJSON(directory, jsonRaw, false, false);
            String jsonFormatted = ConvertToJSON.categoriesToJSON(seriesArray, true);
            SaveFiles.saveSeriesListJSON(directory, jsonFormatted, true, false);
        }
    }
}
