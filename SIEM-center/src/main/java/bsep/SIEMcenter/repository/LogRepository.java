package bsep.SIEMcenter.repository;

import bsep.SIEMcenter.model.Log;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends MongoRepository<Log,String> {
}
