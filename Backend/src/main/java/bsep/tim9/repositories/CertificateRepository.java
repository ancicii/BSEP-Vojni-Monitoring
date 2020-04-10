package bsep.tim9.repositories;

import bsep.tim9.model.Certificate;
import bsep.tim9.model.CertificateType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    Certificate findByAlias(String alias);
    List<Certificate> findAllByType(CertificateType type);
    List<Certificate> findAll();
    List<Certificate> findAllByIssueralias(String alias);

}
