package com.jefiro.TransfermarktScraping.Models;

import lombok.*;

import java.util.List;
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

public class CampsPorPais {
    String pais;
    int nunberLeagues;
    List<Leagues> leagues;
}
