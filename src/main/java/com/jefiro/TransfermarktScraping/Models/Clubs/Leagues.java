package com.jefiro.TransfermarktScraping.Models.Clubs;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Leagues {
    private String name;
    private String contryId;
    private String countryName;
    private String tier;
    private String positionTable;

}
