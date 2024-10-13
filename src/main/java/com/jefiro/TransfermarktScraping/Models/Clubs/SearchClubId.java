package com.jefiro.TransfermarktScraping.Models.Clubs;

import com.jefiro.TransfermarktScraping.Models.Squad;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.jefiro.TransfermarktScraping.Models.Clubs.SearchClub.extrairId;

public class SearchClubId {
    private static final String URL = "https://www.transfermarkt.us/-/datenfakten/verein/";

    public static ClubProfile searchClubById(int id) throws IOException {

        HashMap<String, Object> searh = new HashMap<>();

        List<Leagues> league = new ArrayList<>();
        List<String> colorsList = new ArrayList<>();
        List<Squad> squadList = new ArrayList<>();
        List<String> historicalCrestsList = new ArrayList<>();

        Document doc = Jsoup.connect(URL + id).get();
        Elements elementsInfo = doc.select("div.data-header__club-info");
        elementsInfo.forEach(element -> {
            var infos = element.select("span");
            if (infos.size() >= 4) {
                var leagueName = infos.select("a").get(0).text();
                var link = infos.select("a").get(0).attr("href");
                String _id = null;
                if (!link.isEmpty()) {
                    _id = extrairId(link);
                }
                var tier = infos.get(1).select("span.data-header__content").text();
                var coutryName = infos.get(1).select("span.data-header__content img").attr("title");
                var table = infos.get(3).select("span.data-header__content").text();
                System.out.println(coutryName);
                Leagues leagues = new Leagues(leagueName, _id, coutryName, tier, table);
                league.add(leagues);
                System.out.println(leagues);
            }
        });

        var clubImg = doc.select("div.datenfakten-wappen img").attr("src");
        var clubName = doc.select("div.datenfakten-wappen a").attr("title");
        var clubLink = doc.select("div.datenfakten-wappen a").attr("href");

        searh.put("clubImg", clubImg);
        searh.put("clubName", clubName);
        searh.put("clubLink", clubLink);

        Elements elementsInfos2 = doc.select("ul.data-header__items");
        elementsInfos2.first().forEach(element -> {
            var row = element.select("li");
            if (row.size() >= 3) {
                var squadSize = row.first().select("span").text();
                var age = row.get(1).select("span").text();
                var foreigners = row.get(2).select("span a").text();
                Squad squad = new Squad(squadSize, age, foreigners);
                squadList.add(squad);
            }
        });
        if (elementsInfos2.size() > 1) {
            elementsInfos2.get(1).forEach(element -> {
                var row = element.select("li");
                if (row.size() >= 3) {
                    var stadium = row.get(1).select("a").text();
                    var stadiumSeats = row.get(1).select("span.tabellenplatz").text().replace(" Seats", "");
                    var transferRecord = row.get(2).select("span");
                    var currentTransferRecord = transferRecord.select("span a").text();
                    var currentMarketValue = doc.select("div.data-header__box--small a").text().replace(" Total market value", "");

                    searh.put("stadium", stadium);
                    searh.put("stadiumSeats", stadiumSeats);
                    searh.put("currentTransferRecord", currentTransferRecord);
                    searh.put("currentMarketValue", currentMarketValue);
                }
            });
        }


        Elements elements = doc.select("tbody");
        elements.forEach(element -> {
            var rows = element.select("tr td");
            if (rows.size() >= 8) {

                var oficalClubName = rows.get(0).text();
                var site = rows.get(6).text();
                var founded = rows.get(7).text();
                var menber = rows.get(8).text();
                var colors = element.select("span");
                colors.forEach(color -> {
                    var colorFormater = color.attr("style").replace("background-color:", "").replace(";", "").replace(" ", "");
                    if (colorFormater.isEmpty()) {
                        return;
                    }
                    colorsList.add(colorFormater);
                });
                searh.put("oficalClubName", oficalClubName);
                searh.put("site", site);
                searh.put("founded", founded);
                searh.put("menber", menber);
            }
        });

        try {
            Element reloadElement = doc.getElementById("reload");
            if (reloadElement != null) {
                Elements imgs = reloadElement.select("span img");
                imgs.forEach(img -> {
                    String historicalCrests = img.attr("src");
                    historicalCrestsList.add(historicalCrests);
                });
            }

        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
        }

        ClubProfile clubProfile = new ClubProfile();
        clubProfile.fromMap(searh, colorsList, squadList, league, historicalCrestsList);
        return clubProfile;
    }

    public static void main(String[] args) throws IOException {
        searchClubById(281);
    }
}
