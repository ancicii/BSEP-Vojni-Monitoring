package bsep.tim9.DTOs;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.X500NameBuilder;
import org.bouncycastle.asn1.x500.style.BCStyle;

public class X500NameDTO {

    private String cn;
    private String surname;
    private String givenname;
    private String o;
    private String ou;
    private String c;
    private String e;
    private String uid;

    public X500NameDTO(String cn, String surname, String givenname, String o, String ou, String c, String e, String uid) {
        this.cn = cn;
        this.surname = surname;
        this.givenname = givenname;
        this.o = o;
        this.ou = ou;
        this.c = c;
        this.e = e;
        this.uid = uid;
    }

    public X500NameDTO() {
    }

    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGivenname() {
        return givenname;
    }

    public void setGivenname(String givenname) {
        this.givenname = givenname;
    }

    public String getO() {
        return o;
    }

    public void setO(String o) {
        this.o = o;
    }

    public String getOu() {
        return ou;
    }

    public void setOu(String ou) {
        this.ou = ou;
    }

    public String getC() {
        return c;
    }

    public void setC(String c) {
        this.c = c;
    }

    public String getE() {
        return e;
    }

    public void setE(String e) {
        this.e = e;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public X500Name getX500Name() {
        X500NameBuilder builder = new X500NameBuilder(BCStyle.INSTANCE);
        builder.addRDN(BCStyle.CN, cn);
        builder.addRDN(BCStyle.SURNAME, surname);
        builder.addRDN(BCStyle.GIVENNAME, givenname);
        builder.addRDN(BCStyle.O, o);
        builder.addRDN(BCStyle.OU, ou);
        builder.addRDN(BCStyle.C, c);
        builder.addRDN(BCStyle.E, e);
        builder.addRDN(BCStyle.UID, uid);
        return builder.build();
    }
}
