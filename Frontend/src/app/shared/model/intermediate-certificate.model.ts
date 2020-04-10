import {X500NameModel} from "./x500-name.model";

export class IntermediateCertificateModel {
  private _subjectName: X500NameModel;

  private _issuerAlias: string;

  private _subjectAlias: string;

  constructor(subjectName: X500NameModel, issuerAlias: string, subjectAlias: string) {
    this._subjectName = subjectName;
    this._issuerAlias = issuerAlias;
    this._subjectAlias = subjectAlias;
  }

  get subjectName(): X500NameModel {
    return this._subjectName;
  }

  set subjectName(value: X500NameModel) {
    this._subjectName = value;
  }

  get issuerAlias(): string {
    return this._issuerAlias;
  }

  set issuerAlias(value: string) {
    this._issuerAlias = value;
  }

  get subjectAlias(): string {
    return this._subjectAlias;
  }

  set subjectAlias(value: string) {
    this._subjectAlias = value;
  }
}
