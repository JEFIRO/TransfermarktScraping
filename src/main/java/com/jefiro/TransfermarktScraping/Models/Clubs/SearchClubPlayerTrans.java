package com.jefiro.TransfermarktScraping.Models.Clubs;

import java.util.List;

public record SearchClubPlayerTrans(
        int ClubId,
        List<SearchPlayer> results
) {}
