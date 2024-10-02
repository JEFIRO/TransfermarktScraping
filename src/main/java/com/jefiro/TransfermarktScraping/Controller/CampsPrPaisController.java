package com.jefiro.TransfermarktScraping.Controller;

import com.jefiro.TransfermarktScraping.Entity.CampPorPaisEntity;
import com.jefiro.TransfermarktScraping.Repository.CampsPorPaisRepository;
import com.jefiro.TransfermarktScraping.Scraping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/carrega-banco")
public class CampsPrPaisController {
    @Autowired
    CampsPorPaisRepository repository;

    @GetMapping
    public void comps() {
        var dados = Scraping.getCampeonatos();
        List<CampPorPaisEntity> camp = dados.stream().map(CampPorPaisEntity::new).collect(Collectors.toList());
        repository.saveAll(camp);
    }
}
