package com.jefiro.TransfermarktScraping.Models.Clubs;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SearchPlayer {
    private String idPlayer;
    private String number;
    private String playName;
    private String link;
    private String posicao;
    private String birth;
    private String age;
    private String height;
    private String foot;
    private String joined;
    private String contract;
    private String valueMarket;
    private List<String> national;
}
