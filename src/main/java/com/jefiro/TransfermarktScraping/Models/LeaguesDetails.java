package com.jefiro.TransfermarktScraping.Models;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LeaguesDetails {
    private String countryName;
    private String leagueName;
    private String reigningChampion;
    private String recordHoldingChampions;
    private String numberTeams;
    private String marketValue;
    private String mediaAge;
    private String mostValuablePlayer;
    private List<Times> times;
    private List<TopGoalScorers> topGoalScorers;
    private List<TableCamp> tableCamp;
}
