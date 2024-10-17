package com.jefiro.TransfermarktScraping.Models.Clubs;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.jefiro.TransfermarktScraping.Models.Clubs.SearchClub.extrairId;

public class SearchClubPlayer {
    public static SearchClubPlayerTrans getPayerClub(int id, int idSaison) {
        try {
            String URL = String.format("https://www.transfermarkt.us/-/kader/verein/%d/saison_id/%d/plus/1", id, idSaison);

            List<SearchPlayer> playerList = new ArrayList<>();

            Document doc = Jsoup.connect(URL).get();
            Elements elements = doc.select("tbody tr");
            elements.forEach(element -> {
                var td = element.select("tbody tr");
                if (!td.isEmpty()) {
                    var number = element.select("div.rn_nummer").text();
                    var playName = element.select("tbody tr a").first().text();
                    var link = element.select("tbody tr a").attr("href");
                    String idPlayer = null;
                    if (!link.isEmpty()) {
                        idPlayer = extrairId(link);
                    }
                    var posicao = element.select("tbody tr").get(1).text();


                    var tdZ = element.select("td.zentriert");
                    if (tdZ.size() > 1) {
                        List<String> national = new ArrayList<>();

                        var birth = filterDateBirth(tdZ.get(1).text());
                        var age = extractAge(tdZ.get(1).text());
                        var height = tdZ.get(3).text();
                        var foot = tdZ.get(4).text();
                        if (height.isEmpty()) {
                            height = tdZ.get(4).text();
                            foot = tdZ.get(5).text();
                        }
                        var joined = tdZ.get(5).text();
                        var contractItem = tdZ.get(7).text();
                        var contract = contractItem.isEmpty() ? null : contractItem;
                        var valueMarket = element.select("td.rechts.hauptlink a").text();
                        var nats = tdZ.get(2).select("img");

                        nats.forEach(nat -> {
                            national.add(nat.attr("title"));

                        });

                        playerList.add(new SearchPlayer(idPlayer, number, playName, link, posicao, birth, age, height, foot, joined, contract, valueMarket, national));

                    }
                }
            });
            return new SearchClubPlayerTrans(id, playerList);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static String extractAge(String string) {
        Pattern pattern = Pattern.compile("\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(string);

        String numero = "";

        if (matcher.find()) {
            numero = matcher.group(1);
        }

        return numero;
    }

    public static String filterDateBirth(String string) {
        Pattern pattern = Pattern.compile("\\((\\d+)\\)");
        Matcher matcher = pattern.matcher(string);

        return string.replaceAll("\\s*\\(\\d+\\)", "");
    }
}