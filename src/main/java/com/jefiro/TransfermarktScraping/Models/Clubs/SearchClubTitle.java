package com.jefiro.TransfermarktScraping.Models.Clubs;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class SearchClubTitle {
    static final String URL = "https://www.transfermarkt.us/-/erfolge/verein/";

    public static ClubTitleTrans getTitles(int id) throws IOException {
        Document doc = Jsoup.connect(URL + id).get();

        List<Titles> allTiltes = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();

        Elements elementsTitle = doc.select("tbody tr");
        elementsTitle.forEach(element -> {
            var img = element.select("td.zentriert.no-border-rechts img").attr("src");
            var year = element.select("td.zentriert").text();
            var nameTitle = element.select("td.no-border-links").text();
            allTiltes.add(new Titles(img, year, nameTitle));
        });

        String elementsClub = doc.select("div.data-header__profile-container img").attr("title");
        int _id = id;


        Elements elements = doc.select("div.large-8.columns");
        elements.forEach(element -> {
            List<ClubTitle> titleClub = new ArrayList<>();
            var rows = element.select("div.row");
            rows.forEach(row -> {
                var boxs = row.select("div.box");
                boxs.forEach(box -> {
                    List<String> years = new ArrayList<>();
                    var img = box.select("div.erfolg_bild_box img").attr("src");
                    var name = box.select("div.erfolg_bild_box img").attr("title");
                    var year = box.select(".erfolg_infotext_box").text();

                    String[] parts = year.split(",\\s*");
                    Collections.addAll(years, parts);

                    var championAllYears = years.size();
                    titleClub.add(new ClubTitle(img, name, years, championAllYears));
                    map.put("titleClub", titleClub);
                });
            });
        });
        ClubTitleTrans clubTitleTrans = new ClubTitleTrans(map, elementsClub, allTiltes, _id);
        return clubTitleTrans;
    }

    public static void main(String[] args) throws IOException {
        getTitles(583);
    }
}
