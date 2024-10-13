package com.jefiro.TransfermarktScraping.Models.Clubs;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchClub {
    private static final String url = "https://www.transfermarkt.com/schnellsuche/ergebnis/schnellsuche?query=";
    private static final String urlEnd = "&Verein_page=";

    public static List<ClubSearch> searchClubs(String name, int page) throws IOException {
        Document document = Jsoup.connect(url + name + urlEnd + page).get();
        Elements elements = document.select("tbody tr");
        List<ClubSearch> clubSearches = new ArrayList<>();
        elements.forEach(element -> {

            var row = element.select("td.zentriert");

            var img = element.select("td.suche-vereinswappen img").attr("src");

            var clubNameElement = element.select("td a").first();
            var clubName = clubNameElement != null ? clubNameElement.text() : null;
            String link = clubNameElement != null ? clubNameElement.attr("href") : null;
            String id = null;
            if (link != null) {
                id = extrairId(link);
            }

            String country = null;
            String squad = null;

            if (row.size() >= 3) {
                country = row.get(1).select("img").attr("title");
                squad = row.get(2).text();
            }
            if (country == null) {
                return;
            }

            var value = element.select("td.rechts").text();

            clubSearches.add(new ClubSearch(id, img, clubName, link, country, squad, value));

        });
        return clubSearches;
    }

    public static String extrairId(String url) {
        int lastSlashIndex = url.lastIndexOf('/');
        if (lastSlashIndex != -1 && lastSlashIndex < url.length() - 1) {
            return url.substring(lastSlashIndex + 1);
        }
        return "";
    }
}
