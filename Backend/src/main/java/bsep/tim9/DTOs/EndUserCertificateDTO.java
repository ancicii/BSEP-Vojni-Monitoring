package bsep.tim9.DTOs;

public class EndUserCertificateDTO {

    private String subjectPublicKey;
    private X500NameDTO subjectName;
    private String issuerAlias;
    private String subjectAlias;

    public EndUserCertificateDTO(String subjectPublicKey, X500NameDTO subjectName, String issuerAlias, String subjectAlias) {
        this.subjectPublicKey = subjectPublicKey;
        this.subjectName = subjectName;
        this.issuerAlias = issuerAlias;
        this.subjectAlias = subjectAlias;
    }

    public EndUserCertificateDTO() {
    }

    public String getSubjectPublicKey() {
        return subjectPublicKey;
    }

    public void setSubjectPublicKey(String subjectPublicKey) {
        this.subjectPublicKey = subjectPublicKey;
    }

    public X500NameDTO getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(X500NameDTO subjectName) {
        this.subjectName = subjectName;
    }

    public String getIssuerAlias() {
        return issuerAlias;
    }

    public void setIssuerAlias(String issuerAlias) {
        this.issuerAlias = issuerAlias;
    }

    public String getSubjectAlias() {
        return subjectAlias;
    }

    public void setSubjectAlias(String subjectAlias) {
        this.subjectAlias = subjectAlias;
    }
}
