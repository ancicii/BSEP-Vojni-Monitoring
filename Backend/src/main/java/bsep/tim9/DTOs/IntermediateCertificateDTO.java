package bsep.tim9.DTOs;

public class IntermediateCertificateDTO {

    private X500NameDTO subjectName;
    private String issuerAlias;
    private String subjectAlias;

    public IntermediateCertificateDTO(String subjectPublicKey, X500NameDTO subjectName, String issuerAlias, String subjectAlias) {
        this.subjectName = subjectName;
        this.issuerAlias = issuerAlias;
        this.subjectAlias = subjectAlias;
    }

    public IntermediateCertificateDTO() {
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
