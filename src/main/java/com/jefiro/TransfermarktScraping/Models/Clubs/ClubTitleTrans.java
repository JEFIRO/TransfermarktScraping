package com.jefiro.TransfermarktScraping.Models.Clubs;

import java.util.HashMap;
import java.util.List;

public record ClubTitleTrans(
        int id,
        String clubName,
        List<ClubTitle> Results,
        List<Titles> allTitle
) {
    public ClubTitleTrans(HashMap<String, Object> dados, String clubName, List<Titles> allTitle,int id) {
        this(id,clubName, (List<ClubTitle>) dados.get("titleClub"), allTitle);
    }
}
