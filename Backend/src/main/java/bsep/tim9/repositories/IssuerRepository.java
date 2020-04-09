package bsep.tim9.repositories;

import bsep.tim9.model.Issuer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IssuerRepository extends JpaRepository<Issuer, Long> {
    Issuer findByAlias(String alias);
}
