package com.jefiro.TransfermarktScraping.Models.Clubs;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClubSearch {
    private String _id;
    private String img;
    private String name;
    private String link;
    private String country;
    private String squad;
    private String value;
}
