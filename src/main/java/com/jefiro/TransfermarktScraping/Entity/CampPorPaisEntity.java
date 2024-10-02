package com.jefiro.TransfermarktScraping.Entity;

import com.jefiro.TransfermarktScraping.Models.CampsPorPais;
import com.jefiro.TransfermarktScraping.Models.Leagues;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Random;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "camps_por_pais")
public class CampPorPaisEntity {
    @Id
    String _id;
    int transferID;
    String pais;
    List<Leagues> leagues;

    public CampPorPaisEntity(CampsPorPais dados) {
        this.transferID = generateID();
        this.pais = dados.getPais();
        this.leagues = dados.getLeagues();
    }

    private int generateID() {
        Random random = new Random();
        return random.nextInt(999999999);
    }
}