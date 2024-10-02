package com.jefiro.TransfermarktScraping.Models;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Leagues {
    String league;
    String numberClubs;
    String numberPlayers;
    String avg_age;
    String foreigners;
    String goalsPerMatch;
    String totalValue;
}
