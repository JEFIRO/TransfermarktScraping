package com.jefiro.TransfermarktScraping.Controller;

import com.jefiro.TransfermarktScraping.Models.Clubs.ClubSearchDTO;
import com.jefiro.TransfermarktScraping.Models.Clubs.SearchClub;
import com.jefiro.TransfermarktScraping.Models.Clubs.SearchClubId;
import com.jefiro.TransfermarktScraping.Models.Clubs.SearchClubTitle;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/clubs")
public class ClubsController {
    @GetMapping("/search")
    public ResponseEntity<?> clubs(@RequestParam String club, @RequestParam int page) throws IOException {
        var clubs = SearchClub.searchClubs(club, page);

        List<ClubSearchDTO> dto = clubs.stream().map(clubSearch -> new ClubSearchDTO(clubSearch.get_id(), clubSearch.getImg()
                ,clubSearch.getName(), clubSearch.getLink(), clubSearch.getCountry(), clubSearch.getSquad(), clubSearch.getValue()))
                .filter(clubSearchDTO -> !clubSearchDTO.name().isEmpty())
                .toList();
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/search/clubid")
    public ResponseEntity<?> clubsid(@RequestParam int clubid) throws IOException {
        var clubs = SearchClubId.searchClubById(clubid);;
        return ResponseEntity.ok(clubs);
    }

    @GetMapping("/search/title")
    public ResponseEntity<?> titleid(@RequestParam int clubid) throws IOException {
        var clubs = SearchClubTitle.getTitles(clubid);;
        return ResponseEntity.ok(clubs);
    }



}
