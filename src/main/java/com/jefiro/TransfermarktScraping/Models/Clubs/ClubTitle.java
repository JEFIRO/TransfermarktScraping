package com.jefiro.TransfermarktScraping.Models.Clubs;

import java.util.List;

public record ClubTitle(
        String img,
        String name,
        List<String> yearsChampion,
        int totalChampions

) {
}
