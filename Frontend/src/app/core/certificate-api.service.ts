import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {EndUserCertificateModel} from "../shared/model/end-user-certificate.model";
import {IntermediateCertificateModel} from "../shared/model/intermediate-certificate.model";

@Injectable({
  providedIn: 'root'
})
export class CertificateApiService {
  private _headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Access-Control-Allow-Origin': '*',
    'Access-Control-Allow-Credentials': 'true'
  });

  constructor(private _http: HttpClient) {
  }

  getEndUserCertificates(){
    return this._http.get(`certificate/all/enduser`, {headers: this._headers});
  }

  getIntermediateCertificates(){
    return this._http.get(`certificate/all/intermediate`, {headers: this._headers});
  }

  getAllCertificates(){
    return this._http.get(`certificate/allIntermediateAndRoot`, {headers: this._headers});
  }

  revokeCertificate(alias : String){
      return this._http.get(`certificate/delete/${alias}`, {headers: this._headers});
  }

  createEndUserCertificate(eucModel: EndUserCertificateModel) {
    return this._http.post(`certificate/enduser`, {
      issuerAlias: eucModel.issuerAlias,
      subjectAlias: eucModel.subjectAlias,
      subjectName: {
        cn: eucModel.subjectName.cn,
        surname: eucModel.subjectName.surname,
        givenname: eucModel.subjectName.givenname,
        o: eucModel.subjectName.o,
        ou: eucModel.subjectName.ou,
        c: eucModel.subjectName.c,
        e: eucModel.subjectName.e,
        uid: eucModel.subjectName.uid,
      },
      subjectPublicKey: eucModel.subjectPublicKey
    }, {headers: this._headers, responseType: 'text'});
  }

  createIntermediateCertificate(icModel: IntermediateCertificateModel) {
    return this._http.post(`certificate/intermediate`, {
      issuerAlias: icModel.issuerAlias,
      subjectAlias: icModel.subjectAlias,
      subjectName: {
        cn: icModel.subjectName.cn,
        surname: icModel.subjectName.surname,
        givenname: icModel.subjectName.givenname,
        o: icModel.subjectName.o,
        ou: icModel.subjectName.ou,
        c: icModel.subjectName.c,
        e: icModel.subjectName.e,
        uid: icModel.subjectName.uid,
      }
    }, {headers: this._headers, responseType: 'text'});
  }

  getAllCertificates1() {
    return this._http.get(`certificate/all`,{headers: this._headers});
  }
}
