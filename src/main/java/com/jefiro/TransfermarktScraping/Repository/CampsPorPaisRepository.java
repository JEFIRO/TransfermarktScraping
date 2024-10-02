package com.jefiro.TransfermarktScraping.Repository;

import com.jefiro.TransfermarktScraping.Entity.CampPorPaisEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CampsPorPaisRepository extends MongoRepository<CampPorPaisEntity,String> {
}
