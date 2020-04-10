package bsep.tim9.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Certificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false)
    private Long id;

    @Column(unique = true, nullable = false)
    private String alias;

    @Column(nullable = false)
    private String issuer_name;

    @Column(nullable = false)
    private String issueralias;

    @Column(nullable = false)
    private String serial_number;

    @Column(nullable = false)
    private LocalDateTime start_date;

    @Column(nullable = false)
    private LocalDateTime end_date;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CertificateType type;

    public Certificate(String alias, String issuer_name, String issueralias, String serial_number, LocalDateTime start_date, LocalDateTime end_date, Boolean isActive, CertificateType type) {
        this.alias = alias;
        this.issueralias = issueralias;
        this.issuer_name = issuer_name;
        this.serial_number = serial_number;
        this.start_date = start_date;
        this.end_date = end_date;
        this.isActive = isActive;
        this.type = type;
    }

    public Certificate() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getIssuer_name() {
        return issuer_name;
    }

    public void setIssuer_name(String issuer_name) {
        this.issuer_name = issuer_name;
    }

    public String getIssueralias() {
        return issueralias;
    }

    public void setIssueralias(String issueralias) {
        this.issueralias = issueralias;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public CertificateType getType() {
        return type;
    }

    public void setType(CertificateType type) {
        this.type = type;
    }

    public String getIssueralias() {
        return issueralias;
    }
    public void setIssueralias(String issuer_alias) {
        this.issueralias = issuer_alias;
    }
}
