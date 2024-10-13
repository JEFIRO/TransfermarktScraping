package com.jefiro.TransfermarktScraping.Models.Clubs;

import com.jefiro.TransfermarktScraping.Models.Squad;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ClubProfile {
    private String _id;
    private String url;
    private String clubName;
    private String oficialName;
    private String imagem;
    private String webSite;
    private String foundedOn;
    private String members;
    private List<String> colors;
    private String stadiumName;
    private String stadiumSeats;
    private String currentTransferRecord;
    private String currentMarketValue;
    private List<Squad> squad;
    private List<Leagues> leagues;
    private List<String> historicalCrests;

    public void fromMap(HashMap<String, Object> dados, List<String> color, List<Squad> squad, List<Leagues> league, List<String> historicalCrests) {
        set_id((String) dados.get("id"));
        setClubName((String) dados.get("clubName"));
        setUrl((String) dados.get("clubLink"));
        setOficialName((String) dados.get("oficalClubName"));
        setImagem((String) dados.get("clubImg"));
        setWebSite((String) dados.get("site"));
        setFoundedOn((String) dados.get("founded"));
        setMembers((String) dados.get("menber"));
        setCurrentMarketValue((String) dados.get("currentMarketValue"));
        setCurrentTransferRecord((String) dados.get("currentTransferRecord"));
        setStadiumName((String) dados.get("stadium"));
        setStadiumSeats((String) dados.get("stadiumSeats"));

        this.colors = color;
        this.squad = squad;
        this.leagues = league;
        this.historicalCrests = historicalCrests;
    }
}
