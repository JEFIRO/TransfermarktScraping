package com.jefiro.TransfermarktScraping;

import com.jefiro.TransfermarktScraping.Models.CampsPorPais;
import com.jefiro.TransfermarktScraping.Models.Leagues;
import com.jefiro.TransfermarktScraping.Repository.CampsPorPaisRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Scraping {

    private final static String BASE_URL = "https://www.transfermarkt.com/";
    private final static String CAMPEONATOS_URL = "https://www.transfermarkt.com/wettbewerbe/national/wettbewerbe/";

    @Autowired
    CampsPorPaisRepository repository;

    public static List<CampsPorPais> getCampeonatos() {
        List<CampsPorPais> campsList = new ArrayList<>();

        try {
            // Loop through pages to scrape leagues information
            for (int i = 2; i <= 253; i++) {
                List<Leagues> leagues = new ArrayList<>();

                System.out.println("****************************");
                System.out.println("Scraping page: " + i);
                Document document;
                try {
                    document = Jsoup.connect(CAMPEONATOS_URL + i + "/saison_id/2024/plus/1").get();
                } catch (IOException e) {
                    System.out.println("Failed to fetch URL: " + CAMPEONATOS_URL + i);
                    continue;
                }

                // Select country and league rows
                Elements paisTile = document.select("div.box li a[title]");
                if (paisTile.isEmpty()) {
                    System.out.println("No country information found on page: " + i);
                    continue; // Skip this page if no country data found
                }

                String countryName = paisTile.get(0).attr("title"); // Get the country name

                Elements rows = document.select("tr");

                for (Element row : rows) {
                    String link = row.select("td.hauptlink a").attr("href");
                    String leagueName = row.select("td.hauptlink a[title]").attr("title");

                    Elements zentriertColumns = row.select("td.zentriert");
                    if (zentriertColumns.size() >= 4) {
                        String teams = zentriertColumns.get(0).text();
                        String players = zentriertColumns.get(1).text();
                        String avgAge = zentriertColumns.get(2).text();
                        String foreignPlayers = zentriertColumns.get(3).text();
                        String goalsPerMatch = zentriertColumns.get(4).select("a").text();
                        String value = row.select("td.rechts.hauptlink").text();

                        leagues.add(new Leagues(leagueName, teams, players, avgAge, foreignPlayers, goalsPerMatch, value));
                    } else {
                        System.out.println("Row doesn't have enough columns: " + row.text());
                    }
                }

                CampsPorPais camp = new CampsPorPais(countryName,leagues.size(), leagues);
                campsList.add(camp);

                System.out.println("Country: " + countryName + " | Leagues: " + leagues.size());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

        return campsList;
    }
}
